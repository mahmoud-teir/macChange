package com.example.macchanger;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MacHistoryDatabase_Impl extends MacHistoryDatabase {
  private volatile MacHistoryDao _macHistoryDao;

  private volatile MacProfileDao _macProfileDao;

  private volatile SsidMacMappingDao _ssidMacMappingDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `mac_history` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `mac_address` TEXT NOT NULL, `timestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `mac_profiles` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `mac_address` TEXT NOT NULL, `created_at` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `ssid_mac_mappings` (`ssid` TEXT NOT NULL, `mac_address` TEXT NOT NULL, PRIMARY KEY(`ssid`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '6f157c0c6a0efae95bb5cdfb6d229cb5')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `mac_history`");
        db.execSQL("DROP TABLE IF EXISTS `mac_profiles`");
        db.execSQL("DROP TABLE IF EXISTS `ssid_mac_mappings`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsMacHistory = new HashMap<String, TableInfo.Column>(3);
        _columnsMacHistory.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMacHistory.put("mac_address", new TableInfo.Column("mac_address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMacHistory.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMacHistory = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMacHistory = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMacHistory = new TableInfo("mac_history", _columnsMacHistory, _foreignKeysMacHistory, _indicesMacHistory);
        final TableInfo _existingMacHistory = TableInfo.read(db, "mac_history");
        if (!_infoMacHistory.equals(_existingMacHistory)) {
          return new RoomOpenHelper.ValidationResult(false, "mac_history(com.example.macchanger.MacEntry).\n"
                  + " Expected:\n" + _infoMacHistory + "\n"
                  + " Found:\n" + _existingMacHistory);
        }
        final HashMap<String, TableInfo.Column> _columnsMacProfiles = new HashMap<String, TableInfo.Column>(4);
        _columnsMacProfiles.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMacProfiles.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMacProfiles.put("mac_address", new TableInfo.Column("mac_address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMacProfiles.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMacProfiles = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMacProfiles = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMacProfiles = new TableInfo("mac_profiles", _columnsMacProfiles, _foreignKeysMacProfiles, _indicesMacProfiles);
        final TableInfo _existingMacProfiles = TableInfo.read(db, "mac_profiles");
        if (!_infoMacProfiles.equals(_existingMacProfiles)) {
          return new RoomOpenHelper.ValidationResult(false, "mac_profiles(com.example.macchanger.MacProfile).\n"
                  + " Expected:\n" + _infoMacProfiles + "\n"
                  + " Found:\n" + _existingMacProfiles);
        }
        final HashMap<String, TableInfo.Column> _columnsSsidMacMappings = new HashMap<String, TableInfo.Column>(2);
        _columnsSsidMacMappings.put("ssid", new TableInfo.Column("ssid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSsidMacMappings.put("mac_address", new TableInfo.Column("mac_address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSsidMacMappings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSsidMacMappings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSsidMacMappings = new TableInfo("ssid_mac_mappings", _columnsSsidMacMappings, _foreignKeysSsidMacMappings, _indicesSsidMacMappings);
        final TableInfo _existingSsidMacMappings = TableInfo.read(db, "ssid_mac_mappings");
        if (!_infoSsidMacMappings.equals(_existingSsidMacMappings)) {
          return new RoomOpenHelper.ValidationResult(false, "ssid_mac_mappings(com.example.macchanger.SsidMacMapping).\n"
                  + " Expected:\n" + _infoSsidMacMappings + "\n"
                  + " Found:\n" + _existingSsidMacMappings);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "6f157c0c6a0efae95bb5cdfb6d229cb5", "ac512aa79084240d6ae4367b4af25dff");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "mac_history","mac_profiles","ssid_mac_mappings");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `mac_history`");
      _db.execSQL("DELETE FROM `mac_profiles`");
      _db.execSQL("DELETE FROM `ssid_mac_mappings`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(MacHistoryDao.class, MacHistoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MacProfileDao.class, MacProfileDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SsidMacMappingDao.class, SsidMacMappingDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public MacHistoryDao macHistoryDao() {
    if (_macHistoryDao != null) {
      return _macHistoryDao;
    } else {
      synchronized(this) {
        if(_macHistoryDao == null) {
          _macHistoryDao = new MacHistoryDao_Impl(this);
        }
        return _macHistoryDao;
      }
    }
  }

  @Override
  public MacProfileDao macProfileDao() {
    if (_macProfileDao != null) {
      return _macProfileDao;
    } else {
      synchronized(this) {
        if(_macProfileDao == null) {
          _macProfileDao = new MacProfileDao_Impl(this);
        }
        return _macProfileDao;
      }
    }
  }

  @Override
  public SsidMacMappingDao ssidMacMappingDao() {
    if (_ssidMacMappingDao != null) {
      return _ssidMacMappingDao;
    } else {
      synchronized(this) {
        if(_ssidMacMappingDao == null) {
          _ssidMacMappingDao = new SsidMacMappingDao_Impl(this);
        }
        return _ssidMacMappingDao;
      }
    }
  }
}
