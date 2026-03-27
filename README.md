# macChange ❄️

**Modern MAC Address Modification & WiFi Repeater for Android (Root Required)**

macChange is a powerful, "Precision Functionalism" inspired utility designed for advanced network management on Android devices. It specializes in seamless MAC address spoofing and creating robust WiFi repeaters, with specific optimizations for Xiaomi/MIUI and Qualcomm-based chipsets.

---

## 🚀 Key Features

### 🛡️ Advanced MAC Modification
- **Multiple Methods:** Choose between native `ip link` commands, `ifconfig`, or `ndc` interface setcfg to ensure compatibility.
- **MIUI Optimization:** Special logic to prevent interface disappearance on Xiaomi devices.
- **OUI Database:** Integrated vendor lookup for both current and new MAC addresses.
- **Smart Detection:** Automatically finds core MAC configuration files (`.mac.info`, `.mac.cob`) on supported devices.

### 📶 Robust WiFi Repeater (NEW)
- **Wi-Fi Direct (P2P):** Uses Android's native P2P API to bypass restrictive driver limitations that block raw AP interfaces.
- **Broad Compatibility:** Works on devices where traditional "STA+AP" methods fail (e.g., Xiaomi).
- **Customizable:** Full control over SSID, Password, Operating Band (2.4GHz/5GHz), and specific Channels.
- **Root Routing:** Automated `dnsmasq` and `iptables` configuration for seamless internet sharing.

### 📟 Professional Terminal Log
- **Real-time Monitoring:** Watch every shell command and system response.
- **Copy-to-Clipboard:** Fully selectable text for advanced troubleshooting and auditing.
- **Binary Sanitization:** Safe reading of system files to prevent garbled output.

---

## 🛠️ Built With

- **Kotlin:** Modern, expressive Android development.
- **libsu:** Robust root shell management.
- **dnsmasq:** Lightweight DHCP and DNS provider.
- **iptables:** Linux kernel firewall for NAT and forwarding.
- **Precision Functionalism UI:** A charcoal-themed, zero-radius minimalist interface.

---

## 📖 How to Use

### MAC Address Spoofing
1. Grant **Root Access** when prompted.
2. Select your preferred **Method** (Method 1 for direct path, Method 2 for shell commands).
3. Generate a **Random MAC** or enter a custom one.
4. Tap **Change MAC** and verify via the Terminal Log.

### WiFi Repeater
1. Tap the **Repeater** button in the top bar.
2. Enter your desired **SSID** and **Password** (min 8 chars).
3. Select your **Band** and **Channel**.
4. Tap **Start** and wait for the `p2p-` interface to initialize.

---

## ⚠️ Requirements

- **Root Access:** Essential for low-level network interface control.
- **Chipset Support:** While highly compatible, some kernels may still restrict P2P or STA+AP modes.
- **Android 10+:** Recommended for best P2P API stability.

---
*Crafted with precision for the modern Android power user.*