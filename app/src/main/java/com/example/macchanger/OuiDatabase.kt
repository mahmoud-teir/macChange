package com.example.macchanger

/**
 * Offline OUI (Organizationally Unique Identifier) lookup.
 * Maps the first 3 bytes of a MAC address to the manufacturer name.
 * Contains ~200 of the most common vendors.
 */
object OuiDatabase {

    private val ouiMap = mapOf(
        // Apple
        "00:03:93" to "Apple", "00:05:02" to "Apple", "00:0A:27" to "Apple",
        "00:0A:95" to "Apple", "00:0D:93" to "Apple", "00:10:FA" to "Apple",
        "00:11:24" to "Apple", "00:14:51" to "Apple", "00:16:CB" to "Apple",
        "00:17:F2" to "Apple", "00:19:E3" to "Apple", "00:1B:63" to "Apple",
        "00:1C:B3" to "Apple", "00:1D:4F" to "Apple", "00:1E:52" to "Apple",
        "00:1E:C2" to "Apple", "00:1F:5B" to "Apple", "00:1F:F3" to "Apple",
        "00:21:E9" to "Apple", "00:22:41" to "Apple", "00:23:12" to "Apple",
        "00:23:32" to "Apple", "00:23:6C" to "Apple", "00:23:DF" to "Apple",
        "00:24:36" to "Apple", "00:25:00" to "Apple", "00:25:4B" to "Apple",
        "00:25:BC" to "Apple", "00:26:08" to "Apple", "00:26:4A" to "Apple",
        "00:26:B0" to "Apple", "00:26:BB" to "Apple",
        "3C:15:C2" to "Apple", "3C:07:54" to "Apple",
        "AC:DE:48" to "Apple", "A4:D1:8C" to "Apple",
        "F0:D1:A9" to "Apple", "F4:F1:5A" to "Apple",

        // Samsung
        "00:00:F0" to "Samsung", "00:02:78" to "Samsung", "00:07:AB" to "Samsung",
        "00:09:18" to "Samsung", "00:0D:E5" to "Samsung", "00:12:47" to "Samsung",
        "00:12:FB" to "Samsung", "00:13:77" to "Samsung", "00:15:99" to "Samsung",
        "00:16:32" to "Samsung", "00:16:6B" to "Samsung", "00:16:6C" to "Samsung",
        "00:16:DB" to "Samsung", "00:17:C9" to "Samsung", "00:17:D5" to "Samsung",
        "00:18:AF" to "Samsung", "00:1A:8A" to "Samsung", "00:1B:98" to "Samsung",
        "00:1C:43" to "Samsung", "00:1D:25" to "Samsung", "00:1D:F6" to "Samsung",
        "00:1E:7D" to "Samsung", "00:1F:CC" to "Samsung", "00:1F:CD" to "Samsung",
        "00:21:19" to "Samsung", "00:21:4C" to "Samsung", "00:21:D1" to "Samsung",
        "00:21:D2" to "Samsung", "00:23:39" to "Samsung", "00:23:3A" to "Samsung",
        "00:23:99" to "Samsung", "00:23:D6" to "Samsung", "00:23:D7" to "Samsung",
        "00:24:54" to "Samsung", "00:24:90" to "Samsung", "00:24:91" to "Samsung",
        "00:24:E9" to "Samsung", "00:25:66" to "Samsung", "00:25:67" to "Samsung",
        "00:26:37" to "Samsung",
        "5C:0A:5B" to "Samsung", "5C:3C:27" to "Samsung",
        "8C:77:12" to "Samsung", "94:35:0A" to "Samsung",
        "B4:3A:28" to "Samsung", "BC:44:86" to "Samsung",
        "C4:73:1E" to "Samsung", "CC:07:AB" to "Samsung",
        "D0:22:BE" to "Samsung", "D0:87:E2" to "Samsung",
        "E4:7C:F9" to "Samsung", "F0:25:B7" to "Samsung",
        "F4:7B:5E" to "Samsung", "F8:04:2E" to "Samsung",

        // Google / Pixel
        "3C:5A:B4" to "Google", "54:60:09" to "Google",
        "F4:F5:D8" to "Google", "F4:F5:E8" to "Google",
        "A4:77:33" to "Google", "94:EB:2C" to "Google",

        // Huawei
        "00:1E:10" to "Huawei", "00:18:82" to "Huawei", "00:25:9E" to "Huawei",
        "00:46:4B" to "Huawei", "00:66:4B" to "Huawei", "00:E0:FC" to "Huawei",
        "04:BD:70" to "Huawei", "04:C0:6F" to "Huawei", "04:F9:38" to "Huawei",
        "08:19:A6" to "Huawei", "0C:37:DC" to "Huawei", "10:44:00" to "Huawei",
        "10:47:80" to "Huawei", "14:B9:68" to "Huawei", "20:08:ED" to "Huawei",
        "20:0B:C7" to "Huawei", "20:2B:C1" to "Huawei", "24:09:95" to "Huawei",
        "24:DB:AC" to "Huawei", "28:3C:E4" to "Huawei", "28:6E:D4" to "Huawei",
        "30:D1:7E" to "Huawei", "34:00:A3" to "Huawei", "38:4C:4F" to "Huawei",

        // Xiaomi
        "00:9E:C8" to "Xiaomi", "04:CF:8C" to "Xiaomi", "08:86:3B" to "Xiaomi",
        "0C:1D:AF" to "Xiaomi", "10:2A:B3" to "Xiaomi", "14:F6:5A" to "Xiaomi",
        "18:59:36" to "Xiaomi", "1C:5F:2B" to "Xiaomi", "20:47:DA" to "Xiaomi",
        "28:6C:07" to "Xiaomi", "28:E3:1F" to "Xiaomi", "2C:9D:1E" to "Xiaomi",
        "34:80:B3" to "Xiaomi", "38:A4:ED" to "Xiaomi", "3C:BD:3E" to "Xiaomi",
        "40:31:3C" to "Xiaomi", "44:23:7C" to "Xiaomi", "4C:49:E3" to "Xiaomi",
        "50:64:2B" to "Xiaomi", "58:44:98" to "Xiaomi", "64:09:80" to "Xiaomi",
        "64:CC:2E" to "Xiaomi", "68:AB:1E" to "Xiaomi", "74:23:44" to "Xiaomi",
        "7C:1D:D9" to "Xiaomi", "8C:BE:BE" to "Xiaomi",

        // OnePlus / BBK / Oppo / Vivo / Realme
        "94:65:2D" to "OnePlus", "C0:EE:FB" to "OnePlus",
        "A0:F4:79" to "Oppo", "1C:48:F9" to "Oppo", "2C:5B:E1" to "Oppo",
        "A4:3B:FA" to "Oppo", "EC:DF:3A" to "Oppo",
        "14:3B:39" to "Vivo", "30:05:5C" to "Vivo", "3C:BB:FD" to "Vivo",
        "78:44:FD" to "Realme", "C8:47:8C" to "Realme",

        // Sony
        "00:01:4A" to "Sony", "00:04:1F" to "Sony", "00:13:A9" to "Sony",
        "00:15:C1" to "Sony", "00:19:63" to "Sony", "00:1A:80" to "Sony",
        "00:1D:BA" to "Sony", "00:1E:A4" to "Sony", "00:24:BE" to "Sony",
        "04:5D:4B" to "Sony", "10:A5:D0" to "Sony", "24:21:AB" to "Sony",
        "30:17:C8" to "Sony", "40:B8:37" to "Sony", "70:51:B5" to "Sony",

        // LG
        "00:05:C9" to "LG", "00:07:91" to "LG", "00:0B:E9" to "LG",
        "00:0F:20" to "LG", "00:10:18" to "LG", "00:14:3E" to "LG",
        "00:17:C8" to "LG", "00:1C:62" to "LG", "00:1E:75" to "LG",
        "00:1F:6B" to "LG", "00:1F:E3" to "LG", "00:21:FB" to "LG",
        "00:22:A9" to "LG", "00:24:83" to "LG", "00:25:E5" to "LG",
        "00:26:E2" to "LG", "10:68:3F" to "LG", "14:C9:13" to "LG",
        "20:3D:BD" to "LG", "28:CF:E9" to "LG", "30:76:6F" to "LG",
        "34:FC:EF" to "LG", "40:B0:FA" to "LG", "58:A2:B5" to "LG",

        // Intel
        "00:02:B3" to "Intel", "00:03:47" to "Intel", "00:04:23" to "Intel",
        "00:07:E9" to "Intel", "00:0C:F1" to "Intel", "00:0E:0C" to "Intel",
        "00:0E:35" to "Intel", "00:11:11" to "Intel", "00:13:02" to "Intel",
        "00:13:20" to "Intel", "00:13:CE" to "Intel", "00:13:E8" to "Intel",
        "00:15:00" to "Intel", "00:15:17" to "Intel", "00:16:6F" to "Intel",
        "00:16:76" to "Intel", "00:16:EA" to "Intel", "00:16:EB" to "Intel",
        "00:18:DE" to "Intel", "00:19:D1" to "Intel", "00:1B:21" to "Intel",
        "00:1B:77" to "Intel", "00:1C:BF" to "Intel", "00:1D:E0" to "Intel",
        "00:1D:E1" to "Intel", "00:1E:64" to "Intel", "00:1E:65" to "Intel",
        "00:1F:3B" to "Intel", "00:1F:3C" to "Intel",

        // Qualcomm / Atheros
        "00:03:7F" to "Qualcomm/Atheros", "00:0B:6B" to "Qualcomm/Atheros",
        "00:0C:41" to "Qualcomm/Atheros", "00:0C:43" to "Qualcomm/Atheros",
        "00:0E:6D" to "Qualcomm/Atheros", "00:13:74" to "Qualcomm/Atheros",
        "00:15:AF" to "Qualcomm/Atheros", "00:1A:6B" to "Qualcomm/Atheros",
        "00:1C:14" to "Qualcomm/Atheros", "00:20:A6" to "Qualcomm/Atheros",
        "00:24:D2" to "Qualcomm/Atheros", "00:26:CB" to "Qualcomm/Atheros",

        // Broadcom
        "00:05:B5" to "Broadcom", "00:10:18" to "Broadcom",
        "00:1B:E9" to "Broadcom", "00:22:5F" to "Broadcom",

        // MediaTek
        "00:0C:E7" to "MediaTek", "00:08:22" to "MediaTek",
        "18:D2:76" to "MediaTek", "C8:85:50" to "MediaTek",
        "00:0C:43" to "MediaTek",

        // TP-Link
        "00:27:19" to "TP-Link", "14:CF:92" to "TP-Link",
        "30:B5:C2" to "TP-Link", "50:C7:BF" to "TP-Link",
        "60:32:B1" to "TP-Link", "64:70:02" to "TP-Link",
        "6C:B0:CE" to "TP-Link", "90:F6:52" to "TP-Link",
        "AC:84:C6" to "TP-Link", "B0:4E:26" to "TP-Link",
        "C0:25:E9" to "TP-Link", "D8:07:B6" to "TP-Link",
        "E8:DE:27" to "TP-Link", "EC:08:6B" to "TP-Link",
        "F4:F2:6D" to "TP-Link", "F8:D1:11" to "TP-Link",

        // Motorola / Lenovo
        "00:01:AF" to "Motorola", "00:04:56" to "Motorola",
        "00:08:0E" to "Motorola", "00:0A:28" to "Motorola",
        "00:0B:06" to "Motorola", "00:0C:E5" to "Motorola",
        "00:0E:5C" to "Motorola", "00:0F:9F" to "Motorola",
        "00:11:1A" to "Motorola", "00:11:80" to "Motorola",
        "00:12:25" to "Motorola", "00:14:04" to "Motorola",

        // Nokia / HMD
        "00:02:EE" to "Nokia", "00:03:AF" to "Nokia",
        "00:0B:E1" to "Nokia", "00:0E:ED" to "Nokia",
        "00:10:B3" to "Nokia", "00:12:62" to "Nokia",
        "00:13:70" to "Nokia", "00:14:A7" to "Nokia",
        "00:15:2A" to "Nokia", "00:16:4E" to "Nokia",
        "00:17:4B" to "Nokia", "00:18:0F" to "Nokia",
        "00:19:4F" to "Nokia", "00:1A:DC" to "Nokia",
        "00:1B:AF" to "Nokia", "00:1B:EE" to "Nokia",
        "00:1C:9A" to "Nokia", "00:1C:D4" to "Nokia",
        "00:1C:D6" to "Nokia", "00:1D:6E" to "Nokia",
        "00:1D:FD" to "Nokia", "00:1E:3A" to "Nokia",
        "00:1E:A3" to "Nokia", "00:1F:00" to "Nokia",
        "00:1F:01" to "Nokia", "00:1F:5C" to "Nokia",
        "00:1F:DE" to "Nokia", "00:1F:DF" to "Nokia",

        // HTC
        "00:09:2D" to "HTC", "00:23:76" to "HTC",
        "18:87:96" to "HTC", "1C:B0:94" to "HTC",
        "2C:8A:72" to "HTC", "38:E7:D8" to "HTC",
        "64:A7:69" to "HTC", "7C:61:93" to "HTC",
        "80:01:84" to "HTC", "84:7A:88" to "HTC",
        "90:21:55" to "HTC", "D8:B3:77" to "HTC",

        // Asus
        "00:0C:6E" to "Asus", "00:0E:A6" to "Asus",
        "00:11:2F" to "Asus", "00:11:D8" to "Asus",
        "00:13:D4" to "Asus", "00:15:F2" to "Asus",
        "00:17:31" to "Asus", "00:18:F3" to "Asus",
        "00:1A:92" to "Asus", "00:1B:FC" to "Asus",
        "00:1D:60" to "Asus", "00:1E:8C" to "Asus",
        "00:1F:C6" to "Asus", "00:22:15" to "Asus",
        "00:23:54" to "Asus", "00:24:8C" to "Asus",
        "00:26:18" to "Asus",

        // Locally administered (random)
        "02:00:00" to "Locally Administered (Random)",
        "06:00:00" to "Locally Administered (Random)",
        "0A:00:00" to "Locally Administered (Random)",
        "0E:00:00" to "Locally Administered (Random)"
    )

    /**
     * Look up the vendor/manufacturer for a given MAC address.
     * @param mac MAC address in format XX:XX:XX:XX:XX:XX
     * @return Vendor name or "Unknown" if not found
     */
    fun lookup(mac: String): String {
        val normalized = mac.uppercase().trim()

        // Check if locally administered (bit 1 of first octet is set)
        val firstOctet = normalized.substringBefore(":").toIntOrNull(16) ?: 0
        if (firstOctet and 0x02 != 0) {
            return "Locally Administered (Random)"
        }

        // Look up by OUI prefix (first 3 octets)
        val prefix = normalized.split(":").take(3).joinToString(":")
        return ouiMap[prefix] ?: "Unknown Vendor"
    }
}
