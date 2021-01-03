package com.lawrencec.android.noteworthy

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

//Options fragment.
class OptionsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
    }
    
}