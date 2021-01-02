package com.lawrencec.android.noteworthy

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val TAG = "CalendarFragment"

//Calendar Fragment. This allows the user to navigate to a day to see what has been entered on that
//day, along with the option to add a new note.
class CalendarFragment : Fragment() {

    interface Callbacks {
        fun onClickAddNoteFab()
    }

    private var callbacks: Callbacks? = null

    private lateinit var addNewNoteButton: FloatingActionButton

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        addNewNoteButton = view.findViewById(R.id.add_new_record_button) as FloatingActionButton

        return view
    }

    override fun onStart() {
        super.onStart()

        addNewNoteButton.setOnClickListener {
            Log.d(TAG, "Add new note FAB clicked")
            callbacks?.onClickAddNoteFab()
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

}