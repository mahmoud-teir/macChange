package com.example.macchanger;

/**
 * Offline OUI (Organizationally Unique Identifier) lookup.
 * Maps the first 3 bytes of a MAC address to the manufacturer name.
 * Contains ~200 of the most common vendors.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\u0005J\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u0006\u0010\u0007\u001a\u00020\u0005J\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\tJ\u000e\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/example/macchanger/OuiDatabase;", "", "()V", "ouiMap", "", "", "generateMacForVendor", "vendor", "getOuiPrefixesForVendor", "", "getVendorNames", "lookup", "mac", "app_release"})
public final class OuiDatabase {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<java.lang.String, java.lang.String> ouiMap = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.macchanger.OuiDatabase INSTANCE = null;
    
    private OuiDatabase() {
        super();
    }
    
    /**
     * Look up the vendor/manufacturer for a given MAC address.
     * @param mac MAC address in format XX:XX:XX:XX:XX:XX
     * @return Vendor name or "Unknown" if not found
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String lookup(@org.jetbrains.annotations.NotNull()
    java.lang.String mac) {
        return null;
    }
    
    /**
     * Get sorted list of unique vendor names
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getVendorNames() {
        return null;
    }
    
    /**
     * Get all OUI prefixes for a given vendor name
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getOuiPrefixesForVendor(@org.jetbrains.annotations.NotNull()
    java.lang.String vendor) {
        return null;
    }
    
    /**
     * Generate a random MAC with a specific vendor OUI prefix
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String generateMacForVendor(@org.jetbrains.annotations.NotNull()
    java.lang.String vendor) {
        return null;
    }
}