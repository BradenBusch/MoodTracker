package com.example.moodtracker.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The way to actually access the database. Creating an UserViewModel object allows calling these
 * methods below.
 *
 * Syntax for instantiation:  mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java) [this is context, could be different]
 */
class UserViewModel(application: Application): AndroidViewModel(application) {
    val getAllUsers: LiveData<List<User>>
    private val repository: UserRepository
    init {
        val userDao = DB.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        getAllUsers = repository.getAllUsers
    }

    // TODO add coroutines
    /*
     * The following methods are coroutines that actually call the queries that were written in the DAO,
     * through accessing the repository.
     */
    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun userExists(username: String): LiveData<Int> {
        return repository.userExists(username)
    }

    fun getUser(username: String): LiveData<User>{
        return repository.getUser(username)
    }

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO){
            repository.getAllUsers
        }
    }
}