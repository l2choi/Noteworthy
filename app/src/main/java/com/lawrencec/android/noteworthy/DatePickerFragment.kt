package com.lawrencec.android.noteworthy

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

private const val ARG_DATE = "date"

//DialogFragment displayed when the user enters a date on the NoteFragment
class DatePickerFragment: DialogFragment() {

    interface Callbacks {
        fun onDateSelected(date: Date)
    }

    //The dateListener checks for any changes in the date. It passes the results back to
    //NoteFragment.
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dateListener = DatePickerDialog.OnDateSetListener {
                view,
                year,
                month,
                dayOfMonth ->

            val resultDate : Date = GregorianCalendar(year,month, dayOfMonth).time

            targetFragment?.let {
                fragment -> (fragment as Callbacks).onDateSelected(resultDate)
            }
        }

        val calendar = Calendar.getInstance()
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(),
            dateListener,
            initialYear,
            initialMonth,
            initialDay
        )
    }

}