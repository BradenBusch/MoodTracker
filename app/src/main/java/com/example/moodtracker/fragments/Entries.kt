package com.example.moodtracker.fragments

import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moodtracker.R
import com.example.moodtracker.data.Entry
import com.example.moodtracker.data.EntryViewModel
import java.security.KeyStore


/**
 * This class handles the "Entries" tab. It will show the user a list of their previous entries.
 * It will use a RecyclerView with an adapter to build the custom list.
 * To get the list, the database is queried to return all the entries from the logged in user.
 */
class Entries : Fragment() {

    internal var expandableListView:  ExpandableListView? = null
    internal var adapter: EntryListAdapter? = null
    internal var titleList: List<String>? = null
    internal var imageList: ArrayList<Int> = arrayListOf()
    lateinit var mEntryViewModel: EntryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_entries, container, false)

        // Hooks
        mEntryViewModel = ViewModelProvider(this).get(EntryViewModel::class.java)
        expandableListView = rootView.findViewById(R.id.entries_expandableListView)
        if (expandableListView != null) {
            val listData = getData()
            adapter = EntryListAdapter(rootView.context)
            expandableListView!!.setAdapter(adapter)
            expandableListView!!.setOnGroupExpandListener { }

            expandableListView!!.setOnGroupCollapseListener { }

            expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                false
            }

        }
        return rootView
    }

    companion object {
        fun newInstance(): Entries = Entries()
    }

    private fun getData(): HashMap<String, List<String>> {
        val data = HashMap<String, List<String>>()

        mEntryViewModel.getAllEntriesLD.observe(viewLifecycleOwner, Observer { list->
            list.forEach { entry->
                // Journal is an array list, but it will always be one entry
                Log.d("Date Journal", "${entry.date} ${entry.journal}\n")
                val journal = ArrayList<String>()
                journal.add(entry.journal)
                data[entry.date] = journal
                addImageToList(entry.mood)
            }
            // TODO FIXME
            adapter?.setData(data, ArrayList(data.keys).sorted().reversed(), imageList.reversed())
            // idk if i even need this
            // titleList = ArrayList(data.keys)
        })
        Log.d("Data", data.toString())
        return data
    }

    private fun addImageToList(mood: Int) {
        if (mood == 1) {
            imageList.add(R.drawable.ic_terrible)
        }
        else if (mood == 2) {
            imageList.add(R.drawable.ic_bad)
        }
        else if (mood == 3) {
            imageList.add(R.drawable.ic_average)
        }
        else if (mood == 4) {
            imageList.add(R.drawable.ic_smile)
        }
        else if (mood == 5) {
            imageList.add(R.drawable.ic_great)
        }
    }
}