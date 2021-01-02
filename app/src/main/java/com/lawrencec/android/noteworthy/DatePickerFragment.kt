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

        val date = arguments?.getSerializable(ARG_DATE) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
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

    //Companion object to pass dates between NoteFragment and DatePickerFragment.
    //If a date is already selected, this ensures the DatePickerDialog displays the correct date.
    //When the user changes the date, this ensures NoteFragment receives it.
    companion object {
        fun newInstance(date: Date) : DatePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_DATE, date)
            }
            return DatePickerFragment().apply {
                arguments = args
            }
        }
    }

}