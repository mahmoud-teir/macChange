package com.example.macchanger;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class SsidMacMappingDao_Impl implements SsidMacMappingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SsidMacMapping> __insertionAdapterOfSsidMacMapping;

  private final EntityDeletionOrUpdateAdapter<SsidMacMapping> __deletionAdapterOfSsidMacMapping;

  public SsidMacMappingDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSsidMacMapping = new EntityInsertionAdapter<SsidMacMapping>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `ssid_mac_mappings` (`ssid`,`mac_address`) VALUES (?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SsidMacMapping entity) {
        if (entity.getSsid() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getSsid());
        }
        if (entity.getMacAddress() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getMacAddress());
        }
      }
    };
    this.__deletionAdapterOfSsidMacMapping = new EntityDeletionOrUpdateAdapter<SsidMacMapping>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `ssid_mac_mappings` WHERE `ssid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SsidMacMapping entity) {
        if (entity.getSsid() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getSsid());
        }
      }
    };
  }

  @Override
  public Object insertOrUpdate(final SsidMacMapping mapping,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSsidMacMapping.insert(mapping);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final SsidMacMapping mapping, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSsidMacMapping.handle(mapping);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAll(final Continuation<? super List<SsidMacMapping>> $completion) {
    final String _sql = "SELECT * FROM ssid_mac_mappings ORDER BY ssid ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SsidMacMapping>>() {
      @Override
      @NonNull
      public List<SsidMacMapping> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSsid = CursorUtil.getColumnIndexOrThrow(_cursor, "ssid");
          final int _cursorIndexOfMacAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "mac_address");
          final List<SsidMacMapping> _result = new ArrayList<SsidMacMapping>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SsidMacMapping _item;
            final String _tmpSsid;
            if (_cursor.isNull(_cursorIndexOfSsid)) {
              _tmpSsid = null;
            } else {
              _tmpSsid = _cursor.getString(_cursorIndexOfSsid);
            }
            final String _tmpMacAddress;
            if (_cursor.isNull(_cursorIndexOfMacAddress)) {
              _tmpMacAddress = null;
            } else {
              _tmpMacAddress = _cursor.getString(_cursorIndexOfMacAddress);
            }
            _item = new SsidMacMapping(_tmpSsid,_tmpMacAddress);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getBySsid(final String ssid,
      final Continuation<? super SsidMacMapping> $completion) {
    final String _sql = "SELECT * FROM ssid_mac_mappings WHERE ssid = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (ssid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, ssid);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SsidMacMapping>() {
      @Override
      @Nullable
      public SsidMacMapping call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSsid = CursorUtil.getColumnIndexOrThrow(_cursor, "ssid");
          final int _cursorIndexOfMacAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "mac_address");
          final SsidMacMapping _result;
          if (_cursor.moveToFirst()) {
            final String _tmpSsid;
            if (_cursor.isNull(_cursorIndexOfSsid)) {
              _tmpSsid = null;
            } else {
              _tmpSsid = _cursor.getString(_cursorIndexOfSsid);
            }
            final String _tmpMacAddress;
            if (_cursor.isNull(_cursorIndexOfMacAddress)) {
              _tmpMacAddress = null;
            } else {
              _tmpMacAddress = _cursor.getString(_cursorIndexOfMacAddress);
            }
            _result = new SsidMacMapping(_tmpSsid,_tmpMacAddress);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
