package com.example.hoangcv2_task.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hoangcv2_task.Converters
import com.example.hoangcv2_task.model.AccountActivity
import com.example.hoangcv2_task.model.AccountStatus

@Database(
    entities = [AccountActivity::class, AccountStatus::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AccountActivityDatabase : RoomDatabase() {
    abstract fun AccountActivityDAO(): AccountActivityDAO

    companion object {
        private const val DB_NAME = "account_activity_database.db"

        @Volatile
        private var instance: AccountActivityDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AccountActivityDatabase::class.java,
            DB_NAME
        ).build()
    }
}