package com.example.macchanger

import android.content.Context
import androidx.room.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Room entity that stores each MAC address change with a timestamp.
 */
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

@Database(entities = [MacEntry::class], version = 1, exportSchema = false)
abstract class MacHistoryDatabase : RoomDatabase() {
    abstract fun macHistoryDao(): MacHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: MacHistoryDatabase? = null

        fun getInstance(context: Context): MacHistoryDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MacHistoryDatabase::class.java,
                    "mac_history.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
