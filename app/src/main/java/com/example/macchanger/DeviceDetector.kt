package com.example.macchanger

import android.content.Context
import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Auto-detects the device chipset vendor and determines the correct
 * MAC file paths for EFS/persist.
 */
object DeviceDetector {

    data class DeviceInfo(
        val chipsetVendor: String,
        val platform: String,
        val macPaths: List<String>,
        val efsPartition: String?,
        val supportsIpLink: Boolean = true  // all rooted devices support this
    )

    /**
     * Detect chipset vendor and return device info with recommended paths.
     * Must be called from a coroutine (runs shell commands on IO).
     */
    suspend fun detect(): DeviceInfo = withContext(Dispatchers.IO) {
        val hardware = getProp("ro.hardware").lowercase()
        val platform = getProp("ro.board.platform").lowercase()
        val manufacturer = getProp("ro.product.manufacturer").lowercase()
        val brand = getProp("ro.product.brand").lowercase()
        val soc = getProp("ro.soc.manufacturer").lowercase()
        val chipname = getProp("ro.chipname").lowercase()

        // Determine chipset vendor
        val chipsetVendor = when {
            // Exynos (Samsung's own SoC)
            chipname.contains("exynos") || platform.contains("exynos") ||
            hardware.contains("samsungexynos") -> "Exynos (Samsung)"

            // Qualcomm Snapdragon
            platform.contains("msm") || platform.contains("sdm") ||
            platform.contains("sm") || platform.contains("qcom") ||
            hardware.contains("qcom") || soc.contains("qualcomm") -> "Qualcomm Snapdragon"

            // MediaTek
            platform.contains("mt") || hardware.contains("mt") ||
            soc.contains("mediatek") || chipname.contains("mt") -> "MediaTek"

            // Samsung (generic)
            manufacturer.contains("samsung") || brand.contains("samsung") -> "Samsung"

            // Kirin (Huawei)
            platform.contains("kirin") || soc.contains("hisilicon") ||
            hardware.contains("kirin") || manufacturer.contains("huawei") -> "HiSilicon Kirin (Huawei)"

            // Google Tensor
            hardware.contains("tensor") || platform.contains("gs") -> "Google Tensor"

            // Unisoc / Spreadtrum
            platform.contains("ums") || soc.contains("unisoc") ||
            platform.contains("sp") -> "Unisoc/Spreadtrum"

            else -> "Unknown ($platform / $hardware)"
        }

        // Determine MAC file paths based on vendor/platform
        val macPaths = when {
            // Samsung typically uses /mnt/vendor/efs or /efs
            manufacturer.contains("samsung") || brand.contains("samsung") -> listOf(
                "/mnt/vendor/efs/wifi/.mac.info",
                "/efs/wifi/.mac.info",
                "/mnt/vendor/efs/wifi/.mac.cob",
                "/efs/wifi/.mac.cob"
            )

            // Qualcomm-based devices often use /persist
            platform.contains("msm") || platform.contains("sdm") ||
            platform.contains("sm") || platform.contains("qcom") -> listOf(
                "/persist/wifi/.mac.info",
                "/mnt/vendor/persist/wifi/.mac.info",
                "/persist/wifi/.mac.cob",
                "/mnt/vendor/persist/wifi/.mac.cob"
            )

            // MediaTek
            platform.contains("mt") -> listOf(
                "/mnt/vendor/nvdata/APCFG/APRDEB/WIFI",
                "/nvdata/APCFG/APRDEB/WIFI",
                "/mnt/vendor/efs/wifi/.mac.info",
                "/persist/wifi/.mac.info"
            )

            // Huawei / Kirin
            manufacturer.contains("huawei") -> listOf(
                "/cust/APCFG/APRDEB/WIFI",
                "/mnt/vendor/efs/wifi/.mac.info",
                "/persist/wifi/.mac.info"
            )

            // Fallback: try all common paths
            else -> listOf(
                "/mnt/vendor/efs/wifi/.mac.info",
                "/persist/wifi/.mac.info",
                "/efs/wifi/.mac.info",
                "/mnt/vendor/persist/wifi/.mac.info"
            )
        }

        // EFS block device name
        val efsPartition = when {
            manufacturer.contains("samsung") || brand.contains("samsung") -> "/dev/block/by-name/efs"
            else -> findEfsPartition()
        }

        // Verify ip link set support (should work on all rooted devices)
        val ipLinkTest = Shell.cmd("ip link show wlan0").exec()
        val supportsIpLink = ipLinkTest.isSuccess

        DeviceInfo(
            chipsetVendor = chipsetVendor,
            platform = platform.ifEmpty { hardware },
            macPaths = macPaths,
            efsPartition = efsPartition,
            supportsIpLink = supportsIpLink
        )
    }

    /** Get a system property via getprop */
    private fun getProp(key: String): String {
        val result = Shell.cmd("getprop $key").exec()
        return if (result.isSuccess) result.out.firstOrNull()?.trim() ?: "" else ""
    }

    /** Try to find the EFS block device */
    private fun findEfsPartition(): String? {
        val candidates = listOf(
            "/dev/block/by-name/efs",
            "/dev/block/by-name/persist",
            "/dev/block/by-name/nvdata"
        )
        for (path in candidates) {
            val result = Shell.cmd("test -e $path && echo EXISTS").exec()
            if (result.isSuccess && result.out.firstOrNull()?.trim() == "EXISTS") {
                return path
            }
        }
        return null
    }

    /**
     * Produce a human-readable summary of the detection results.
     */
    fun formatReport(info: DeviceInfo): String = buildString {
        appendLine("── Device Detection ──")
        appendLine("Chipset : ${info.chipsetVendor}")
        appendLine("Platform: ${info.platform}")
        appendLine("EFS block: ${info.efsPartition ?: "not found"}")
        appendLine("ip link : ${if (info.supportsIpLink) "supported (universal method)" else "NOT supported"}")
        appendLine("Candidate MAC paths:")
        info.macPaths.forEach { appendLine("  $it") }
        if (info.macPaths.isEmpty() || info.efsPartition == null) {
            appendLine()
            appendLine("NOTE: No EFS files found. The app will use")
            appendLine("'ip link set' (universal method) to change MAC.")
            appendLine("This works on all rooted devices but resets on reboot.")
            appendLine("Enable 'Boot MAC' to auto-change after reboot.")
        }
        appendLine("── End ──")
    }
}
