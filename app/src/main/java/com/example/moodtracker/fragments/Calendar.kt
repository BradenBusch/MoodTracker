package com.example.moodtracker.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moodtracker.MainActivity
import com.example.moodtracker.MonthYearPickerDialog
import com.example.moodtracker.R
import com.example.moodtracker.data.Entry
import com.example.moodtracker.data.EntryViewModel
import java.text.DateFormatSymbols

/**
 * This class handles the Dashboard / Calendar tab.
 */

class Calendar : Fragment() {
    lateinit var calendarBtn: ImageButton
    lateinit var dateTimeTv: TextView
    lateinit var greatCountTv: TextView
    lateinit var goodCountTv: TextView
    lateinit var averageCountTv: TextView
    lateinit var badCountTv: TextView
    lateinit var terribleCountTv: TextView
    lateinit var overallCountTv: TextView
    var greatCount: Int = 0
    var goodCount: Int = 0
    var averageCount: Int = 0
    var badCount: Int = 0
    var terribleCount: Int = 0
    var overallCount: Int = 0

    lateinit var mEntryViewModel: EntryViewModel
    val REQUEST_CODE = 11

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    // TODO fix to always show the current month
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        // Hooks
        calendarBtn = view.findViewById(R.id.calendar_imgbtn_calendar)
        dateTimeTv = view.findViewById(R.id.calendar_tv_dateyear)
        greatCountTv = view.findViewById(R.id.calendar_tv_greatCount)
        goodCountTv = view.findViewById(R.id.calendar_tv_goodCount)
        averageCountTv = view.findViewById(R.id.calendar_tv_averageCount)
        badCountTv = view.findViewById(R.id.calendar_tv_badCount)
        terribleCountTv = view.findViewById(R.id.calendar_tv_terribleCount)
        overallCountTv = view.findViewById(R.id.calendar_tv_totalCount)

        mEntryViewModel = ViewModelProvider(this).get(EntryViewModel::class.java)

        // Load values for the current month
        val calendar: android.icu.util.Calendar = android.icu.util.Calendar.getInstance()
        val month = calendar.get(android.icu.util.Calendar.MONTH)
        val year = calendar.get(android.icu.util.Calendar.YEAR)

        // Set values for current month
        loadValues((month + 1).toString()) // todo fix this +1 band-aid
        val realMonth = getMonth(month + 1)
        dateTimeTv.text = "${realMonth}, $year"
        overallCountTv.text = "Total Entries Made In ${realMonth}: $overallCount"

        // Set on click listener for handling picking a date
        calendarBtn.setOnClickListener {
            val pd = MonthYearPickerDialog()
            val dpl = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                dateTimeTv.text = "${getMonth(i2)}, $i"
                loadValues(i2.toString())
            }
            pd.setListener(dpl)
            pd.show(requireActivity().supportFragmentManager, "")
        }
        return view
    }
    companion object {
        fun newInstance(): Calendar = Calendar()
    }

    private fun getMonth(month: Int): String {
        return DateFormatSymbols().months[month - 1]
    }

    private fun loadValues(month: String) {
        resetCounts()
        var entries: List<Entry> = mEntryViewModel.getAllEntries
        entries.forEach {entry ->
            val trueDate = entry.date.split("/")[0]
            Log.d("Date and month", "$trueDate $month")
            // Months match
            if (trueDate.toInt() == month.toInt()) {
                Log.d("Date and month", "$trueDate $month")
                addToCount(entry.mood)
            }
        }
        greatCountTv.text = greatCount.toString()
        goodCountTv.text = goodCount.toString()
        averageCountTv.text = averageCount.toString()
        badCountTv.text = badCount.toString()
        terribleCountTv.text = terribleCount.toString()
        overallCountTv.text = "Total Entries Made In ${getMonth(month.toInt())}: $overallCount"
    }

    private fun addToCount(mood: Int) {
        overallCount++
        if (mood == 1) {
            terribleCount++
        }
        else if (mood == 2) {
            badCount++
        }
        else if (mood == 3) {
            averageCount++
        }
        else if (mood == 4) {
            goodCount++
        }
        else if (mood == 5) {
            greatCount++
        }
    }

    private fun resetCounts() {
        overallCount = 0
        terribleCount = 0
        badCount = 0
        averageCount = 0
        goodCount = 0
        greatCount = 0
    }


}