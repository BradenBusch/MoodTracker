package com.example.moodtracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moodtracker.R

/**
 * This class handles the "Entries" tab. It will show the user a list of their previous entries.
 * It will use a RecyclerView with an adapter to build the custom list.
 * To get the list, the database is queried to return all the entries from the logged in user.
 */
class Entries : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_entries, container, false)

    companion object {
        fun newInstance(): Entries = Entries()
    }
}