package com.lawrencec.android.noteworthy

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

//Options fragment.
class OptionsFragment : PreferenceFragmentCompat() {

    private val optionsViewModel: OptionsViewModel by lazy {
        ViewModelProviders.of(this).get(OptionsViewModel::class.java)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)

    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        return when (preference?.key) {
            getString(R.string.key_erase_all) -> {
                val alertDialog: AlertDialog? = activity?.let {

                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setPositiveButton(R.string.yes,
                            DialogInterface.OnClickListener { dialog, id ->
                                // User deletes all notes.
                                optionsViewModel.deleteAllNotes()
                                val toast = Toast.makeText(
                                    requireContext(),
                                    R.string.erase_all_toast,
                                    Toast.LENGTH_SHORT
                                )
                                toast.show()
                            })
                        setNegativeButton(R.string.no,
                            DialogInterface.OnClickListener { dialog, id ->
                                // User doesn't delete all notes.
                                val toast = Toast.makeText(
                                    requireContext(),
                                    R.string.action_cancelled,
                                    Toast.LENGTH_SHORT
                                )
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
            else -> return super.onPreferenceTreeClick(preference)
        }

    }
}