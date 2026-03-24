package com.example.macchanger

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * MAC Changer – a root-only Android utility that reads, backs up,
 * changes, and restores the Wi-Fi MAC address stored in EFS/persist.
 *
 * Requirements:
 *   - Rooted device with Magisk / KernelSU
 *   - libsu (com.github.topjohnwu.libsu) for safe superuser shell execution
 *
 * Typical MAC file locations (Samsung / Qualcomm / generic):
 *   /mnt/vendor/efs/wifi/.mac.info   and   .mac.cob
 *   /persist/wifi/.mac.info           and   .mac.cob
 *   /efs/wifi/.mac.info               and   .mac.cob
 */
class MainActivity : AppCompatActivity() {

    // ── UI references ────────────────────────────────────────────────
    private lateinit var btnScan: Button
    private lateinit var btnBackup: Button
    private lateinit var btnChange: Button
    private lateinit var btnRestore: Button
    private lateinit var etNewMac: EditText
    private lateinit var tvLog: TextView
    private lateinit var scrollView: ScrollView

    // ── State ────────────────────────────────────────────────────────
    private var macInfoPath: String = ""
    private var macCobPath: String = ""
    private var backupPath: String = ""

    // Well-known paths where vendors store the Wi-Fi MAC
    private val knownPaths = listOf(
        "/mnt/vendor/efs/wifi/.mac.info",
        "/persist/wifi/.mac.info",
        "/efs/wifi/.mac.info"
    )

    // ── Lifecycle ────────────────────────────────────────────────────

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bind views
        btnScan = findViewById(R.id.btnScan)
        btnBackup = findViewById(R.id.btnBackup)
        btnChange = findViewById(R.id.btnChange)
        btnRestore = findViewById(R.id.btnRestore)
        etNewMac = findViewById(R.id.etNewMac)
        tvLog = findViewById(R.id.tvLog)
        scrollView = tvLog.parent as ScrollView

        // Configure libsu: redirect stderr so we capture error output too
        Shell.enableVerboseLogging = BuildConfig.DEBUG
        Shell.setDefaultBuilder(
            Shell.Builder.create()
                .setFlags(Shell.FLAG_REDIRECT_STDERR)
                .setTimeout(30)
        )

        // Check root on launch
        checkRootAccess()

