package com.example.macchanger

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * MAC Changer v2 – root-only Android utility with:
 *   1. MAC comparison before/after change
 *   2. Random MAC generation (locally administered)
 *   3. Original MAC saved on first scan (SharedPreferences)
 *   4. Confirmation dialogs before dangerous operations
 *   5. MAC history (Room database)
 *   6. Scheduled auto MAC change (WorkManager)
 *   7. Backup file picker
 *   8. Log export / share
 */
class MainActivity : AppCompatActivity() {

    // ── UI ───────────────────────────────────────────────────────────
    private lateinit var btnScan: Button
    private lateinit var btnBackup: Button
    private lateinit var btnChange: Button
    private lateinit var btnRestore: Button
    private lateinit var btnRandom: Button
    private lateinit var btnHistory: Button
    private lateinit var btnSchedule: Button
    private lateinit var btnExport: Button
    private lateinit var etNewMac: EditText
    private lateinit var tvLog: TextView
    private lateinit var scrollView: ScrollView

    // ── State ────────────────────────────────────────────────────────
    private var macInfoPath: String = ""
    private var macCobPath: String = ""
    private lateinit var prefs: SharedPreferences
    private lateinit var db: MacHistoryDatabase

    private val knownPaths = listOf(
        "/mnt/vendor/efs/wifi/.mac.info",
        "/persist/wifi/.mac.info",
        "/efs/wifi/.mac.info"
    )

    companion object {
        private const val WORK_TAG = "mac_auto_change"
        private const val PREF_NAME = "mac_prefs"
        private const val KEY_ORIGINAL_MAC = "original_mac"
        private const val KEY_MAC_INFO_PATH = "mac_info_path"
        private const val KEY_MAC_COB_PATH = "mac_cob_path"
    }

    // ── Lifecycle ────────────────────────────────────────────────────

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        db = MacHistoryDatabase.getInstance(this)

        // Bind views
        btnScan = findViewById(R.id.btnScan)
        btnBackup = findViewById(R.id.btnBackup)
        btnChange = findViewById(R.id.btnChange)
        btnRestore = findViewById(R.id.btnRestore)
        btnRandom = findViewById(R.id.btnRandom)
        btnHistory = findViewById(R.id.btnHistory)
        btnSchedule = findViewById(R.id.btnSchedule)
        btnExport = findViewById(R.id.btnExport)
        etNewMac = findViewById(R.id.etNewMac)
        tvLog = findViewById(R.id.tvLog)
        scrollView = findViewById(R.id.scrollLog)

        // Configure libsu
        Shell.enableVerboseLogging = BuildConfig.DEBUG
        Shell.setDefaultBuilder(
            Shell.Builder.create()
                .setFlags(Shell.FLAG_REDIRECT_STDERR)
                .setTimeout(30)
        )

        // Restore saved paths
        macInfoPath = prefs.getString(KEY_MAC_INFO_PATH, "") ?: ""
        macCobPath = prefs.getString(KEY_MAC_COB_PATH, "") ?: ""

        checkRootAccess()

        // Show saved original MAC if we have it
        val savedOriginal = prefs.getString(KEY_ORIGINAL_MAC, null)
        if (savedOriginal != null) {
            appendLog("[*] Saved original MAC: $savedOriginal")
        }
        if (macInfoPath.isNotEmpty()) {
            appendLog("[*] Saved MAC path: $macInfoPath")
        }

        // Button listeners
        btnScan.setOnClickListener { locateMacFiles() }
        btnBackup.setOnClickListener { confirmBackup() }
        btnChange.setOnClickListener { confirmChange() }
        btnRestore.setOnClickListener { showBackupPicker() }
        btnRandom.setOnClickListener { generateAndFillRandomMac() }
        btnHistory.setOnClickListener { showHistory() }
        btnSchedule.setOnClickListener { showScheduleDialog() }
        btnExport.setOnClickListener { exportLog() }
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

    // ── Helpers ──────────────────────────────────────────────────────

