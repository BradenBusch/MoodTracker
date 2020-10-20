package com.example.moodtracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Entry::class], version = 1)

abstract class DB: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun entryDao(): EntryDao

    companion object {
        @Volatile
        private var INSTANCE: DB? = null

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