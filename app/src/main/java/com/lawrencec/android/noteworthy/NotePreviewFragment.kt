package com.lawrencec.android.noteworthy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import java.util.*

private const val TAG = "NotePreviewFragment"
private const val ARG_NOTEID = "note_id"
private const val ARG_TITLE = "note_title"
private const val ARG_DATE = "note_date"
private const val ARG_CONTENTS = "note_contents"

//Fragment that appears when user selects a note from the CalendarFragment.
class NotePreviewFragment : DialogFragment() {

    private lateinit var titleField : TextView
    private lateinit var dateField: TextView
    private lateinit var contentsField: TextView
    private lateinit var editButton : Button
    private lateinit var deleteButton : Button
    private val notePreviewViewModel: NotePreviewViewModel by lazy {
        ViewModelProviders.of(this).get(NotePreviewViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notepreview, container, false)
        val noteid = arguments?.getSerializable(ARG_NOTEID)
        val title = arguments?.getSerializable(ARG_TITLE)
        val date = arguments?.getSerializable(ARG_DATE)
        val contents = arguments?.getSerializable(ARG_CONTENTS)

        titleField = view.findViewById(R.id.title_preview)
        dateField = view.findViewById(R.id.date_preview)
        contentsField = view.findViewById(R.id.contents_preview)
        editButton = view.findViewById(R.id.edit_button)
        deleteButton = view.findViewById(R.id.delete_button)

        titleField.text = title.toString()
        dateField.text = date.toString()
        contentsField.text = contents.toString()

        deleteButton.setOnClickListener {
            activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton(R.string.yes) {
                        _, _ ->
                        //Delete an individual note.
                        val finalUUID = UUID.fromString(noteid.toString())
                        if (finalUUID != null) {
                            notePreviewViewModel.deleteNoteById(finalUUID)
                            val toast = Toast.makeText(
                                    requireContext(),
                                    R.string.delete_toast,
                                    Toast.LENGTH_SHORT
                            )
                            toast.show()
                            dismiss()
                        }
                    }
                    setNegativeButton(R.string.no) {
                        _, _ ->
                        //Cancel
                        val toast = Toast.makeText(
                                requireContext(),
                                R.string.action_cancelled,
                                Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                }
                // Set other dialog properties
                builder.setTitle(R.string.attention)
                builder.setMessage(R.string.delete_warning)
                // Create and show the AlertDialog
                builder.create()
                builder.show()
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    //Companion object to receive the contents of a note.
    companion object {
        fun newInstance(
                id: UUID,
                title: String,
                date: String,
                contents: String
        ) : NotePreviewFragment {
            val args = Bundle().apply {
                putSerializable(ARG_NOTEID, id)
                putSerializable(ARG_TITLE, title)
                putSerializable(ARG_DATE, date)
                putSerializable(ARG_CONTENTS, contents)
            }
            return NotePreviewFragment().apply {
                arguments = args
            }
        }
    }

}