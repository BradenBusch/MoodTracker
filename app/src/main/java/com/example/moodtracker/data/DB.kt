package com.example.moodtracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * This class holds the actually Database object. It has already created an object and will continue
 * to re-use that same object instead of re-instantiating the database.
 */
@Database(entities = [User::class, Entry::class], version = 1)
abstract class DB: RoomDatabase() {
    // Daos
    abstract fun userDao(): UserDao
    abstract fun entryDao(): EntryDao

    companion object {
        @Volatile
        private var INSTANCE: DB? = null

        // Return the previously called database or a new instance if one doesn't exist
        fun getDatabase(context: Context): DB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DB::class.java,
                    "_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}