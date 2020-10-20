package com.example.moodtracker.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query

@Dao
interface UserDao {
    // TODO Handle the same username being entered
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY userName ASC")
    fun getAllUsers(): LiveData<List<User>>

}