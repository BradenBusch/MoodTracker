package com.example.moodtracker

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.fragment.app.FragmentActivity

class MonthYearPickerDialog(): DialogFragment() {
    private val MAX_YEAR = 2099
    private lateinit var listener: DatePickerDialog.OnDateSetListener
    private val mActivity = activity

    fun setListener(listener: DatePickerDialog.OnDateSetListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(requireActivity())
        var inflater = requireActivity().layoutInflater
        var calendar = Calendar.getInstance()
        var dialog = inflater.inflate(R.layout.date_picker_dialog, null)
        val monthPicker: NumberPicker = dialog.findViewById(R.id.picker_month)
        val yearPicker: NumberPicker = dialog.findViewById(R.id.picker_year)

        monthPicker.minValue = 1
        monthPicker.maxValue = 12
        monthPicker.value = calendar.get(Calendar.MONTH)

        val year = calendar.get(Calendar.YEAR)
        yearPicker.minValue = year
        yearPicker.maxValue = MAX_YEAR
        yearPicker.value = year

        builder.setView(dialog)
            .setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                listener.onDateSet(null, yearPicker.value, monthPicker.value, 0)
            }
            .setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int ->
                this.dialog?.cancel()
            }
        return builder.create()
    }

}