package com.example.macchanger

import android.content.Context
import android.content.SharedPreferences
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * WorkManager worker that generates a random locally-administered MAC,
 * writes it to EFS/persist, and cycles Wi-Fi — all in the background.
 */
class MacChangeWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val prefs = applicationContext.getSharedPreferences("mac_prefs", Context.MODE_PRIVATE)
        val macInfoPath = prefs.getString("mac_info_path", "") ?: ""
        val macCobPath = prefs.getString("mac_cob_path", "") ?: ""

        // Save current SSID before disconnecting for auto-reconnect
        val previousSsid = getCurrentSsid()

        val newMac = generateRandomMac()

        // Method 1: EFS/persist file write (if path is known)
        if (macInfoPath.isNotEmpty()) {
            val partition = when {
                macInfoPath.startsWith("/mnt/vendor/efs") -> "/mnt/vendor/efs"
                macInfoPath.startsWith("/persist") -> "/persist"
                macInfoPath.startsWith("/efs") -> "/efs"
                else -> null
            }

            if (partition != null) Shell.cmd("mount -o rw,remount $partition").exec()
            Shell.cmd("echo '$newMac' > $macInfoPath").exec()
            Shell.cmd("echo '$newMac' > $macCobPath").exec()
            Shell.cmd("chmod 660 $macInfoPath $macCobPath").exec()
            Shell.cmd("chown system:wifi $macInfoPath $macCobPath").exec()
        }

        // Method 2: Universal ip link set (works on ALL rooted devices)
        Shell.cmd("svc wifi disable").exec()
        Shell.cmd("sleep 1").exec()
        Shell.cmd("ip link set wlan0 down && ip link set wlan0 address $newMac && ip link set wlan0 up").exec()
        Shell.cmd("svc wifi enable").exec()
        Shell.cmd("sleep 3").exec()

        // Auto-reconnect to previous WiFi network
        if (previousSsid != null) {
            reconnectToSsid(previousSsid)
        }

        // Save to history
        val db = MacHistoryDatabase.getInstance(applicationContext)
        db.macHistoryDao().insert(MacEntry(macAddress = newMac))

        Result.success()
    }

    /** Read current SSID before disconnecting */
    private fun getCurrentSsid(): String? {
        val result = Shell.cmd("dumpsys wifi | grep 'mWifiInfo' | head -1").exec()
        val line = result.out.joinToString("\n")
        val match = "SSID: ([^,]+)".toRegex().find(line)
        val ssid = match?.groupValues?.get(1)?.trim()?.removeSurrounding("\"")
        return if (!ssid.isNullOrEmpty() && ssid != "<unknown ssid>" && ssid != "0x") ssid else null
    }

    /** Attempt to reconnect to the saved SSID after WiFi is re-enabled */
    private fun reconnectToSsid(ssid: String) {
        // Try wpa_cli first
        val netList = Shell.cmd("wpa_cli -i wlan0 list_networks").exec()
        val netId = netList.out.firstOrNull { it.contains(ssid) }
            ?.split("\\s+".toRegex())?.firstOrNull()?.trim()

        if (netId != null) {
            Shell.cmd("wpa_cli -i wlan0 select_network $netId").exec()
        } else {
            // Fallback for Android 12+
            Shell.cmd("cmd wifi connect-network \"$ssid\" open").exec()
        }
    }

    companion object {
        fun generateRandomMac(): String {
            val bytes = ByteArray(6).also { java.security.SecureRandom().nextBytes(it) }
            // Set locally administered bit (bit 1 of first octet) and clear multicast bit (bit 0)
            bytes[0] = ((bytes[0].toInt() or 0x02) and 0xFE).toByte()
            return bytes.joinToString(":") { "%02X".format(it) }
        }
    }
}
