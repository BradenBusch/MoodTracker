package com.example.moodtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moodtracker.data.User
import com.example.moodtracker.data.UserViewModel
import com.example.moodtracker.fragments.NewEntry
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        val bottomNavBar: BottomNavigationView = findViewById(R.id.mainactivity_bottomnav_navbar)
        bottomNavBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun insertUserToDatabase(user_name: String, pin: Int) {
        val user = User(user_name, pin)
        mUserViewModel.addUser(user)
    }

    /*
     * Listener for each fragment. This allows switching between the three
     */
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_calendar -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_newEntry -> {
                val fragment = NewEntry.newInstance()
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_entries -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    /*
     * Helper function to add our fragment to the ui.
     * TODO caution, this seems sketch to me
     *  https://code.tutsplus.com/tutorials/how-to-code-a-bottom-navigation-bar-for-an-android-app--cms-30305
     */
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}