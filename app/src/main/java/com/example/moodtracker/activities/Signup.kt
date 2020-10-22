package com.example.moodtracker.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moodtracker.MainActivity
import com.example.moodtracker.R
import com.example.moodtracker.data.User
import com.example.moodtracker.data.UserViewModel

/**
 * This class handles the sign-up screen. It should write to SharedPreferences an account is created
 * to show the Login screen instead. If the user clicks Log-Out from the app, this screen will be
 * shown again.
 *
 * This class also handles database insert of a new User
 */
class Signup : AppCompatActivity() {

    // Hooks
    private lateinit var usernameEditText: EditText
    private lateinit var pinEditText: EditText
    private lateinit var confPinEditText: EditText
    private lateinit var submitBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        usernameEditText = findViewById(R.id.signup_et_username)
        pinEditText = findViewById(R.id.signup_et_pin)
        confPinEditText = findViewById(R.id.signup_et_pinConfirm)
        submitBtn = findViewById(R.id.signup_btn_signup)
        submitBtn.setOnClickListener { verfiyFields() }
    }

    /*
     * Verify the Username, PIN, and Confirm PIN fields. This method will also create the User if all
     * fields are verified propery.
     */
    fun verfiyFields() {
        var username: String = usernameEditText.text.toString()
        var pin: String = pinEditText.text.toString()
        var pinConf: String = confPinEditText.text.toString()

        // First check if any of the fields are empty
        if (TextUtils.isEmpty(username)) {
            usernameEditText.error = "Please make a username."
        }
        if (TextUtils.isEmpty(pin)) {
            pinEditText.error = "Make sure you enter a pin!"
        }
        if (TextUtils.isEmpty(pinConf)) {
            confPinEditText.error = "Please confirm your pin"
        }

        // Check if pins are all integers and length 4
        val isPinNum: Boolean = pin.chars().allMatch(Character::isDigit)
        val isConfPinNum: Boolean = pinConf.chars().allMatch(Character::isDigit)
        if (!isPinNum || !isConfPinNum || pin.length != 4 || pinConf.length != 4) {
            pinEditText.error = "Make sure to use a 4 digit number as your pin."
            confPinEditText.error = "Make sure to use a 4 digit number as your pin."
            return
        }
        // Pins are all integers. Check if they equal each other.
        if (Integer.valueOf(pin) != Integer.valueOf(pinConf)) {
            confPinEditText.error = "Make sure your pins match"
            return
        }
        // Pins match, now query the database to see if this username and pin is taken
        val mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.userExists(username).observe(this, Observer {count ->
            Log.d("BEEP", count.toString())
            // "count" is the return of userExists, which is a query to check if there is already a user with this name
            // This username is taken
            if (count == 1) {
                usernameEditText.error = "Sorry, but this username is already taken. Try another."
            }
            // Username is open, add this to the database and move on to MainActivity
            else {
                val user = User(username, Integer.valueOf(pin))
                mUserViewModel.addUser(user)
                val editor: SharedPreferences.Editor = getSharedPreferences("SignedIn", Context.MODE_PRIVATE).edit()
                editor.putString("SignedIn", "true")
                editor.apply()
                var intent = Intent(this, MainActivity::class.java)
                // Pass the user name to MainActivity
                intent.putExtra("LoggedInUser", username)
                startActivity(intent)
                finish()
            }
        })
    }
}