package com.example.moodtracker.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

/**
 * Entry Entity. This is a one to many relationship with User (1) and Entry (N).
 */
//// TODO these parentColumns and childColumns might not be right
//@Entity(tableName = "entry_table",
//        foreignKeys = [ForeignKey(entity = User::class,
//            parentColumns = arrayOf("userName"),
//            childColumns = arrayOf("entryId"),
//            onDelete = ForeignKey.CASCADE)])
//class Entry (
//    @PrimaryKey(autoGenerate = true)
//    val entryId: Int,
//    val mood: Int,
//    val journal: String,
//    val date: String,
//    val userName: String
//)

@Entity(tableName = "entry_table")
data class Entry(
    var mood: Int,
    var journal: String,
    var date: String
) {
    @PrimaryKey(autoGenerate = true)
    var entryId: Int = 0
}