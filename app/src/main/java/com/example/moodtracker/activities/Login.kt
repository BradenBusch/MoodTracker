package com.example.moodtracker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.moodtracker.R

/**
 * This class handles the login screen. It will pass the current user in an Intent to the MainActivity.
 * It verifies the credentials of Login and PIN. We can also implement a "Stay Logged In" feature
 */
class Login : AppCompatActivity() {

    // Hooks
    private lateinit var usernameEditText: EditText
    private lateinit var pinEditText: EditText
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        usernameEditText = findViewById(R.id.login_et_username)
        pinEditText = findViewById(R.id.login_et_pin)
        loginBtn = findViewById(R.id.login_btn_login)
        loginBtn.setOnClickListener { verifyFields() }
    }

    /*
     * Verify the username and PIN. This method will query the database to see if the user exists, and
     * if they do, log them in.
     */
    fun verifyFields() {

    }

}