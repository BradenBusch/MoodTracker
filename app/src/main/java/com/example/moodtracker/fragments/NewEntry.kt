package com.example.moodtracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moodtracker.R

/**
 * This class handles the "New Entry" tab. It will allow a user to make a new entry which is stored in the database.
 */
class NewEntry : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_new_entry, container, false)

    companion object {
        fun newInstance(): NewEntry = NewEntry()
    }
}