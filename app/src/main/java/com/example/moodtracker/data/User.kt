package com.example.moodtracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (
    @PrimaryKey
    val userName: String,
    val PIN: Int
)