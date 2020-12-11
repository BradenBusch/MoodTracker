package com.example.moodtracker.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.example.moodtracker.MainActivity
import com.example.moodtracker.R

/**
 * This class handles the login screen.
 * Handles login of the user, as in checks the PIN they created
 *
 */
class Login : AppCompatActivity() {

    private lateinit var pinEditText: EditText
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Hooks
        pinEditText = findViewById(R.id.login_et_pin)
        loginBtn = findViewById(R.id.login_btn_login)
        loginBtn.setOnClickListener { verifyFields() }
    }

    /*
     * Verify the username and PIN. This method will query the database to see if the user exists, and
     * if they do, log them in.
     */
    private fun verifyFields() {
        var pin: String = pinEditText.text.toString()
        if (TextUtils.isEmpty(pin)) {
            pinEditText.error = "Make sure you enter a pin!"
            return
        }
        // User entered a PIN
        val preferences: SharedPreferences = getSharedPreferences("PIN", Context.MODE_PRIVATE)
        val userPin: String? = preferences.getString("PIN", "0")
        if (userPin.equals(pin)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else {
            pinEditText.error = "Your pin is incorrect. Try another."
        }
    }
}
