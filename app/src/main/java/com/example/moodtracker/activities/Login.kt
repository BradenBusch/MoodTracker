package com.example.moodtracker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moodtracker.MainActivity
import com.example.moodtracker.R
import com.example.moodtracker.data.User
import com.example.moodtracker.data.UserViewModel

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
    private fun verifyFields() {
        var username: String = usernameEditText.text.toString()
        var pin: String = pinEditText.text.toString()

        // Check if username or pin is empty
        if (TextUtils.isEmpty(username)) {
            usernameEditText.error = "Please make a username."
            return
        }
        if (TextUtils.isEmpty(pin)) {
            pinEditText.error = "Make sure you enter a pin!"
            return
        }
        // TODO Remove logs
        // Not empty, check if the username exists
        var userExists = 0
        Log.d("BEEP", "Username $username    User Exists: $userExists")
        val mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.userExists(username).observe(this, Observer {
            Log.d("BEEP", "IT: $it")
            userExists = it
            // Username doesn't exist
            if (userExists != 1) {
                usernameEditText.error = "This username doesn't exist, try again."
            }
            // Username exists, check if pins match
            else {
                Log.d("BEEP", userExists.toString())
                var user: User?
                mUserViewModel.getUser(username).observe(this, Observer {u ->
                    user = u
                    // Pins match, log the user in
                    if (user!!.PIN == Integer.valueOf(pin)) {
                        var intent = Intent(this, MainActivity::class.java)
                        // Pass the user name to MainActivity
                        intent.putExtra("LoggedInUser", username)
                        startActivity(intent)
                        finish()
                    } else {
                        pinEditText.error = "Sorry, but your pin doesn't match the username."
                    }
                })
            }
        })
    }

}