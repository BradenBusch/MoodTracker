package com.example.moodtracker.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.moodtracker.MainActivity
import com.example.moodtracker.R

/**
 * Splash screen, this essentially only handles delaying the app loading.
 * Sets the activity and calls a handler method.
 */
class SpashScreen : AppCompatActivity() {
    private val splashTime: Long = 3000 // This is one second

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash_screen)

        Handler().postDelayed({
            val preferences: SharedPreferences = getSharedPreferences("SignedIn", Context.MODE_PRIVATE)
            val status: String? = preferences.getString("SignedIn", "0")
            if (status.equals("true")) {
                startActivity(Intent(this, Login::class.java))
            }
            else {
                startActivity(Intent(this, Signup::class.java))
            }
            // startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, splashTime)
    }
}