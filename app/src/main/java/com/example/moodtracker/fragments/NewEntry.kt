package com.example.moodtracker.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moodtracker.R
import com.example.moodtracker.data.DB
import com.example.moodtracker.data.Entry
import com.example.moodtracker.data.EntryViewModel
import com.example.moodtracker.data.UserViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * This class handles the "New Entry" tab. It will allow a user to make a new entry which is stored in the database.
 * If the user try's to make multiple entries in one day, they will be prompted saying that their new entry will replace the old one
 */
class NewEntry : Fragment() {

    // TODO make method handling the clicking of the buttons. this method should increase the size of
    //  one of the buttons when clicked, and when another is clicked shrink it back.
    //  - Make a dialog box telling the user that they can only submit once per day
    private lateinit var coolBtn: ImageButton
    private lateinit var goodBtn: ImageButton
    private lateinit var averageBtn: ImageButton
    private lateinit var badBtn: ImageButton
    private lateinit var terribleBtn: ImageButton
    private lateinit var buttonList: Array<ImageButton>
    private lateinit var dateTextView: TextView
    private lateinit var greetingHeader: TextView
    private lateinit var journalEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var mEntryViewModel: EntryViewModel


    private var clickedEmotion: Int = 0 // the emotion (will end up being 5-1)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_new_entry, container, false)
        //activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        // Hooks
        coolBtn = rootView.findViewById(R.id.newEntry_imgbtn_cool)
        goodBtn = rootView.findViewById(R.id.newEntry_imgbtn_happy)
        averageBtn = rootView.findViewById(R.id.newEntry_imgbtn_average)
        badBtn = rootView.findViewById(R.id.newEntry_imgbtn_bad)
        terribleBtn = rootView.findViewById(R.id.newEntry_imgbtn_terrible)
        dateTextView = rootView.findViewById(R.id.newEntry_tv_calendardate)
        greetingHeader = rootView.findViewById(R.id.newEntry_tv_helloheader)
        journalEditText = rootView.findViewById(R.id.newEntry_et_journal)
        saveButton = rootView.findViewById(R.id.newEntry_btn_save)

        mEntryViewModel = ViewModelProvider(this).get(EntryViewModel::class.java)
        // Get current date
        val date = LocalDateTime.now()
        val format = DateTimeFormatter.ofPattern("MM/dd/yyyy. hh:mm a")
        val setDate = date.format(format)
        dateTextView.text = setDate

        // Get the username
        val sharedPreferences: SharedPreferences = rootView.context.getSharedPreferences("Username", Context.MODE_PRIVATE)
        val userName: String? = sharedPreferences.getString("UserName", "0")

        // Personalize the hello
        greetingHeader.text = "${greetingHeader.text}$userName"

        // Make list of buttons
        buttonList = arrayOf(coolBtn, goodBtn, averageBtn, badBtn, terribleBtn)

        // Set the clicks of the buttons
        setButtonClicks(rootView, setDate)
        //printAllEntries()
        return rootView
    }

    companion object {
        fun newInstance(): NewEntry = NewEntry()
    }

    /*
     * Helper method to print all the entries in the data base
     */
