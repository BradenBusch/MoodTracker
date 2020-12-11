package com.example.moodtracker.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The way to actually access the database. Creating an EntryViewModel object allows calling these
 * methods below.
 */
class EntryViewModel(application: Application): AndroidViewModel(application) {

    val getAllEntriesLD: LiveData<List<Entry>>
    val getAllEntries: List<Entry>
    private val repository: EntryRepository
    init {
        val entryDao = DB.getDatabase(application).entryDao()
        repository = EntryRepository(entryDao)
        getAllEntries = repository.getAllEntries
        getAllEntriesLD = repository.getAllEntriesLD
    }

    fun addEntry(entry: Entry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addEntry(entry)
        }
    }

    fun getEntryOnDate(date: String): List<Entry> {
        return repository.getEntryOnDate(date)
    }
}