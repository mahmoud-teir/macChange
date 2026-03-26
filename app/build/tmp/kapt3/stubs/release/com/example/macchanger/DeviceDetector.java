package com.example.macchanger;

/**
 * Auto-detects the device chipset vendor and determines the correct
 * MAC file paths for EFS/persist.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\fB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u0004H\u0086@\u00a2\u0006\u0002\u0010\u0005J\n\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u000e\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0004J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007H\u0002\u00a8\u0006\r"}, d2 = {"Lcom/example/macchanger/DeviceDetector;", "", "()V", "detect", "Lcom/example/macchanger/DeviceDetector$DeviceInfo;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findEfsPartition", "", "formatReport", "info", "getProp", "key", "DeviceInfo", "app_release"})
public final class DeviceDetector {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.macchanger.DeviceDetector INSTANCE = null;
    
    private DeviceDetector() {
        super();
    }
    
    /**
     * Detect chipset vendor and return device info with recommended paths.
     * Must be called from a coroutine (runs shell commands on IO).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object detect(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.macchanger.DeviceDetector.DeviceInfo> $completion) {
        return null;
    }
    
    /**
     * Get a system property via getprop
     */
    private final java.lang.String getProp(java.lang.String key) {
        return null;
    }
    
    /**
     * Try to find the EFS block device
     */
    private final java.lang.String findEfsPartition() {
        return null;
    }
    
    /**
     * Produce a human-readable summary of the detection results.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatReport(@org.jetbrains.annotations.NotNull()
    com.example.macchanger.DeviceDetector.DeviceInfo info) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006H\u00c6\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\tH\u00c6\u0003JC\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\b\u001a\u00020\tH\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\t2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001b\u001a\u00020\u001cH\u00d6\u0001J\t\u0010\u001d\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001e"}, d2 = {"Lcom/example/macchanger/DeviceDetector$DeviceInfo;", "", "chipsetVendor", "", "platform", "macPaths", "", "efsPartition", "supportsIpLink", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Z)V", "getChipsetVendor", "()Ljava/lang/String;", "getEfsPartition", "getMacPaths", "()Ljava/util/List;", "getPlatform", "getSupportsIpLink", "()Z", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "app_release"})
    public static final class DeviceInfo {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String chipsetVendor = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String platform = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.String> macPaths = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String efsPartition = null;
        private final boolean supportsIpLink = false;
        
        public DeviceInfo(@org.jetbrains.annotations.NotNull()
        java.lang.String chipsetVendor, @org.jetbrains.annotations.NotNull()
        java.lang.String platform, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> macPaths, @org.jetbrains.annotations.Nullable()
        java.lang.String efsPartition, boolean supportsIpLink) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getChipsetVendor() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getPlatform() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getMacPaths() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getEfsPartition() {
            return null;
        }
        
        public final boolean getSupportsIpLink() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> component3() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component4() {
            return null;
        }
        
        public final boolean component5() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.macchanger.DeviceDetector.DeviceInfo copy(@org.jetbrains.annotations.NotNull()
        java.lang.String chipsetVendor, @org.jetbrains.annotations.NotNull()
        java.lang.String platform, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> macPaths, @org.jetbrains.annotations.Nullable()
        java.lang.String efsPartition, boolean supportsIpLink) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}