//    private fun printAllEntries() {
//        mEntryViewModel = ViewModelProvider(this).get(EntryViewModel::class.java)
//        mEntryViewModel.getAllEntries.observe(viewLifecycleOwner, Observer {
//            list -> list.forEach{Log.d("BEEP", "EntryID: ${it.entryId} Mood: ${it.mood} Date: ${it.date} Journal: ${it.journal}")}
//        })
//    }

    private fun resetButtonClicks() {
        for (button in buttonList) {
            button.setColorFilter(resources.getColor(R.color.black))
        }
    }

    /*
     * Set the button clicks for every button on the screen
     */
    private fun setButtonClicks(view: View,  date: String) {
        coolBtn.setOnClickListener {
            resetButtonClicks()
            coolBtn.setColorFilter(resources.getColor(R.color.great))
            clickedEmotion = 5
            Log.d("ClickedEmotion", clickedEmotion.toString())
        }
        goodBtn.setOnClickListener {
            resetButtonClicks()
            goodBtn.setColorFilter(resources.getColor(R.color.good))
            clickedEmotion = 4
            Log.d("ClickedEmotion", clickedEmotion.toString())
        }
        averageBtn.setOnClickListener {
            resetButtonClicks()
            averageBtn.setColorFilter(resources.getColor(R.color.average))
            clickedEmotion = 3
            Log.d("ClickedEmotion", clickedEmotion.toString())
        }
        badBtn.setOnClickListener {
            resetButtonClicks()
            badBtn.setColorFilter(resources.getColor(R.color.bad))
            clickedEmotion = 2
            Log.d("ClickedEmotion", clickedEmotion.toString())
        }
        terribleBtn.setOnClickListener {
            resetButtonClicks()
            terribleBtn.setColorFilter(resources.getColor(R.color.terrible))
            clickedEmotion = 1
            Log.d("ClickedEmotion", clickedEmotion.toString())
        }
        /*
         * Clear the EditText
         * Make a new entry
         * Dialog box saying that their response has been recorded
         */
        saveButton.setOnClickListener {
            if (clickedEmotion == 0) {
                Toast.makeText(context, "Remember to tap an emotion before saving!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            // Check if the dialog should be shown
            val sharedPreferences = view.context.getSharedPreferences("ShowDialog", Context.MODE_PRIVATE)
            val showDialog: Boolean? = sharedPreferences.getBoolean("ShowDialog", true)

            // Check if there was already an entry made today
            val entryMadeToday = entryAlreadyMade(date)
            Log.d("Entry Made today right here", entryMadeToday.toString())

            if (entryMadeToday) {
                Log.d("ENTERY WAS MADE", "")
                Toast.makeText(context, "You already made an entry today, come back tomorrow to make another.", Toast.LENGTH_LONG).show()
            }

            else {
                // Build a dialog box that lets the user know that they can only make one entry per day
                if (showDialog!!) {
                    val dialogBuilder = AlertDialog.Builder(view.context)
                    dialogBuilder.setMessage("You can only make one entry per day, would you like to continue?")
                        .setPositiveButton("Save", DialogInterface.OnClickListener { dialog, i ->
                            saveEntry(clickedEmotion, date, journalEditText.text.toString())
                            dialog.cancel()
                        })
                        .setNeutralButton("OK, don't show again", DialogInterface.OnClickListener { dialog, i ->
                            saveEntry(clickedEmotion, date, journalEditText.text.toString())
                            dialog.cancel()
                            val editor: SharedPreferences.Editor = view.context.getSharedPreferences("ShowDialog", Context.MODE_PRIVATE).edit()
                            editor.putBoolean("ShowDialog", false)
                            editor.apply()
                        })
                        .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, i ->
                            dialog.cancel()
                        })
                    val alert = dialogBuilder.create()
                    alert.setTitle("")
                    alert.show()
                }
                else {
                    saveEntry(clickedEmotion, date, journalEditText.text.toString())
                }
            }
        }
    }

    /*
     * Check if there was already an entry made on the current date.
     */
    private fun entryAlreadyMade(date: String): Boolean {
        var date = date.split(".")[0]
        var list: List<Entry> = mEntryViewModel.getEntryOnDate(date)
        if (list.isEmpty()) {
            return false
        }
        return true
    }


    /*
     * Save an entry to the database.
     * mood, date, and journal are the attributes for the Entry entity.
     */
    private fun saveEntry(mood: Int, date: String, journal: String) {
        Log.d("ClickedEmotion", clickedEmotion.toString())
        Log.d("Date", date)
        val mDate = date.split(".")[0]
        Log.d("Date", mDate)
        val entry = Entry(mood, journal, mDate)
        val mEntryViewModel = ViewModelProvider(this).get(EntryViewModel::class.java)
        mEntryViewModel.addEntry(entry)
        journalEditText.text.clear()
    }
}