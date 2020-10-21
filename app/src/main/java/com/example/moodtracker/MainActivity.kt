package com.example.moodtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moodtracker.data.User
import com.example.moodtracker.data.UserViewModel

/* TODO All that main activity should do is instantiate the fragment and navigation components of the app
 *  we should consider making a logo and using that instead of a textview
 */

/**
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