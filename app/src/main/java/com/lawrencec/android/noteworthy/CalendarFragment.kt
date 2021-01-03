package com.lawrencec.android.noteworthy

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var calendarView: CalendarView
    private lateinit var noteRecyclerView: RecyclerView
    private var adapter: NoteAdapter? = NoteAdapter(emptyList())
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
        calendarView = view.findViewById(R.id.monthlyView) as CalendarView

        noteRecyclerView = view.findViewById(R.id.note_recycler_view) as RecyclerView
        noteRecyclerView.layoutManager = LinearLayoutManager(context)
        noteRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //While the fragment is visible, check for new data. When a new note is created, update the
        //UI accordingly.
        calendarViewModel.noteListLiveData.observe(
            viewLifecycleOwner,
            Observer { notes ->
                notes?.let {
                    Log.i(TAG, "No. of notes:  ${notes.size}")
                    updateUI(notes)
                }
            }
        )
    }

    //Function to update the UI.
    private fun updateUI(notes: List<Note>) {
        adapter = NoteAdapter(notes)
        noteRecyclerView.adapter = adapter
    }

    //Wraps each item view in an instance of ViewHolder for the RecyclerView.
    private inner class NoteHolder(view: View)
        : RecyclerView.ViewHolder(view) {
            //Hold a reference to the TextViews so we can change them later easily
            val titleTextView: TextView = itemView.findViewById(R.id.note_title)
            val dateTextView : TextView = itemView.findViewById(R.id.note_date)
    }

    //Creates the necessary ViewHolders when needed
    private inner class NoteAdapter(var notes: List<Note>)
        : RecyclerView.Adapter<NoteHolder>() {

        //Creates the view to display by inflating the proper layout
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
            val view = layoutInflater.inflate(R.layout.list_item_note, parent, false)
            return NoteHolder(view)
        }

        override fun getItemCount() = notes.size

        //Populate a given holder, and replace the placeholder text with a note's title and date.
        override fun onBindViewHolder(holder: NoteHolder, position: Int) {
            val note = notes[position]
            holder.apply {
                titleTextView.text = note.title
                dateTextView.text = note.date.toString()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        addNewNoteButton.setOnClickListener {
            Log.d(TAG, "Add new note FAB clicked")
            callbacks?.onClickAddNoteFab()
        }

        calendarView.setOnDateChangeListener {
            view,
            year,
            month,
            dayOfMonth ->
            //Zero index so January = 0
            Log.d(TAG, "New date (MM/DD/YYYY) is: " + month + dayOfMonth + year )

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

    //Operations related to menu items.
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