package com.example.moodtracker.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The way to actually access the database. Creating an EntryViewModel object allows calling these
 * methods below.
 *
 * Syntax for instantiation:  mEntryViewModel = ViewModelProvider(this).get(EntryViewModel::class.java) [this is context, could be different]
 */
class EntryViewModel(application: Application): AndroidViewModel(application) {
    // TODO Setters. Add method names in init{}
    private val repository: EntryRepository
    init {
        val entryDao = DB.getDatabase(application).entryDao()
        repository = EntryRepository(entryDao)

    }

    // TODO add more coroutines
    fun addEntry(entry: Entry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addEntry(entry)
        }
    }
}