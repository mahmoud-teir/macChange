package com.example.macchanger

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.*

/**
 * Foreground service that:
 * 1. Shows the current MAC address in a persistent notification
 * 2. Periodically checks if the MAC has leaked back to the original
 */
class MacMonitorService : Service() {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var monitorJob: Job? = null

    companion object {
        const val CHANNEL_ID = "mac_monitor_channel"
        const val NOTIFICATION_ID = 1001
        const val LEAK_CHANNEL_ID = "mac_leak_channel"
        const val LEAK_NOTIFICATION_ID = 1002
        const val ACTION_UPDATE = "com.example.macchanger.UPDATE_MAC"
        const val EXTRA_MAC = "current_mac"
        private const val CHECK_INTERVAL_MS = 60_000L // 1 minute
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val currentMac = intent?.getStringExtra(EXTRA_MAC) ?: "Unknown"

        // Start as foreground with current MAC
        val notification = buildMacNotification(currentMac)
        startForeground(NOTIFICATION_ID, notification)

        // Start leak monitoring
        startLeakMonitoring()

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        monitorJob?.cancel()
        scope.cancel()
        super.onDestroy()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val macChannel = NotificationChannel(
                CHANNEL_ID,
                "MAC Address Monitor",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows the current active MAC address"
                setShowBadge(false)
            }

            val leakChannel = NotificationChannel(
                LEAK_CHANNEL_ID,
                "MAC Leak Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Alerts when original MAC address is detected"
                enableVibration(true)
            }

            val nm = getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(macChannel)
            nm.createNotificationChannel(leakChannel)
        }
    }

    private fun buildMacNotification(mac: String): Notification {
        val vendor = OuiDatabase.lookup(mac)

        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Active MAC: $mac")
            .setContentText("Vendor: $vendor")
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    /**
     * Feature 3 (MAC Leak Detection):
     * Periodically reads wlan0 MAC and compares to original.
     * If the original MAC reappears, send a high-priority alert.
     */
    private fun startLeakMonitoring() {
        monitorJob?.cancel()
        monitorJob = scope.launch {
            val prefs = getSharedPreferences("mac_prefs", Context.MODE_PRIVATE)

            while (isActive) {
                delay(CHECK_INTERVAL_MS)

                val originalMac = prefs.getString("original_mac", null) ?: continue
                val currentMac = readActiveMac()

                // Update persistent notification with current MAC
                val nm = getSystemService(NotificationManager::class.java)
                nm.notify(NOTIFICATION_ID, buildMacNotification(currentMac))

                // Check for leak
                if (currentMac.equals(originalMac, ignoreCase = true)) {
                    sendLeakAlert(originalMac)
                }
            }
        }
    }

    private fun readActiveMac(): String {
        val result = Shell.cmd("ip link show wlan0").exec()
        val output = result.out.joinToString("\n")
        val match = "link/ether\\s+([0-9A-Fa-f:]{17})".toRegex().find(output)
        return match?.groupValues?.get(1)?.uppercase() ?: "Unknown"
    }

    private fun sendLeakAlert(mac: String) {
        val pendingIntent = PendingIntent.getActivity(
            this, 1,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, LEAK_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("MAC Leak Detected!")
            .setContentText("Original MAC ($mac) is active. Your identity may be exposed.")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Your original MAC address ($mac) has been detected on wlan0. " +
                        "This may happen after a reboot or Wi-Fi reconnect. " +
                        "Open the app to change it again."))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val nm = getSystemService(NotificationManager::class.java)
        nm.notify(LEAK_NOTIFICATION_ID, notification)
    }
}
