package com.example.moodtracker.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    val getAllUsers: LiveData<List<User>>
    private val repository: UserRepository
    init {
        val userDao = DB.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        getAllUsers = repository.getAllUsers
    }

    // TODO add coroutines
    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO){
            repository.getAllUsers
        }
    }
}