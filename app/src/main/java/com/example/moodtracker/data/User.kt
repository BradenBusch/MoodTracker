package com.example.moodtracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * User Entity. This is a one to many relationship with User (1) and Entry (N).
 */
@Entity(tableName = "user_table")
data class User (
    @PrimaryKey
    val userName: String,
    val PIN: Int
)