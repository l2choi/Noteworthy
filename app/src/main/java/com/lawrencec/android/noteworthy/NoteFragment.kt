package com.lawrencec.android.noteworthy

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
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

        //Launch DatePickerFragment.
        //Set NoteFragment as the target so we can retrieve the date from the DatePickerFragment.
        dateFieldButton.setOnClickListener {
            DatePickerFragment().apply {
                setTargetFragment(this@NoteFragment, REQUEST_CODE)
                show(this@NoteFragment.requireFragmentManager(), DIALOG_DATE)
            }
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