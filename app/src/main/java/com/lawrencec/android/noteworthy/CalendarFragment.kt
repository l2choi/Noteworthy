package com.lawrencec.android.noteworthy

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
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
    private val calendarViewModel: CalendarViewModel by lazy {
        ViewModelProviders.of(this).get(CalendarViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_calendar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                val fragment = SearchFragment()
                val manager = requireActivity().supportFragmentManager
                manager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack("CalendarFragment")
                        .commit()
                true
            }
            R.id.erase_all -> {
                val alertDialog: AlertDialog? = activity?.let {

                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setPositiveButton(R.string.yes,
                                DialogInterface.OnClickListener { dialog, id ->
                                    // User deletes all notes.
                                    calendarViewModel.deleteAllNotes()
                                    val toast = Toast.makeText(
                                            requireContext(),
                                            R.string.erase_all_toast,
                                            Toast.LENGTH_SHORT)
                                    toast.show()
                                })
                        setNegativeButton(R.string.no,
                                DialogInterface.OnClickListener { dialog, id ->
                                    // User doesn't delete all notes.
                                    val toast = Toast.makeText(
                                            requireContext(),
                                            R.string.action_cancelled,
                                            Toast.LENGTH_SHORT)
                                    toast.show()
                                })
                    }
                    // Set other dialog properties
                    builder.setTitle(R.string.attention)
                    builder.setMessage(R.string.erase_all_warning)
                    // Create and show the AlertDialog
                    builder.create()
                    builder.show()
                }
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

}