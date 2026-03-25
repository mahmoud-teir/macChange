package com.example.macchanger

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
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
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * MAC Changer v3 – full-featured root utility.
 *
 * Features:
 *   1. MAC comparison before/after   2. Random MAC generation
 *   3. Original MAC saved on first scan   4. Confirmation dialogs
 *   5. MAC history (Room)   6. Scheduled auto-change (WorkManager)
 *   7. Backup file picker   8. Log export/share
 *   9. MAC Leak Detection (foreground service)
 *  10. OUI Vendor Lookup (offline)
 *  11. MAC Profiles (Room)
 *  12. Network info display
 *  13. Persistent notification
 *  14. Auto device/chipset detection
 *  15. EFS integrity check (checksum)
 *  16. WiFi auto-reconnect after MAC change
 *  17. Import/Export MAC profiles (JSON)
 */
class MainActivity : AppCompatActivity() {

    // ── UI ───────────────────────────────────────────────────────────
    private lateinit var btnScan: Button
    private lateinit var btnDetect: Button
    private lateinit var btnBackup: Button
    private lateinit var btnChange: Button
    private lateinit var btnRestore: Button
    private lateinit var btnRandom: Button
    private lateinit var btnHistory: Button
    private lateinit var btnProfiles: Button
    private lateinit var btnNetInfo: Button
    private lateinit var btnSchedule: Button
    private lateinit var btnMonitor: Button
    private lateinit var btnExport: Button
    private lateinit var etNewMac: EditText
    private lateinit var tvLog: TextView
    private lateinit var scrollView: ScrollView

