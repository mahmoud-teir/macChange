package com.example.macchanger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.*

/**
 * Feature 19: Boot-time MAC Change
 * Automatically changes MAC address after device boot if enabled.
 */
class BootMacReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action != Intent.ACTION_BOOT_COMPLETED) return

        val prefs = context.getSharedPreferences("mac_prefs", Context.MODE_PRIVATE)
        val enabled = prefs.getBoolean(KEY_BOOT_MAC_ENABLED, false)
        if (!enabled) return

        val macInfoPath = prefs.getString("mac_info_path", "") ?: ""
        val macCobPath = prefs.getString("mac_cob_path", "") ?: ""
        if (macInfoPath.isEmpty()) return

        // Use goAsync() to get more time for the shell operations
        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val newMac = MacChangeWorker.generateRandomMac()

                val partition = when {
                    macInfoPath.startsWith("/mnt/vendor/efs") -> "/mnt/vendor/efs"
                    macInfoPath.startsWith("/persist") -> "/persist"
                    macInfoPath.startsWith("/efs") -> "/efs"
                    else -> null
                }

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
                    "svc wifi enable"
                ))

                for (cmd in commands) {
                    Shell.cmd(cmd).exec()
                }

                // Save to history
                val db = MacHistoryDatabase.getInstance(context)
                db.macHistoryDao().insert(MacEntry(macAddress = newMac))
            } finally {
                pendingResult.finish()
            }
        }
    }

    companion object {
        const val KEY_BOOT_MAC_ENABLED = "boot_mac_enabled"
    }
}
