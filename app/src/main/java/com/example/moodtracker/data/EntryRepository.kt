package com.example.moodtracker.data

import androidx.lifecycle.LiveData

/**
 * Entry repository. This class abstracts access to multiple data sources, and calls the DAOs methods
 */

class EntryRepository(private val entryDao: EntryDao) {

    suspend fun addEntry(entry: Entry) {
        entryDao.addEntry(entry)
    }
}