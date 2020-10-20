package com.example.moodtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moodtracker.data.User
import com.example.moodtracker.data.UserViewModel

/* TODO
 * I think that UserViewModel and EntryViewModel will handle opening the database..
 */

class MainActivity : AppCompatActivity() {
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        //insertUserToDatabase("CazualGingerr", 4486)
        //insertUserToDatabase("GNELSON", 2222)
        Log.d("BEEP", "ello!")
        // TODO this is how you print each user in the database
        mUserViewModel.getAllUsers.observe(this, Observer {
                list -> list.forEach{Log.d("BEEP", it.userName)}
        })
    }

    private fun insertUserToDatabase(user_name: String, pin: Int) {
        val user = User(user_name, pin)
        mUserViewModel.addUser(user)
    }
}