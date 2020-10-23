package com.example.moodtracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moodtracker.R

/**
 * This class handles the Dashboard / Calendar tab.
 */
// TODO I think we can try the calendar, but if it fails just use a simple dashboard like entries in
//  a certain time frame, total entries, etc.
class Calendar : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_calendar, container, false)

    companion object {
        fun newInstance(): Calendar = Calendar()
    }
}