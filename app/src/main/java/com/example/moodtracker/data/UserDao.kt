package com.example.moodtracker.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query

/**
 * Data Access Object for User. This is queries, insert, delete, update, etc. are written.
 * Suspend is used to call the database function as a coroutine.
 */
@Dao
interface UserDao {
    // TODO Handle the same username being entered
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY userName ASC")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT COUNT(userName) FROM user_table WHERE userName =:username")
    fun userExists(username: String) : LiveData<Int>

    @Query("SELECT * FROM user_table WHErE userName=:username")
    fun getUser(username: String): LiveData<User> // TODO finish writing this in DAO, etc
}