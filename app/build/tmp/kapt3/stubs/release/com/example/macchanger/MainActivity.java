package com.example.macchanger;

/**
 * MAC Changer v3 – full-featured root utility.
 *
 * Features:
 *  1. MAC comparison before/after   2. Random MAC generation
 *  3. Original MAC saved on first scan   4. Confirmation dialogs
 *  5. MAC history (Room)   6. Scheduled auto-change (WorkManager)
 *  7. Backup file picker   8. Log export/share
 *  9. MAC Leak Detection (foreground service)
 * 10. OUI Vendor Lookup (offline)
 * 11. MAC Profiles (Room)
 * 12. Network info display
 * 13. Persistent notification
 * 14. Auto device/chipset detection
 * 15. EFS integrity check (checksum)
 * 16. WiFi auto-reconnect after MAC change
 * 17. Import/Export MAC profiles (JSON)
 * 18. Boot-time MAC change (BOOT_COMPLETED receiver)
 * 19. Auto EFS backup before MAC change
 * 20. Copy MAC to clipboard
 * 21. Vendor MAC spoofing (generate MAC by manufacturer)
 * 22. Network scanner (ARP table)
 * 23. MAC per SSID (auto-apply saved MAC per network)
 * 24. Universal MAC change (ip link set – works on ALL rooted devices)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 j2\u00020\u0001:\u0001jB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0019H\u0002J\b\u0010+\u001a\u00020)H\u0002J\b\u0010,\u001a\u00020)H\u0002J\u0010\u0010-\u001a\u00020)2\u0006\u0010.\u001a\u00020\u0019H\u0002J\b\u0010/\u001a\u00020)H\u0002J\b\u00100\u001a\u00020)H\u0002J\b\u00101\u001a\u00020)H\u0002J\b\u00102\u001a\u00020)H\u0002J\u0010\u00103\u001a\u00020)2\u0006\u00104\u001a\u00020\u0019H\u0002J\b\u00105\u001a\u00020)H\u0002J\b\u00106\u001a\u00020)H\u0002J\u0010\u00107\u001a\u00020)2\u0006\u00104\u001a\u00020\u0019H\u0002J\b\u00108\u001a\u00020)H\u0002J\b\u00109\u001a\u00020)H\u0002J\b\u0010:\u001a\u00020)H\u0002J\u0010\u0010;\u001a\u0004\u0018\u00010\u0019H\u0082@\u00a2\u0006\u0002\u0010<J\b\u0010=\u001a\u00020)H\u0002J\u0010\u0010>\u001a\u00020!2\u0006\u0010?\u001a\u00020\u0019H\u0002J\b\u0010@\u001a\u00020)H\u0002J\u0016\u0010A\u001a\u00020\u00192\u0006\u0010B\u001a\u00020\u0019H\u0082@\u00a2\u0006\u0002\u0010CJ\u0012\u0010D\u001a\u00020)2\b\u0010E\u001a\u0004\u0018\u00010FH\u0014J\u000e\u0010G\u001a\u00020\u0019H\u0082@\u00a2\u0006\u0002\u0010<J\u0016\u0010H\u001a\u00020)2\u0006\u0010I\u001a\u00020\u0019H\u0082@\u00a2\u0006\u0002\u0010CJ\n\u0010J\u001a\u0004\u0018\u00010\u0019H\u0002J\u0010\u0010K\u001a\u00020)2\u0006\u00104\u001a\u00020\u0019H\u0002J\b\u0010L\u001a\u00020)H\u0002J\u0016\u0010M\u001a\u00020\u00192\u0006\u0010N\u001a\u00020\u0019H\u0082@\u00a2\u0006\u0002\u0010CJ\u0018\u0010O\u001a\u00020)2\u0006\u0010I\u001a\u00020\u00192\u0006\u0010?\u001a\u00020\u0019H\u0002J\b\u0010P\u001a\u00020)H\u0002J\u0010\u0010Q\u001a\u00020)2\u0006\u0010R\u001a\u00020SH\u0002J\b\u0010T\u001a\u00020)H\u0002J\u0010\u0010U\u001a\u00020)2\u0006\u0010I\u001a\u00020\u0019H\u0002J\b\u0010V\u001a\u00020)H\u0002J\b\u0010W\u001a\u00020)H\u0002J\b\u0010X\u001a\u00020)H\u0002J\u0010\u0010Y\u001a\u00020)2\u0006\u0010Z\u001a\u00020[H\u0002J\b\u0010\\\u001a\u00020)H\u0002J\b\u0010]\u001a\u00020)H\u0002J\b\u0010^\u001a\u00020)H\u0002J\u0010\u0010_\u001a\u00020)2\u0006\u0010`\u001a\u00020aH\u0002J\b\u0010b\u001a\u00020)H\u0002J\b\u0010c\u001a\u00020)H\u0002J\u0010\u0010d\u001a\u00020)2\u0006\u0010e\u001a\u00020\u0019H\u0002J\b\u0010f\u001a\u00020)H\u0002J\b\u0010g\u001a\u00020)H\u0002J\b\u0010h\u001a\u00020)H\u0002J\b\u0010i\u001a\u00020)H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00190\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\'X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006k"}, d2 = {"Lcom/example/macchanger/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "btnAutoBackup", "Landroid/widget/Button;", "btnBackup", "btnBootMac", "btnChange", "btnCopyMac", "btnDetect", "btnExport", "btnHistory", "btnMonitor", "btnNetInfo", "btnNetScan", "btnProfiles", "btnRandom", "btnRestore", "btnScan", "btnSchedule", "btnSsidMac", "btnVendorMac", "db", "Lcom/example/macchanger/MacHistoryDatabase;", "efsPartition", "", "etNewMac", "Landroid/widget/EditText;", "knownPaths", "", "macCobPath", "macInfoPath", "monitorRunning", "", "prefs", "Landroid/content/SharedPreferences;", "scrollView", "Landroid/widget/ScrollView;", "tvLog", "Landroid/widget/TextView;", "appendLog", "", "msg", "backupEfs", "bindViews", "changeMac", "newMac", "checkRootAccess", "configureSu", "confirmBackup", "confirmChange", "confirmRestore", "filePath", "copyMacToClipboard", "detectDevice", "doImportProfiles", "exportLog", "exportProfiles", "generateAndFillRandomMac", "getCurrentSsid", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "importProfiles", "isValidMac", "mac", "locateMacFiles", "md5sum", "path", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "readActiveMac", "reconnectToSsid", "ssid", "resolvePartition", "restoreEfs", "restoreState", "runSu", "cmd", "saveSsidMacMapping", "scanNetwork", "scheduleAutoChange", "intervalMinutes", "", "setListeners", "showAssignSsidMacDialog", "showBackupPicker", "showHistory", "showNetworkInfo", "showProfileActions", "profile", "Lcom/example/macchanger/MacProfile;", "showProfiles", "showSaveProfileDialog", "showScheduleDialog", "showSsidMacActions", "mapping", "Lcom/example/macchanger/SsidMacMapping;", "showSsidMacDialog", "showVendorSpoofDialog", "startMonitorService", "currentMac", "toggleAutoBackup", "toggleBootMac", "toggleMonitor", "updateToggleButtons", "Companion", "app_release"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.Button btnScan;
    private android.widget.Button btnDetect;
    private android.widget.Button btnBackup;
    private android.widget.Button btnChange;
    private android.widget.Button btnRestore;
    private android.widget.Button btnRandom;
    private android.widget.Button btnHistory;
    private android.widget.Button btnProfiles;
    private android.widget.Button btnNetInfo;
    private android.widget.Button btnSchedule;
    private android.widget.Button btnMonitor;
    private android.widget.Button btnExport;
    private android.widget.Button btnVendorMac;
    private android.widget.Button btnSsidMac;
    private android.widget.Button btnNetScan;
    private android.widget.Button btnBootMac;
    private android.widget.Button btnAutoBackup;
    private android.widget.Button btnCopyMac;
    private android.widget.EditText etNewMac;
    private android.widget.TextView tvLog;
    private android.widget.ScrollView scrollView;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String macInfoPath = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String macCobPath = "";
    @org.jetbrains.annotations.Nullable()
    private java.lang.String efsPartition;
    private boolean monitorRunning = false;
    private android.content.SharedPreferences prefs;
    private com.example.macchanger.MacHistoryDatabase db;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> knownPaths = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String WORK_TAG = "mac_auto_change";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREF_NAME = "mac_prefs";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_ORIGINAL_MAC = "original_mac";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_MAC_INFO_PATH = "mac_info_path";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_MAC_COB_PATH = "mac_cob_path";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_EFS_PARTITION = "efs_partition";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_AUTO_BACKUP = "auto_backup_enabled";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.macchanger.MainActivity.Companion Companion = null;
    
    public MainActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void bindViews() {
    }
    
    private final void configureSu() {
    }
    
    private final void restoreState() {
    }
    
    private final void setListeners() {
    }
    
    private final void updateToggleButtons() {
    }
    
    private final void checkRootAccess() {
    }
    
    private final void appendLog(java.lang.String msg) {
    }
    
    private final java.lang.Object runSu(java.lang.String cmd, kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final boolean isValidMac(java.lang.String mac) {
        return false;
    }
    
    private final java.lang.Object readActiveMac(kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Resolve the mount partition from the mac file path
     */
    private final java.lang.String resolvePartition() {
        return null;
    }
    
    private final void detectDevice() {
    }
    
    private final void locateMacFiles() {
    }
    
    private final void generateAndFillRandomMac() {
    }
    
    private final void confirmBackup() {
    }
    
    private final void confirmChange() {
    }
    
    private final void confirmRestore(java.lang.String filePath) {
    }
    
    private final void backupEfs() {
    }
    
    /**
     * Calculate MD5 checksum of a file via shell
     */
    private final java.lang.Object md5sum(java.lang.String path, kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Read current SSID before disconnecting
     */
    private final java.lang.Object getCurrentSsid(kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Attempt to reconnect to the saved SSID after WiFi is re-enabled
     */
    private final java.lang.Object reconnectToSsid(java.lang.String ssid, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Change MAC using the best available method:
     * 1. EFS file method (Samsung/Qualcomm/Huawei) – persistent across reboots
     * 2. Universal ip link method – works on ALL rooted devices but resets on reboot
     * Both methods are tried; ip link is always used as a complement/fallback.
     */
    private final void changeMac(java.lang.String newMac) {
    }
    
    private final void showBackupPicker() {
    }
    
    private final void restoreEfs(java.lang.String filePath) {
    }
    
    private final void showHistory() {
    }
    
    private final void showProfiles() {
    }
    
    private final void showSaveProfileDialog() {
    }
    
    private final void showProfileActions(com.example.macchanger.MacProfile profile) {
    }
    
    private final void exportProfiles() {
    }
    
    private final void importProfiles() {
    }
    
    private final void doImportProfiles(java.lang.String filePath) {
    }
    
    private final void showNetworkInfo() {
    }
    
    private final void toggleMonitor() {
    }
    
    private final void startMonitorService(java.lang.String currentMac) {
    }
    
    private final void showScheduleDialog() {
    }
    
    private final void scheduleAutoChange(long intervalMinutes) {
    }
    
    private final void toggleBootMac() {
    }
    
    private final void toggleAutoBackup() {
    }
    
    private final void copyMacToClipboard() {
    }
    
    private final void showVendorSpoofDialog() {
    }
    
    private final void scanNetwork() {
    }
    
    private final void showSsidMacDialog() {
    }
    
    private final void showAssignSsidMacDialog(java.lang.String ssid) {
    }
    
    private final void saveSsidMacMapping(java.lang.String ssid, java.lang.String mac) {
    }
    
    private final void showSsidMacActions(com.example.macchanger.SsidMacMapping mapping) {
    }
    
    private final void exportLog() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/example/macchanger/MainActivity$Companion;", "", "()V", "KEY_AUTO_BACKUP", "", "KEY_EFS_PARTITION", "KEY_MAC_COB_PATH", "KEY_MAC_INFO_PATH", "KEY_ORIGINAL_MAC", "PREF_NAME", "WORK_TAG", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}