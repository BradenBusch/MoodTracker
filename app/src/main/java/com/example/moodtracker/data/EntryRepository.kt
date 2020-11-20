package com.example.moodtracker.data

import androidx.lifecycle.LiveData

/**
 * Entry repository. This class abstracts access to multiple data sources, and calls the DAOs methods
 */

class EntryRepository(private val entryDao: EntryDao) {

    val getAllEntries: List<Entry> = entryDao.getAllEntries()
    val getAllEntriesLD: LiveData<List<Entry>> = entryDao.getAllEntriesLD()

    suspend fun addEntry(entry: Entry) {
        entryDao.addEntry(entry)
    }

    fun getEntryOnDate(date: String): List<Entry> {
        return entryDao.getEntryOnDate(date)
    }
}