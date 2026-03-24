package com.example.macchanger

import android.content.Context
import androidx.room.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ── MAC History Entity ───────────────────────────────────────────────

@Entity(tableName = "mac_history")
data class MacEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "mac_address") val macAddress: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long = System.currentTimeMillis()
) {
    fun formattedTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}

@Dao
interface MacHistoryDao {
    @Insert
    suspend fun insert(entry: MacEntry)

    @Query("SELECT * FROM mac_history ORDER BY timestamp DESC")
    suspend fun getAll(): List<MacEntry>

    @Query("SELECT * FROM mac_history ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLast(): MacEntry?
}

// ── MAC Profile Entity ───────────────────────────────────────────────

@Entity(tableName = "mac_profiles")
data class MacProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "mac_address") val macAddress: String,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
) {
    fun formattedTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(Date(createdAt))
    }
}

@Dao
interface MacProfileDao {
    @Insert
    suspend fun insert(profile: MacProfile)

    @Delete
    suspend fun delete(profile: MacProfile)

    @Query("SELECT * FROM mac_profiles ORDER BY name ASC")
    suspend fun getAll(): List<MacProfile>

    @Query("SELECT * FROM mac_profiles WHERE id = :id")
    suspend fun getById(id: Int): MacProfile?
}

// ── Database ─────────────────────────────────────────────────────────

@Database(
    entities = [MacEntry::class, MacProfile::class],
    version = 2,
    exportSchema = false
)
abstract class MacHistoryDatabase : RoomDatabase() {
    abstract fun macHistoryDao(): MacHistoryDao
    abstract fun macProfileDao(): MacProfileDao

    companion object {
        @Volatile
        private var INSTANCE: MacHistoryDatabase? = null

        fun getInstance(context: Context): MacHistoryDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MacHistoryDatabase::class.java,
                    "mac_history.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
