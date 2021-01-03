package com.lawrencec.android.noteworthy

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import java.util.*

private const val TAG = "NoteFragment"
private const val DIALOG_DATE = "DialogDate"
private const val REQUEST_CODE = 0

//Fragment for adding a new Note.
class NoteFragment : Fragment(), DatePickerFragment.Callbacks {

    private lateinit var note: Note
    private lateinit var titleField : EditText
    private lateinit var dateFieldButton : Button
    private lateinit var contentsField : EditText
    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button
    private val noteViewModel: NoteViewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        note = Note()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note, container, false)
        titleField = view.findViewById(R.id.note_title) as EditText
        dateFieldButton = view.findViewById(R.id.note_date) as Button
        contentsField = view.findViewById(R.id.note_contents) as EditText
        cancelButton = view.findViewById(R.id.cancel_button) as Button
        saveButton = view.findViewById(R.id.save_button) as Button

        return view
    }

    override fun onStart() {
        super.onStart()

        //Watchers check for any changes in the title and content text fields.
        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int) {

            }
            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int) {
                note.title = s.toString()
            }
            override fun afterTextChanged(s: Editable?) {

            }
        }
        titleField.addTextChangedListener(titleWatcher)

        val contentWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int) {

            }
            override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int) {
                note.contents = s.toString()
            }
            override fun afterTextChanged(s: Editable?) {

            }
        }
        contentsField.addTextChangedListener(contentWatcher)

        //Launch DatePickerFragment.
        //Set NoteFragment as the target so we can retrieve the date from the DatePickerFragment.
        //Place the currently selected date into a Bundle so DatePickerFragment can extract it.
        dateFieldButton.setOnClickListener {
            DatePickerFragment.newInstance(note.date).apply {
                setTargetFragment(this@NoteFragment, REQUEST_CODE)
                show(this@NoteFragment.requireFragmentManager(), DIALOG_DATE)
            }
        }

        cancelButton.setOnClickListener {
            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton(R.string.yes,
                            DialogInterface.OnClickListener { dialog, id ->
                                // User discards all changes. Simply pop NoteFragment from stack.
                                val manager = requireActivity().supportFragmentManager
                                manager.popBackStack()
                            })
                    setNegativeButton(R.string.no,
                            DialogInterface.OnClickListener { dialog, id ->
                                // User doesn't discard changes. Dismiss the dialog.

                            })
                }
                // Set other dialog properties
                builder.setTitle(R.string.attention)
                builder.setMessage(R.string.cancel_warning)
                // Create and show the AlertDialog
                builder.create()
                builder.show()
            }
        }

        saveButton.setOnClickListener {
            Log.d(TAG, "Save button pressed")
            noteViewModel.addNote(note)
            //After saving, go back to the CalendarFragment
            val manager = requireActivity().supportFragmentManager
            manager.popBackStack()
            val toast = Toast.makeText(
                    requireContext(),
                    R.string.note_saved,
                    Toast.LENGTH_SHORT)
            toast.show()
        }

    }

    override fun onStop() {
        super.onStop()
    }

    //Callback function from DatePickerFragment.
    override fun onDateSelected(date: Date) {
        note.date = date
        updateUI()
    }

    //Function to update the UI
    fun updateUI() {
        dateFieldButton.text = note.date.toString()
    }
}