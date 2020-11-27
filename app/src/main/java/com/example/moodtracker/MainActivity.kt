package com.example.moodtracker

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moodtracker.fragments.Calendar
import com.example.moodtracker.fragments.Entries
import com.example.moodtracker.fragments.NewEntry
import com.google.android.material.bottomnavigation.BottomNavigationView

/* TODO All that main activity should do is instantiate the fragment and navigation components of the app
 *  we should consider making a logo and using that instead of a textview
 */

/**
 * MainActivity controls how the three primary fragments interact with each other.
 * This class instantiates the bottom nav bar, setting its on click listeners (which fragment to open)
 * This class also will get the currently logged in user, and pass around as needed
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the username for use in a UI if needed
        val preferences: SharedPreferences = getSharedPreferences("UserName", Context.MODE_PRIVATE)
        val username: String? = preferences.getString("UserName", "0")

        // Apply on clicks for the bottom nav bar
        val bottomNavBar: BottomNavigationView = findViewById(R.id.mainactivity_bottomnav_navbar)
        bottomNavBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavBar.selectedItemId = R.id.action_newEntry

        // Show the user a dialog box the first time (this can be disabled)
        val spCheck: SharedPreferences = getSharedPreferences("ShowDialog", Context.MODE_PRIVATE)
        val bool = spCheck.getBoolean("ShowDialog", false)
        // Only show the alert dialog if the user hasn't clicked "dont show again"
        if (bool) {
            val editor: SharedPreferences.Editor = getSharedPreferences("ShowDialog", Context.MODE_PRIVATE).edit()
            editor.putBoolean("ShowDialog", true)
            editor.apply()
        }

    }

    /*
     * Listener for each fragment. This allows switching between the three.
     */
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_calendar -> {
                val fragment = Calendar.newInstance()
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_newEntry -> {
                val fragment = NewEntry.newInstance()
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_entries -> {
                val fragment = Entries.newInstance()
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false // idk what this false is for, i think its like default in a switch
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