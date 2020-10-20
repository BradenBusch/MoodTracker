package com.example.moodtracker.data

import androidx.lifecycle.LiveData

/**
 * User repository. This class abstracts access to multiple data sources, and calls the DAOs methods
 */
class UserRepository(private val userDao: UserDao) {

    val getAllUsers: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }
}