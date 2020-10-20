package com.example.moodtracker.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object for Entry. This is queries, insert, delete, update, etc. are written.
 * Suspend is used to call the database function as a coroutine.
 */
@Dao
interface EntryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addEntry(entry: Entry)

    @Query("SELECT * FROM entry_table WHERE userName=:userName")
    fun getAllUserEntries(userName: String): LiveData<List<Entry>>
}