package com.lawrencec.android.noteworthy

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.w3c.dom.Text
import java.util.*

private const val TAG = "NoteFragment"
private const val DIALOG_DATE = "DialogDate"
private const val REQUEST_CODE = 0

//Fragment for adding a new Note.
class NoteFragment : Fragment(), DatePickerFragment.Callbacks {

    private lateinit var note: Note
    private lateinit var titleField : TextInputEditText
    private lateinit var dateFieldButton : Button
    private lateinit var contentsField : TextInputEditText
    private lateinit var titleFieldTextLayout : TextInputLayout
    private lateinit var contentsFieldTextLayout: TextInputLayout
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
        titleFieldTextLayout = view.findViewById(R.id.textinputlayout_title) as TextInputLayout
        titleField = view.findViewById(R.id.note_title) as TextInputEditText
        dateFieldButton = view.findViewById(R.id.note_date) as Button
        contentsFieldTextLayout = view.findViewById(R.id.textinputlayout_contents) as
                TextInputLayout
        contentsField = view.findViewById(R.id.note_contents) as TextInputEditText
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
            var validationsPassed = validateInputs()

            if (validationsPassed) {
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

    }

    override fun onStop() {
        super.onStop()
    }

    //Callback function from DatePickerFragment.
    override fun onDateSelected(date: Date) {
        note.date = date
        updateUI()
    }

    //Function to update the UI.
    private fun updateUI() {
        dateFieldButton.text = note.date.toString()
    }

    //Function to validate inputs.
    private fun validateInputs() : Boolean {
        //Clear any outstanding errors before checking.
        titleFieldTextLayout.error = null
        contentsFieldTextLayout.error = null

        var maxTitleLength = titleFieldTextLayout.counterMaxLength
        var maxContentLength = contentsFieldTextLayout.counterMaxLength

        var titleLength = titleField.length()
        var contentLength = contentsField.length()

        if(TextUtils.isEmpty(titleField.text.toString())) {
            titleFieldTextLayout.error = getString(R.string.field_empty)
        }

        if(TextUtils.isEmpty(contentsField.text.toString())) {
            contentsFieldTextLayout.error = getString(R.string.field_empty)
        }

        if (titleLength > maxTitleLength) {
            titleFieldTextLayout.error = getString(R.string.input_length_exceeded)
        }

        if (contentLength > maxContentLength) {
            contentsFieldTextLayout.error = getString(R.string.input_length_exceeded)
        }

        //Final check: make sure no errors set
        return (titleFieldTextLayout.error == null && contentsFieldTextLayout.error == null)
    }
}