        // Button listeners
        btnScan.setOnClickListener { locateMacFiles() }
        btnBackup.setOnClickListener { backupEfs() }
        btnChange.setOnClickListener { changeMacFromInput() }
        btnRestore.setOnClickListener { restoreEfs() }
    }

    // ── Root check ───────────────────────────────────────────────────

    private fun checkRootAccess() {
        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                Shell.cmd("id -u").exec()
            }
            if (result.isSuccess && result.out.firstOrNull()?.trim() == "0") {
                appendLog("[+] Root access granted")
            } else {
                appendLog("[!] Root access not available. App may not work.")
            }
        }
    }

    // ── Logging helper ───────────────────────────────────────────────

    private fun appendLog(msg: String) {
        runOnUiThread {
            tvLog.append("$msg\n")
            // Auto-scroll to bottom
            scrollView.post { scrollView.fullScroll(ScrollView.FOCUS_DOWN) }
        }
    }

    // ── Shell helper (runs a single command as root) ─────────────────

    /**
     * Execute a single shell command as root on Dispatchers.IO and
     * return the combined stdout. Errors are prefixed with "ERROR:".
     */
    private suspend fun runSu(cmd: String): String = withContext(Dispatchers.IO) {
        val result = Shell.cmd(cmd).exec()
        if (result.isSuccess) {
            result.out.joinToString("\n")
        } else {
            "ERROR: ${result.err.joinToString("\n")}"
        }
    }

    // ── 1. Scan for MAC files ────────────────────────────────────────

    private fun locateMacFiles() {
        appendLog("[*] Searching for MAC files...")

        lifecycleScope.launch {
            // First try a quick find
            val findResult = runSu("find / -name '.mac.info' -maxdepth 6 2>/dev/null | head -5")
            val foundPath = findResult.lines().firstOrNull { it.startsWith("/") }?.trim()

            if (!foundPath.isNullOrEmpty()) {
                macInfoPath = foundPath
                macCobPath = foundPath.replace(".mac.info", ".mac.cob")
                appendLog("[+] Found: $macInfoPath")
                // Show current MAC value
                val currentMac = runSu("cat $macInfoPath")
                appendLog("[*] Current MAC: ${currentMac.trim()}")
                return@launch
            }

            // Fallback: probe well-known paths
            for (path in knownPaths) {
                val exists = runSu("test -f $path && echo EXISTS")
                if (exists.trim() == "EXISTS") {
                    macInfoPath = path
                    macCobPath = path.replace(".mac.info", ".mac.cob")
                    appendLog("[+] Found: $macInfoPath")
                    val currentMac = runSu("cat $macInfoPath")
                    appendLog("[*] Current MAC: ${currentMac.trim()}")
                    return@launch
                }
            }

            appendLog("[!] Could not locate MAC files automatically.")
        }
    }

    // ── 2. Backup EFS partition ──────────────────────────────────────

    private fun backupEfs() {
        if (macInfoPath.isEmpty()) {
            appendLog("[!] Please scan for MAC files first.")
            return
        }

        val timestamp = System.currentTimeMillis()
        backupPath = "/sdcard/efs_backup_$timestamp.img"
        appendLog("[*] Creating backup to $backupPath ...")

        lifecycleScope.launch {
            val output = runSu("dd if=/dev/block/by-name/efs of=$backupPath")
            if (output.startsWith("ERROR")) {
                appendLog("[!] Backup failed: $output")
            } else {
                appendLog("[+] Backup created: $backupPath")
                val sizeInfo = runSu("ls -lh $backupPath")
                appendLog(sizeInfo)
            }
        }
    }

    // ── 3. Change MAC address ────────────────────────────────────────

    /** Validate colon-separated hex MAC like 02:AA:34:9F:22:11 */
    private fun isValidMac(mac: String): Boolean {
        return "^([0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}$".toRegex().matches(mac)
    }

    private fun changeMacFromInput() {
        val newMac = etNewMac.text.toString().trim()

        if (!isValidMac(newMac)) {
            appendLog("[!] Invalid MAC format. Use: XX:XX:XX:XX:XX:XX")
            return
        }
        if (macInfoPath.isEmpty()) {
            appendLog("[!] Please scan for MAC files first.")
            return
        }

        appendLog("[*] Changing MAC to $newMac ...")

        // Determine which partition to remount
        val partition = when {
            macInfoPath.startsWith("/mnt/vendor/efs") -> "/mnt/vendor/efs"
            macInfoPath.startsWith("/persist") -> "/persist"
            macInfoPath.startsWith("/efs") -> "/efs"
            else -> null
        }

        // Build the command sequence
        val commands = mutableListOf<String>()

        if (partition != null) {
            commands.add("mount -o rw,remount $partition")
        }

        commands.addAll(
            listOf(
                // Write new MAC to both files
                "echo '$newMac' > $macInfoPath",
                "echo '$newMac' > $macCobPath",
                // Fix permissions: owner=system, group=wifi, mode=rw-rw----
                "chmod 660 $macInfoPath $macCobPath",
                "chown system:wifi $macInfoPath $macCobPath",
                // Cycle Wi-Fi so the driver picks up the new address
                "svc wifi disable",
                "rm -rf /data/vendor/wifi/*",
                "rm -rf /data/misc/wifi/*",
                "sleep 2",
                "svc wifi enable",
                "sleep 3",
                // Read back the active MAC from the network interface
                "ip link show wlan0 | grep ether"
            )
        )

        lifecycleScope.launch {
            for (cmd in commands) {
                appendLog("> $cmd")
                val output = runSu(cmd)
                if (output.isNotBlank()) {
                    appendLog(output)
                }
            }
            appendLog("[+] MAC change sequence complete.")
        }
    }

    // ── 4. Restore EFS from backup ───────────────────────────────────

    private fun restoreEfs() {
        if (backupPath.isEmpty()) {
            appendLog("[!] No backup found. Please create a backup first.")
            return
        }

        appendLog("[*] Restoring EFS from $backupPath ...")

        lifecycleScope.launch {
            val output = runSu("dd if=$backupPath of=/dev/block/by-name/efs")
            if (output.startsWith("ERROR")) {
                appendLog("[!] Restore failed: $output")
            } else {
                appendLog("[+] Restore completed. Reboot recommended.")
            }
        }
    }
}
