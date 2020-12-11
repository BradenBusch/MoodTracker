package com.example.moodtracker.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

/**
 * Entry Entity.
 * This is the only entity in the database
 */

@Entity(tableName = "entry_table")
data class Entry(
    var mood: Int,
    var journal: String,
    var date: String
) {
    @PrimaryKey(autoGenerate = true)
    var entryId: Int = 0
}