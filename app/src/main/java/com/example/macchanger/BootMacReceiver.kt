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

        // Use goAsync() to get more time for the shell operations
        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val newMac = MacChangeWorker.generateRandomMac()

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