    // ── State ────────────────────────────────────────────────────────
    private var macInfoPath: String = ""
    private var macCobPath: String = ""
    private var efsPartition: String? = null
    private var monitorRunning = false
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
        private const val KEY_EFS_PARTITION = "efs_partition"
    }

    // ── Lifecycle ────────────────────────────────────────────────────

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        db = MacHistoryDatabase.getInstance(this)

        bindViews()
        configureSu()
        restoreState()
        checkRootAccess()
        setListeners()
    }

    private fun bindViews() {
        btnScan = findViewById(R.id.btnScan)
        btnDetect = findViewById(R.id.btnDetect)
        btnBackup = findViewById(R.id.btnBackup)
        btnChange = findViewById(R.id.btnChange)
        btnRestore = findViewById(R.id.btnRestore)
        btnRandom = findViewById(R.id.btnRandom)
        btnHistory = findViewById(R.id.btnHistory)
        btnProfiles = findViewById(R.id.btnProfiles)
        btnNetInfo = findViewById(R.id.btnNetInfo)
        btnSchedule = findViewById(R.id.btnSchedule)
        btnMonitor = findViewById(R.id.btnMonitor)
        btnExport = findViewById(R.id.btnExport)
        etNewMac = findViewById(R.id.etNewMac)
        tvLog = findViewById(R.id.tvLog)
        scrollView = findViewById(R.id.scrollLog)
    }

    private fun configureSu() {
        Shell.enableVerboseLogging = BuildConfig.DEBUG
        Shell.setDefaultBuilder(
            Shell.Builder.create()
                .setFlags(Shell.FLAG_REDIRECT_STDERR)
                .setTimeout(30)
        )
    }

    private fun restoreState() {
        macInfoPath = prefs.getString(KEY_MAC_INFO_PATH, "") ?: ""
        macCobPath = prefs.getString(KEY_MAC_COB_PATH, "") ?: ""
        efsPartition = prefs.getString(KEY_EFS_PARTITION, null)

        val savedOriginal = prefs.getString(KEY_ORIGINAL_MAC, null)
        if (savedOriginal != null) {
            val vendor = OuiDatabase.lookup(savedOriginal)
            appendLog("[*] Saved original MAC: $savedOriginal ($vendor)")
        }
        if (macInfoPath.isNotEmpty()) {
            appendLog("[*] Saved MAC path: $macInfoPath")
        }
    }

    private fun setListeners() {
        btnScan.setOnClickListener { locateMacFiles() }
        btnDetect.setOnClickListener { detectDevice() }
        btnBackup.setOnClickListener { confirmBackup() }
        btnChange.setOnClickListener { confirmChange() }
        btnRestore.setOnClickListener { showBackupPicker() }
        btnRandom.setOnClickListener { generateAndFillRandomMac() }
        btnHistory.setOnClickListener { showHistory() }
        btnProfiles.setOnClickListener { showProfiles() }
        btnNetInfo.setOnClickListener { showNetworkInfo() }
        btnSchedule.setOnClickListener { showScheduleDialog() }
        btnMonitor.setOnClickListener { toggleMonitor() }
        btnExport.setOnClickListener { exportLog() }
    }

    // ── Root check ───────────────────────────────────────────────────

    private fun checkRootAccess() {
        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) { Shell.cmd("id -u").exec() }
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
        if (result.isSuccess) result.out.joinToString("\n")
        else "ERROR: ${result.err.joinToString("\n")}"
    }

    private fun isValidMac(mac: String): Boolean =
        "^([0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}$".toRegex().matches(mac)

    private suspend fun readActiveMac(): String {
        val output = runSu("ip link show wlan0")
        val match = "link/ether\\s+([0-9A-Fa-f:]{17})".toRegex().find(output)
        return match?.groupValues?.get(1)?.uppercase() ?: "unknown"
    }

    /** Resolve the mount partition from the mac file path */
    private fun resolvePartition(): String? = when {
        macInfoPath.startsWith("/mnt/vendor/efs") -> "/mnt/vendor/efs"
        macInfoPath.startsWith("/persist") -> "/persist"
        macInfoPath.startsWith("/efs") -> "/efs"
        else -> null
    }

    // ── Feature 14: Auto Device Detection ────────────────────────────

    private fun detectDevice() {
        appendLog("[*] Detecting device chipset...")
        lifecycleScope.launch {
            val info = DeviceDetector.detect()
            appendLog(DeviceDetector.formatReport(info))

            // Auto-probe the candidate paths
            for (path in info.macPaths) {
                val exists = runSu("test -f $path && echo EXISTS").trim()
                if (exists == "EXISTS") {
                    macInfoPath = path
                    macCobPath = path.replace(".mac.info", ".mac.cob")
                    efsPartition = info.efsPartition

                    prefs.edit()
                        .putString(KEY_MAC_INFO_PATH, macInfoPath)
                        .putString(KEY_MAC_COB_PATH, macCobPath)
                        .putString(KEY_EFS_PARTITION, efsPartition)
                        .apply()

                    appendLog("[+] Auto-detected MAC path: $macInfoPath")
                    val mac = runSu("cat $macInfoPath").trim()
                    appendLog("[*] MAC in file: $mac (${OuiDatabase.lookup(mac)})")

                    // Save original on first detect
                    if (!prefs.contains(KEY_ORIGINAL_MAC) && mac.isNotEmpty() && !mac.startsWith("ERROR")) {
                        prefs.edit().putString(KEY_ORIGINAL_MAC, mac).apply()
                        appendLog("[+] Original MAC saved: $mac")
                    }
                    return@launch
                }
            }
            appendLog("[!] No MAC files found at detected paths. Try manual scan.")
        }
    }

    // ── Scan ─────────────────────────────────────────────────────────

    private fun locateMacFiles() {
        appendLog("[*] Searching for MAC files...")
        lifecycleScope.launch {
            val findResult = runSu("find / -name '.mac.info' -maxdepth 6 2>/dev/null | head -5")
            val foundPath = findResult.lines().firstOrNull { it.startsWith("/") }?.trim()

            val path = if (!foundPath.isNullOrEmpty()) foundPath
            else knownPaths.firstOrNull { p ->
                runSu("test -f $p && echo EXISTS").trim() == "EXISTS"
            }

            if (path != null) {
                macInfoPath = path
                macCobPath = path.replace(".mac.info", ".mac.cob")

                prefs.edit()
                    .putString(KEY_MAC_INFO_PATH, macInfoPath)
                    .putString(KEY_MAC_COB_PATH, macCobPath)
                    .apply()

                appendLog("[+] Found: $macInfoPath")
                val fileMac = runSu("cat $macInfoPath").trim()
                val vendor = OuiDatabase.lookup(fileMac)
                appendLog("[*] MAC in file : $fileMac ($vendor)")

                val activeMac = readActiveMac()
                val activeVendor = OuiDatabase.lookup(activeMac)
                appendLog("[*] Active wlan0: $activeMac ($activeVendor)")

                if (!prefs.contains(KEY_ORIGINAL_MAC)) {
                    prefs.edit().putString(KEY_ORIGINAL_MAC, fileMac).apply()
                    appendLog("[+] Original MAC saved: $fileMac")
                }
            } else {
                appendLog("[!] Could not locate MAC files. Try 'Detect Device'.")
            }
        }
    }

    // ── Random MAC ───────────────────────────────────────────────────

    private fun generateAndFillRandomMac() {
        val mac = MacChangeWorker.generateRandomMac()
        etNewMac.setText(mac)
        appendLog("[*] Generated: $mac (${OuiDatabase.lookup(mac)})")
    }

    // ── Confirmation dialogs ─────────────────────────────────────────

    private fun confirmBackup() {
        if (macInfoPath.isEmpty()) { appendLog("[!] Please scan first."); return }
        AlertDialog.Builder(this)
            .setTitle(R.string.confirm_backup_title)
            .setMessage(R.string.confirm_backup_msg)
            .setPositiveButton(R.string.confirm_yes) { _, _ -> backupEfs() }
            .setNegativeButton(R.string.confirm_no, null)
            .show()
    }

    private fun confirmChange() {
        val newMac = etNewMac.text.toString().trim()
        if (!isValidMac(newMac)) { appendLog("[!] Invalid MAC. Use XX:XX:XX:XX:XX:XX"); return }
        if (macInfoPath.isEmpty()) { appendLog("[!] Please scan first."); return }
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
        val efsDev = efsPartition ?: "/dev/block/by-name/efs"
        appendLog("[*] Backup: $efsDev -> $path ...")

        lifecycleScope.launch {
            val output = runSu("dd if=$efsDev of=$path")
            if (output.startsWith("ERROR")) {
                appendLog("[!] Backup failed: $output")
            } else {
                appendLog("[+] Backup created: $path")
                val sizeInfo = runSu("ls -lh $path")
                appendLog(sizeInfo)
            }
        }
    }

    // ── Feature 15: EFS Integrity Check ──────────────────────────────

    /** Calculate MD5 checksum of a file via shell */
    private suspend fun md5sum(path: String): String {
        val output = runSu("md5sum $path")
        return output.split("\\s+".toRegex()).firstOrNull() ?: "error"
    }

    // ── Change MAC with before/after + integrity check ───────────────

    // ── Feature 16: WiFi Auto-Reconnect helpers ────────────────────

    /** Read current SSID before disconnecting */
    private suspend fun getCurrentSsid(): String? {
        val line = runSu("dumpsys wifi | grep 'mWifiInfo' | head -1")
        val match = "SSID: ([^,]+)".toRegex().find(line)
        val ssid = match?.groupValues?.get(1)?.trim()?.removeSurrounding("\"")
        return if (!ssid.isNullOrEmpty() && ssid != "<unknown ssid>" && ssid != "0x") ssid else null
    }

    /** Attempt to reconnect to the saved SSID after WiFi is re-enabled */
    private suspend fun reconnectToSsid(ssid: String) {
        appendLog("[*] Reconnecting to '$ssid' ...")
        // Get the network ID for the saved SSID
        val netId = runSu("wpa_cli -i wlan0 list_networks | grep '$ssid'").let { output ->
            output.lines().firstOrNull { it.contains(ssid) }
                ?.split("\\s+".toRegex())?.firstOrNull()?.trim()
        }

        if (netId != null) {
            runSu("wpa_cli -i wlan0 select_network $netId")
            appendLog("[+] Sent reconnect command (network #$netId)")
        } else {
            // Fallback: use cmd wifi connect on Android 12+
            runSu("cmd wifi connect-network \"$ssid\" open")
            appendLog("[*] Fallback reconnect attempted via cmd wifi")
        }

        // Wait briefly and check connection
        runSu("sleep 3")
        val newSsid = getCurrentSsid()
        if (newSsid != null) {
            appendLog("[+] Connected to: $newSsid")
        } else {
            appendLog("[!] Not connected yet. WiFi may still be associating.")
        }
    }

    // ── Change MAC with before/after + integrity check ───────────────

    private fun changeMac(newMac: String) {
        lifecycleScope.launch {
            val macBefore = readActiveMac()
            appendLog("[*] MAC before: $macBefore (${OuiDatabase.lookup(macBefore)})")

            // Save current SSID before disconnecting
            val previousSsid = getCurrentSsid()
            if (previousSsid != null) {
                appendLog("[*] Current SSID: $previousSsid (will auto-reconnect)")
            }

            // Integrity: checksum before
            val md5Before = md5sum(macInfoPath)
            appendLog("[*] Checksum before: $md5Before")

            appendLog("[*] Changing MAC to $newMac ...")

            val partition = resolvePartition()
            val commands = mutableListOf<String>()
            if (partition != null) commands.add("mount -o rw,remount $partition")
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

            // Integrity: checksum after – verify the file was written correctly
            val md5After = md5sum(macInfoPath)
            appendLog("[*] Checksum after : $md5After")

            // Verify file content matches requested MAC
            val fileContent = runSu("cat $macInfoPath").trim()
            if (fileContent.equals(newMac, ignoreCase = true)) {
                appendLog("[+] File integrity OK: content matches requested MAC")
            } else {
                appendLog("[!] INTEGRITY WARNING: file content ($fileContent) != requested ($newMac)")
            }

            // Compare active MAC
            val macAfter = readActiveMac()
            appendLog("[*] MAC after : $macAfter (${OuiDatabase.lookup(macAfter)})")

            appendLog("── Comparison ──")
            appendLog("  Before : $macBefore")
            appendLog("  After  : $macAfter")
            appendLog("  Target : $newMac")
            if (macAfter.equals(newMac, ignoreCase = true)) {
                appendLog("[+] SUCCESS: MAC changed!")
            } else {
                appendLog("[!] Active MAC differs from target (may be Android MAC randomization).")
            }

            // Feature 16: Auto-reconnect to previous WiFi network
            if (previousSsid != null) {
                reconnectToSsid(previousSsid)
            }

            // Save to history
            withContext(Dispatchers.IO) { db.macHistoryDao().insert(MacEntry(macAddress = newMac)) }
            appendLog("[*] Saved to history.")

            // Update monitor notification if running
            if (monitorRunning) {
                startMonitorService(macAfter)
            }
        }
    }

    // ── Backup picker ────────────────────────────────────────────────

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

            val names = files.map { it.substringAfterLast("/") }.toTypedArray()
            withContext(Dispatchers.Main) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(R.string.select_backup_title)
                    .setItems(names) { _, which -> confirmRestore(files[which]) }
                    .setNegativeButton(R.string.confirm_no, null)
                    .show()
            }
        }
    }

    // ── Restore EFS ──────────────────────────────────────────────────

    private fun restoreEfs(filePath: String) {
        val efsDev = efsPartition ?: "/dev/block/by-name/efs"
        appendLog("[*] Restoring: $filePath -> $efsDev ...")

        lifecycleScope.launch {
            // Integrity check of backup file
            val backupMd5 = md5sum(filePath)
            appendLog("[*] Backup checksum: $backupMd5")

            val output = runSu("dd if=$filePath of=$efsDev")
            if (output.startsWith("ERROR")) {
                appendLog("[!] Restore failed: $output")
            } else {
                appendLog("[+] Restore completed. Reboot recommended.")
            }
        }
    }

    // ── History ──────────────────────────────────────────────────────

    private fun showHistory() {
        lifecycleScope.launch {
            val entries = withContext(Dispatchers.IO) { db.macHistoryDao().getAll() }
            if (entries.isEmpty()) { appendLog("[*] No MAC history yet."); return@launch }

            val text = buildString {
                appendLine("── MAC Change History ──")
                entries.forEachIndexed { i, e ->
                    val vendor = OuiDatabase.lookup(e.macAddress)
                    appendLine("${i + 1}. ${e.macAddress} ($vendor)  [${e.formattedTime()}]")
                }
                appendLine("── ${entries.size} entries ──")
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

    // ── Feature 11: MAC Profiles ─────────────────────────────────────

    private fun showProfiles() {
        lifecycleScope.launch {
            val profiles = withContext(Dispatchers.IO) { db.macProfileDao().getAll() }

            withContext(Dispatchers.Main) {
                val items = mutableListOf("+ Save current MAC as profile")
                profiles.forEach { p -> items.add("${p.name}: ${p.macAddress}") }

                AlertDialog.Builder(this@MainActivity)
                    .setTitle(R.string.profiles_title)
                    .setItems(items.toTypedArray()) { _, which ->
                        if (which == 0) {
                            showSaveProfileDialog()
                        } else {
                            val profile = profiles[which - 1]
                            showProfileActions(profile)
                        }
                    }
                    .setPositiveButton(R.string.export_profiles) { _, _ -> exportProfiles() }
                    .setNeutralButton(R.string.import_profiles) { _, _ -> importProfiles() }
                    .setNegativeButton(R.string.confirm_no, null)
                    .show()
            }
        }
    }

    private fun showSaveProfileDialog() {
        val mac = etNewMac.text.toString().trim().let {
            if (it.isEmpty() || !isValidMac(it)) null else it
        }

        val input = EditText(this).apply {
            hint = getString(R.string.profile_name_hint)
            inputType = InputType.TYPE_CLASS_TEXT
        }

        AlertDialog.Builder(this)
            .setTitle(R.string.save_profile)
            .setMessage("MAC: ${mac ?: "(enter a MAC first)"}")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val name = input.text.toString().trim()
                if (name.isEmpty()) { appendLog("[!] Profile name cannot be empty."); return@setPositiveButton }
                if (mac == null) { appendLog("[!] Enter a valid MAC first."); return@setPositiveButton }
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        db.macProfileDao().insert(MacProfile(name = name, macAddress = mac))
                    }
                    appendLog("[+] Profile saved: $name -> $mac")
                }
            }
            .setNegativeButton(R.string.confirm_no, null)
            .show()
    }

    private fun showProfileActions(profile: MacProfile) {
        AlertDialog.Builder(this)
            .setTitle(profile.name)
            .setMessage("MAC: ${profile.macAddress}\nCreated: ${profile.formattedTime()}")
            .setPositiveButton(R.string.apply) { _, _ ->
                etNewMac.setText(profile.macAddress)
                appendLog("[*] Profile '${profile.name}' loaded: ${profile.macAddress}")
            }
            .setNeutralButton(R.string.delete) { _, _ ->
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) { db.macProfileDao().delete(profile) }
                    appendLog("[*] Profile '${profile.name}' deleted.")
                }
            }
            .setNegativeButton(R.string.confirm_no, null)
            .show()
    }

    // ── Feature 17: Import/Export Profiles ──────────────────────────

    private fun exportProfiles() {
        lifecycleScope.launch {
            val profiles = withContext(Dispatchers.IO) { db.macProfileDao().getAll() }
            if (profiles.isEmpty()) {
                appendLog("[!] No profiles to export.")
                return@launch
            }

            val jsonArray = JSONArray()
            for (p in profiles) {
                val obj = JSONObject().apply {
                    put("name", p.name)
                    put("mac_address", p.macAddress)
                    put("created_at", p.createdAt)
                }
                jsonArray.put(obj)
            }

            val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            val fileName = "mac_profiles_${sdf.format(Date())}.json"
            val file = File("/sdcard/$fileName")
            file.writeText(jsonArray.toString(2))

            appendLog("[+] Exported ${profiles.size} profiles to /sdcard/$fileName")

            // Share via intent
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/json"
                putExtra(Intent.EXTRA_TEXT, jsonArray.toString(2))
                putExtra(Intent.EXTRA_SUBJECT, "MAC Changer Profiles")
            }
            startActivity(Intent.createChooser(shareIntent, "Export Profiles"))
        }
    }

    private fun importProfiles() {
        lifecycleScope.launch {
            // List all profile JSON files on /sdcard
            val listOutput = runSu("ls -1 /sdcard/mac_profiles_*.json 2>/dev/null")
            val files = listOutput.lines()
                .map { it.trim() }
                .filter { it.startsWith("/sdcard/") && it.endsWith(".json") }

            if (files.isEmpty()) {
                appendLog("[!] No profile files found on /sdcard/")
                appendLog("[*] Expected format: /sdcard/mac_profiles_*.json")
                return@launch
            }

            val names = files.map { it.substringAfterLast("/") }.toTypedArray()
            withContext(Dispatchers.Main) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(R.string.import_profiles)
                    .setItems(names) { _, which -> doImportProfiles(files[which]) }
                    .setNegativeButton(R.string.confirm_no, null)
                    .show()
            }
        }
    }

    private fun doImportProfiles(filePath: String) {
        lifecycleScope.launch {
            try {
                val content = runSu("cat $filePath").trim()
                val jsonArray = JSONArray(content)
                var imported = 0

                withContext(Dispatchers.IO) {
                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        val name = obj.getString("name")
                        val mac = obj.getString("mac_address")
                        val createdAt = obj.optLong("created_at", System.currentTimeMillis())

                        if (isValidMac(mac)) {
                            db.macProfileDao().insert(
                                MacProfile(name = name, macAddress = mac, createdAt = createdAt)
                            )
                            imported++
                        }
                    }
                }

                appendLog("[+] Imported $imported/${jsonArray.length()} profiles from ${filePath.substringAfterLast("/")}")
            } catch (e: Exception) {
                appendLog("[!] Import failed: ${e.message}")
            }
        }
    }

    // ── Feature 12: Network Info ─────────────────────────────────────

    private fun showNetworkInfo() {
        appendLog("[*] Fetching network info...")
        lifecycleScope.launch {
            val mac = readActiveMac()
            val vendor = OuiDatabase.lookup(mac)

            // SSID
            val ssid = runSu("dumpsys wifi | grep 'mWifiInfo' | head -1").let { line ->
                "SSID: ([^,]+)".toRegex().find(line)?.groupValues?.get(1) ?: "N/A"
            }

            // IP address
            val ip = runSu("ip addr show wlan0 | grep 'inet '").let { line ->
                "inet\\s+([0-9.]+)".toRegex().find(line)?.groupValues?.get(1) ?: "N/A"
            }

            // Gateway
            val gateway = runSu("ip route | grep default | grep wlan0").let { line ->
                "via\\s+([0-9.]+)".toRegex().find(line)?.groupValues?.get(1) ?: "N/A"
            }

            // DNS
            val dns = runSu("getprop net.dns1").trim().ifEmpty { "N/A" }
            val dns2 = runSu("getprop net.dns2").trim().ifEmpty { "N/A" }

            // Link speed
            val linkSpeed = runSu("iw dev wlan0 link | grep 'tx bitrate'").let { line ->
                "tx bitrate:\\s+(.+)".toRegex().find(line)?.groupValues?.get(1) ?: "N/A"
            }

            val info = buildString {
                appendLine("── Network Info ──")
                appendLine("MAC     : $mac ($vendor)")
                appendLine("SSID    : $ssid")
                appendLine("IP      : $ip")
                appendLine("Gateway : $gateway")
                appendLine("DNS     : $dns / $dns2")
                appendLine("Speed   : $linkSpeed")
                appendLine("── End ──")
            }

            appendLog(info)
        }
    }

    // ── Feature 9/13: Monitor Service (leak detection + notification) ─

    private fun toggleMonitor() {
        if (monitorRunning) {
            stopService(Intent(this, MacMonitorService::class.java))
            monitorRunning = false
            btnMonitor.text = getString(R.string.monitor_toggle)
            appendLog("[*] Monitor stopped.")
        } else {
            lifecycleScope.launch {
                val mac = readActiveMac()
                startMonitorService(mac)
                monitorRunning = true
                btnMonitor.text = "Stop Monitor"
                appendLog("[+] Monitor started. Checking MAC every 60s.")
                appendLog("    Persistent notification active.")
                appendLog("    Leak detection: alerts if original MAC reappears.")
            }
        }
    }

    private fun startMonitorService(currentMac: String) {
        val intent = Intent(this, MacMonitorService::class.java).apply {
            putExtra(MacMonitorService.EXTRA_MAC, currentMac)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    // ── Scheduled auto-change ────────────────────────────────────────

    private fun showScheduleDialog() {
        val workManager = WorkManager.getInstance(applicationContext)
        val input = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_NUMBER
            hint = "Minutes (15-1440)"
        }

        AlertDialog.Builder(this)
            .setTitle(R.string.schedule_title)
            .setMessage(R.string.schedule_msg)
            .setView(input)
            .setPositiveButton(R.string.schedule_start) { _, _ ->
                val minutes = input.text.toString().toLongOrNull()
                if (minutes == null || minutes < 15 || minutes > 1440) {
                    appendLog("[!] Invalid interval (15-1440)."); return@setPositiveButton
                }
                scheduleAutoChange(minutes)
            }
            .setNeutralButton(R.string.stop_schedule) { _, _ ->
                workManager.cancelAllWorkByTag(WORK_TAG)
                appendLog("[*] Schedule stopped.")
            }
            .setNegativeButton(R.string.confirm_no, null)
            .show()
    }

    private fun scheduleAutoChange(intervalMinutes: Long) {
        if (macInfoPath.isEmpty()) { appendLog("[!] Please scan first."); return }

        val workManager = WorkManager.getInstance(applicationContext)
        workManager.cancelAllWorkByTag(WORK_TAG)

        val request = PeriodicWorkRequestBuilder<MacChangeWorker>(
            intervalMinutes, TimeUnit.MINUTES
        )
            .addTag(WORK_TAG)
            .setConstraints(Constraints.Builder().setRequiresBatteryNotLow(true).build())
            .build()

        workManager.enqueueUniquePeriodicWork(WORK_TAG, ExistingPeriodicWorkPolicy.REPLACE, request)
        appendLog("[+] Scheduled: MAC changes every $intervalMinutes min.")
    }

    // ── Export log ───────────────────────────────────────────────────

    private fun exportLog() {
        val logText = tvLog.text.toString()
        if (logText.isBlank()) { appendLog("[!] Nothing to export."); return }

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