    private fun appendLog(msg: String) {
        runOnUiThread {
            tvLog.append("$msg\n")
            scrollView.post { scrollView.fullScroll(ScrollView.FOCUS_DOWN) }
        }
    }

    private suspend fun runSu(cmd: String): String = withContext(Dispatchers.IO) {
        val result = Shell.cmd(cmd).exec()
        if (result.isSuccess) {
            result.out.joinToString("\n")
        } else {
            "ERROR: ${result.err.joinToString("\n")}"
        }
    }

    private fun isValidMac(mac: String): Boolean {
        return "^([0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}$".toRegex().matches(mac)
    }

    /** Read the current active MAC from wlan0 interface */
    private suspend fun readActiveMac(): String {
        val output = runSu("ip link show wlan0")
        // Parse line like "    link/ether aa:bb:cc:dd:ee:ff brd ff:ff:ff:ff:ff:ff"
        val match = "link/ether\\s+([0-9A-Fa-f:]{17})".toRegex().find(output)
        return match?.groupValues?.get(1)?.uppercase() ?: "unknown"
    }

    // ── Feature 1: Scan with original MAC saving ─────────────────────

    private fun locateMacFiles() {
        appendLog("[*] Searching for MAC files...")

        lifecycleScope.launch {
            // Try find first
            val findResult = runSu("find / -name '.mac.info' -maxdepth 6 2>/dev/null | head -5")
            val foundPath = findResult.lines().firstOrNull { it.startsWith("/") }?.trim()

            val path = if (!foundPath.isNullOrEmpty()) {
                foundPath
            } else {
                // Fallback: probe known paths
                knownPaths.firstOrNull { p ->
                    runSu("test -f $p && echo EXISTS").trim() == "EXISTS"
                }
            }

            if (path != null) {
                macInfoPath = path
                macCobPath = path.replace(".mac.info", ".mac.cob")

                // Persist paths for WorkManager
                prefs.edit()
                    .putString(KEY_MAC_INFO_PATH, macInfoPath)
                    .putString(KEY_MAC_COB_PATH, macCobPath)
                    .apply()

                appendLog("[+] Found: $macInfoPath")

                // Read current MAC from file
                val fileMac = runSu("cat $macInfoPath").trim()
                appendLog("[*] MAC in file : $fileMac")

                // Read active MAC from interface
                val activeMac = readActiveMac()
                appendLog("[*] Active wlan0: $activeMac")

                // Feature 3: Save original MAC on first scan
                if (!prefs.contains(KEY_ORIGINAL_MAC)) {
                    prefs.edit().putString(KEY_ORIGINAL_MAC, fileMac).apply()
                    appendLog("[+] Original MAC saved: $fileMac")
                }
            } else {
                appendLog("[!] Could not locate MAC files automatically.")
            }
        }
    }

    // ── Feature 2: Random MAC generation ─────────────────────────────

    private fun generateAndFillRandomMac() {
        val mac = MacChangeWorker.generateRandomMac()
        etNewMac.setText(mac)
        appendLog("[*] Generated random MAC: $mac")
    }

    // ── Feature 4: Confirmation dialogs ──────────────────────────────

    private fun confirmBackup() {
        if (macInfoPath.isEmpty()) {
            appendLog("[!] Please scan for MAC files first.")
            return
        }
        AlertDialog.Builder(this)
            .setTitle(R.string.confirm_backup_title)
            .setMessage(R.string.confirm_backup_msg)
            .setPositiveButton(R.string.confirm_yes) { _, _ -> backupEfs() }
            .setNegativeButton(R.string.confirm_no, null)
            .show()
    }

    private fun confirmChange() {
        val newMac = etNewMac.text.toString().trim()
        if (!isValidMac(newMac)) {
            appendLog("[!] Invalid MAC format. Use: XX:XX:XX:XX:XX:XX")
            return
        }
        if (macInfoPath.isEmpty()) {
            appendLog("[!] Please scan for MAC files first.")
            return
        }
        AlertDialog.Builder(this)
            .setTitle(R.string.confirm_change_title)
            .setMessage(getString(R.string.confirm_change_msg, newMac))
            .setPositiveButton(R.string.confirm_yes) { _, _ -> changeMac(newMac) }
            .setNegativeButton(R.string.confirm_no, null)
            .show()
    }

