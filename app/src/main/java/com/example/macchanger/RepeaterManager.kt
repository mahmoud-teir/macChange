package com.example.macchanger

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pGroup
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import android.os.Looper
import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Manages the WiFi Repeater lifecycle using Android's native Wi-Fi Direct (P2P) API.
 * This works on chipsets (like Xiaomi) that block raw `iw` AP creation.
 */
object RepeaterManager {

    private const val STA_IFACE = "wlan0"
    private const val AP_IP = "192.168.49.1"
    private const val DHCP_RANGE_START = "192.168.49.10"
    private const val DHCP_RANGE_END = "192.168.49.50"
    private const val DHCP_LEASE = "12h"
    private const val DNSMASQ_CONF = "/data/local/tmp/dnsmasq_repeater.conf"

    var isRunning = false
        private set

    private var p2pManager: WifiP2pManager? = null
    private var p2pChannel: WifiP2pManager.Channel? = null
    private var p2pInterface: String? = null

    private suspend fun runSu(cmd: String): String = withContext(Dispatchers.IO) {
        val result = Shell.cmd(cmd).exec()
        if (result.isSuccess) result.out.joinToString("\n")
        else "ERROR: ${result.err.joinToString("\n")}"
    }

    /**
     * Start the WiFi repeater using WifiP2pManager.
     */
    @SuppressLint("MissingPermission")
    suspend fun start(
        context: Context,
        ssid: String,
        password: String,
        channel: Int = 6,
        band: String = "2.4GHz",
        log: (String) -> Unit
    ): Boolean {
        if (isRunning) {
            log("[!] Repeater is already running")
            return false
        }

        log("[*] Initializing Wi-Fi Direct (P2P)...")
        p2pManager = context.getSystemService(Context.WIFI_P2P_SERVICE) as? WifiP2pManager
        if (p2pManager == null) {
            log("[!] Device does not support Wi-Fi Direct")
            return false
        }
        p2pChannel = p2pManager?.initialize(context, Looper.getMainLooper(), null)
        if (p2pChannel == null) {
            log("[!] Failed to initialize Wi-Fi Direct channel")
            return false
        }

        // Clean up any existing group
        removeGroup()

        log("[*] Creating P2P Group ($ssid)...")
        val created = suspendCoroutine<Boolean> { cont ->
            if (Build.VERSION.SDK_INT >= 29) {
                val configBuilder = WifiP2pConfig.Builder()
                    .setNetworkName(ssid)
                    .setPassphrase(if (password.length >= 8) password else "12345678")

                if (channel > 0) {
                    val freq = channelToFrequency(channel, band == "5GHz")
                    configBuilder.setGroupOperatingFrequency(freq)
                } else {
                    configBuilder.setGroupOperatingBand(if (band == "5GHz") WifiP2pConfig.GROUP_OWNER_BAND_5GHZ else WifiP2pConfig.GROUP_OWNER_BAND_2GHZ)
                }
                
                val config = configBuilder.build()
                p2pManager?.createGroup(p2pChannel!!, config, object : WifiP2pManager.ActionListener {
                    override fun onSuccess() = cont.resume(true)
                    override fun onFailure(reason: Int) = cont.resume(false)
                })
            } else {
                p2pManager?.createGroup(p2pChannel!!, object : WifiP2pManager.ActionListener {
                    override fun onSuccess() = cont.resume(true)
                    override fun onFailure(reason: Int) = cont.resume(false)
                })
            }
        }

        if (!created) {
            log("[!] Failed to create P2P Group. Ensure Location and Wi-Fi are enabled.")
            return false
        }

        log("[*] Waiting for group interface to come up...")
        var groupInfo: WifiP2pGroup? = null
        for (i in 1..10) {
            delay(1000)
            groupInfo = suspendCoroutine { cont ->
                p2pManager?.requestGroupInfo(p2pChannel!!) { group ->
                    cont.resume(group)
                }
            }
            if (groupInfo != null && !groupInfo.networkName.isNullOrEmpty()) {
                break
            }
        }

        if (groupInfo == null) {
            log("[!] P2P Group created but could not retrieve information.")
            cleanup(log)
            return false
        }

        p2pInterface = groupInfo.`interface`
        if (p2pInterface.isNullOrEmpty()) {
            // Sometimes it takes another second for the interface string to populate
            delay(1000)
            groupInfo = suspendCoroutine { cont ->
                p2pManager?.requestGroupInfo(p2pChannel!!) { group -> cont.resume(group) }
            }
            p2pInterface = groupInfo?.`interface`
        }

        val apIface = p2pInterface
        if (apIface.isNullOrEmpty()) {
            log("[!] Could not determine P2P interface name.")
            cleanup(log)
            return false
        }

        log("[+] P2P interface created: $apIface")
        
        // Sometimes the interface takes a moment to be visible to ifconfig
        delay(1000)

        // Step 2: Assign IP to AP interface
        log("[*] Assigning IP $AP_IP to $apIface...")
        val ipResult = runSu("ifconfig $apIface $AP_IP netmask 255.255.255.0 up")
        if (ipResult.startsWith("ERROR") && !ipResult.contains("File exists")) {
            log("[!] Failed to assign IP: $ipResult")
            cleanup(log)
            return false
        }

        // Step 3: Write dnsmasq config & start
        log("[*] Starting dnsmasq (DHCP server)...")
        runSu("killall dnsmasq 2>/dev/null")
        val dnsmasqConf = buildDnsmasqConf(apIface)
        File(DNSMASQ_CONF).parentFile?.mkdirs()
        runSu("cat > $DNSMASQ_CONF << 'CONF_EOF'\n$dnsmasqConf\nCONF_EOF")
        runSu("dnsmasq -C $DNSMASQ_CONF --no-daemon &")
        
        // Wait a moment for dnsmasq to bind
        delay(500)
        val dnsmasqPid = runSu("pidof dnsmasq")
        if (dnsmasqPid.startsWith("ERROR") || dnsmasqPid.isBlank()) {
            runSu("dnsmasq --interface=$apIface --dhcp-range=$DHCP_RANGE_START,$DHCP_RANGE_END,$DHCP_LEASE --port=0 &")
        }
        log("[+] dnsmasq started")

        // Step 4: Enable IP forwarding & NAT
        log("[*] Setting up NAT forwarding ($apIface -> $STA_IFACE)...")
        runSu("echo 1 > /proc/sys/net/ipv4/ip_forward")
        runSu("iptables -t nat -A POSTROUTING -o $STA_IFACE -j MASQUERADE")
        runSu("iptables -A FORWARD -i $apIface -o $STA_IFACE -j ACCEPT")
        runSu("iptables -A FORWARD -i $STA_IFACE -o $apIface -m state --state RELATED,ESTABLISHED -j ACCEPT")
        log("[+] NAT forwarding enabled")

        isRunning = true
        log("[+] Repeater started! SSID: ${groupInfo?.networkName ?: ssid} | Gateway: $AP_IP")
        return true
    }

