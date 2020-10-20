package com.example.moodtracker.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

/**
 * Entry Entity. This is a one to many relationship with User (1) and Entry (N).
 */
// TODO these parentColumns and childColumns might not be right
@Entity(tableName = "entry_table",
        foreignKeys = [ForeignKey(entity = User::class,
            parentColumns = arrayOf("userName"),
            childColumns = arrayOf("userName"),
            onDelete = ForeignKey.CASCADE)])
class Entry (
    @PrimaryKey(autoGenerate = true)
    val entryId: Int,
    val mood: Int,
    val journal: String,
    val date: String,
    val userName: String
)