    private fun confirmRestore(filePath: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.confirm_restore_title)
            .setMessage(getString(R.string.confirm_restore_msg, filePath))
            .setPositiveButton(R.string.confirm_yes) { _, _ -> restoreEfs(filePath) }
            .setNegativeButton(R.string.confirm_no, null)
            .show()
    }

    // ── Backup EFS ───────────────────────────────────────────────────

    private fun backupEfs() {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val timestamp = sdf.format(Date())
        val path = "/sdcard/efs_backup_$timestamp.img"
        appendLog("[*] Creating backup to $path ...")

        lifecycleScope.launch {
            val output = runSu("dd if=/dev/block/by-name/efs of=$path")
            if (output.startsWith("ERROR")) {
                appendLog("[!] Backup failed: $output")
            } else {
                appendLog("[+] Backup created: $path")
                val sizeInfo = runSu("ls -lh $path")
                appendLog(sizeInfo)
            }
        }
    }

    // ── Feature 1: Change MAC with before/after comparison ───────────

    private fun changeMac(newMac: String) {
        lifecycleScope.launch {
            // Read MAC BEFORE change
            val macBefore = readActiveMac()
            appendLog("[*] MAC before: $macBefore")
            appendLog("[*] Changing MAC to $newMac ...")

            val partition = when {
                macInfoPath.startsWith("/mnt/vendor/efs") -> "/mnt/vendor/efs"
                macInfoPath.startsWith("/persist") -> "/persist"
                macInfoPath.startsWith("/efs") -> "/efs"
                else -> null
            }

            val commands = mutableListOf<String>()
            if (partition != null) {
                commands.add("mount -o rw,remount $partition")
            }
            commands.addAll(listOf(
                "echo '$newMac' > $macInfoPath",
                "echo '$newMac' > $macCobPath",
                "chmod 660 $macInfoPath $macCobPath",
                "chown system:wifi $macInfoPath $macCobPath",
                "svc wifi disable",
                "rm -rf /data/vendor/wifi/*",
                "rm -rf /data/misc/wifi/*",
                "sleep 2",
                "svc wifi enable",
                "sleep 3"
            ))

            for (cmd in commands) {
                appendLog("> $cmd")
                val output = runSu(cmd)
                if (output.isNotBlank()) appendLog(output)
            }

            // Read MAC AFTER change
            val macAfter = readActiveMac()
            appendLog("[*] MAC after : $macAfter")

            // Comparison
            if (macAfter.equals(newMac, ignoreCase = true)) {
                appendLog("[+] SUCCESS: MAC changed successfully!")
            } else {
                appendLog("[!] WARNING: Active MAC ($macAfter) differs from requested ($newMac).")
                appendLog("    This may be due to MAC randomization (Android 12+).")
            }

            // Feature 5: Save to history
            withContext(Dispatchers.IO) {
                db.macHistoryDao().insert(MacEntry(macAddress = newMac))
            }
            appendLog("[*] Saved to history.")
        }
    }

    // ── Feature 7: Backup file picker ────────────────────────────────

    private fun showBackupPicker() {
        lifecycleScope.launch {
            val listOutput = runSu("ls -1 /sdcard/efs_backup_*.img 2>/dev/null")
            val files = listOutput.lines()
                .map { it.trim() }
                .filter { it.startsWith("/sdcard/") && it.endsWith(".img") }

            if (files.isEmpty()) {
                appendLog("[!] No backup files found on /sdcard/")
                return@launch
            }

            // Show file names only in the dialog
            val names = files.map { it.substringAfterLast("/") }.toTypedArray()

            withContext(Dispatchers.Main) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(R.string.select_backup_title)
                    .setItems(names) { _, which ->
                        confirmRestore(files[which])
                    }
                    .setNegativeButton(R.string.confirm_no, null)
                    .show()
            }
        }
    }

    // ── Restore EFS ──────────────────────────────────────────────────

    private fun restoreEfs(filePath: String) {
        appendLog("[*] Restoring EFS from $filePath ...")

        lifecycleScope.launch {
            val output = runSu("dd if=$filePath of=/dev/block/by-name/efs")
            if (output.startsWith("ERROR")) {
                appendLog("[!] Restore failed: $output")
            } else {
                appendLog("[+] Restore completed. Reboot recommended.")
            }
        }
    }

    // ── Feature 5: History viewer ────────────────────────────────────

    private fun showHistory() {
        lifecycleScope.launch {
            val entries = withContext(Dispatchers.IO) {
                db.macHistoryDao().getAll()
            }

            if (entries.isEmpty()) {
                appendLog("[*] No MAC history yet.")
                return@launch
            }

            val text = buildString {
                appendLine("── MAC Change History ──")
                entries.forEachIndexed { i, e ->
                    appendLine("${i + 1}. ${e.macAddress}  [${e.formattedTime()}]")
                }
                appendLine("── End ──")
            }

            withContext(Dispatchers.Main) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(getString(R.string.view_history))
                    .setMessage(text)
                    .setPositiveButton("OK", null)
                    .show()
            }

            appendLog(text)
        }
    }

    // ── Feature 6: Scheduled auto-change via WorkManager ─────────────

    private fun showScheduleDialog() {
        // Check if a schedule is already active
        val workManager = WorkManager.getInstance(applicationContext)

        val input = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_NUMBER
            hint = "Minutes (15–1440)"
        }

        AlertDialog.Builder(this)
            .setTitle(R.string.schedule_title)
            .setMessage(R.string.schedule_msg)
            .setView(input)
            .setPositiveButton(R.string.schedule_start) { _, _ ->
                val minutes = input.text.toString().toLongOrNull()
                if (minutes == null || minutes < 15 || minutes > 1440) {
                    appendLog("[!] Invalid interval. Must be 15–1440 minutes.")
                    return@setPositiveButton
                }
                scheduleAutoChange(minutes)
            }
            .setNeutralButton(R.string.stop_schedule) { _, _ ->
                workManager.cancelAllWorkByTag(WORK_TAG)
                appendLog("[*] Auto MAC change schedule stopped.")
            }
            .setNegativeButton(R.string.confirm_no, null)
            .show()
    }

    private fun scheduleAutoChange(intervalMinutes: Long) {
        if (macInfoPath.isEmpty()) {
            appendLog("[!] Please scan for MAC files first.")
            return
        }

        val workManager = WorkManager.getInstance(applicationContext)

        // Cancel any existing schedule
        workManager.cancelAllWorkByTag(WORK_TAG)

        val request = PeriodicWorkRequestBuilder<MacChangeWorker>(
            intervalMinutes, TimeUnit.MINUTES
        )
            .addTag(WORK_TAG)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_TAG,
            ExistingPeriodicWorkPolicy.REPLACE,
            request
        )

        appendLog("[+] Scheduled: MAC will change every $intervalMinutes minutes.")
        appendLog("    (Minimum interval enforced by WorkManager is 15 min)")
    }

    // ── Feature 8: Export / share log ────────────────────────────────

    private fun exportLog() {
        val logText = tvLog.text.toString()
        if (logText.isBlank()) {
            appendLog("[!] Nothing to export.")
            return
        }

        // Write to a temp file and share
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val fileName = "mac_changer_log_${sdf.format(Date())}.txt"

        val file = File(getExternalFilesDir(null), fileName)
        file.writeText(logText)

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, logText)
            putExtra(Intent.EXTRA_SUBJECT, "MAC Changer Log")
        }
        startActivity(Intent.createChooser(shareIntent, "Export Log"))

        appendLog("[+] Log exported: ${file.absolutePath}")
    }
}
