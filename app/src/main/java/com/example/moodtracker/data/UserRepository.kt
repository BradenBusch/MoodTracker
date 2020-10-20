package com.example.moodtracker.data

import androidx.lifecycle.LiveData

/**
 * This class abstracts access to multiple data sources
 */
class UserRepository(private val userDao: UserDao) {

    val getAllUsers: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }
}