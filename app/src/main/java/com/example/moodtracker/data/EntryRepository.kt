package com.example.moodtracker.data

import androidx.lifecycle.LiveData

class EntryRepository(private val entryDao: EntryDao) {

    suspend fun addEntry(entry: Entry) {
        entryDao.addEntry(entry)
    }
}