    /**
     * Stop the WiFi repeater and clean up all resources.
     */
    suspend fun stop(log: (String) -> Unit) {
        log("[*] Stopping repeater...")
        cleanup(log)
        isRunning = false
        log("[+] Repeater stopped")
    }

    private suspend fun cleanup(log: (String) -> Unit) {
        // Kill processes
        runSu("killall dnsmasq 2>/dev/null")
        log("[*] Killed dnsmasq")

        // Remove NAT rules
        val apIface = p2pInterface
        if (!apIface.isNullOrEmpty()) {
            runSu("iptables -t nat -D POSTROUTING -o $STA_IFACE -j MASQUERADE 2>/dev/null")
            runSu("iptables -D FORWARD -i $apIface -o $STA_IFACE -j ACCEPT 2>/dev/null")
            runSu("iptables -D FORWARD -i $STA_IFACE -o $apIface -m state --state RELATED,ESTABLISHED -j ACCEPT 2>/dev/null")
            log("[*] Cleared iptables rules")
        }

        // Disable forwarding
        runSu("echo 0 > /proc/sys/net/ipv4/ip_forward")

        // Remove P2P Group
        removeGroup()
        log("[*] Removed P2P Group")

        // Clean up config files
        runSu("rm -f $DNSMASQ_CONF 2>/dev/null")
        p2pInterface = null
    }

    private suspend fun removeGroup() {
        if (p2pManager != null && p2pChannel != null) {
            suspendCoroutine<Unit> { cont ->
                p2pManager?.removeGroup(p2pChannel!!, object : WifiP2pManager.ActionListener {
                    override fun onSuccess() = cont.resume(Unit)
                    override fun onFailure(reason: Int) = cont.resume(Unit)
                })
            }
        }
    }

    /**
     * Get connected client count via ARP table.
     */
    suspend fun getConnectedClients(): List<String> {
        val apIface = p2pInterface ?: return emptyList()
        val arp = runSu("ip neigh show dev $apIface")
        if (arp.startsWith("ERROR") || arp.isBlank()) return emptyList()
        return arp.lines()
            .filter { it.contains("lladdr") }
            .mapNotNull { line ->
                val parts = line.split("\\s+".toRegex())
                val ip = parts.firstOrNull()
                val mac = "lladdr\\s+([0-9a-fA-F:]{17})".toRegex()
                    .find(line)?.groupValues?.get(1)?.uppercase()
                if (ip != null && mac != null) "$ip ($mac)" else null
            }
    }

    private fun buildDnsmasqConf(iface: String): String {
        return """
            interface=$iface
            bind-interfaces
            dhcp-range=$DHCP_RANGE_START,$DHCP_RANGE_END,$DHCP_LEASE
            dhcp-option=3,$AP_IP
            dhcp-option=6,8.8.8.8,8.8.4.4
            port=0
            log-queries
            log-dhcp
        """.trimIndent()
    }

    private fun channelToFrequency(channel: Int, is5GHz: Boolean): Int {
        return if (is5GHz) {
            when (channel) {
                in 36..48 -> 5180 + (channel - 36) * 5
                in 52..64 -> 5260 + (channel - 52) * 5
                in 100..144 -> 5500 + (channel - 100) * 5
                in 149..165 -> 5745 + (channel - 149) * 5
                else -> 5180 // Default to 36
            }
        } else {
            if (channel in 1..13) 2407 + (channel * 5)
            else if (channel == 14) 2484
            else 2437 // Default to 6
        }
    }
}
