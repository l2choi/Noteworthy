package com.lawrencec.android.noteworthy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), CalendarFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Single Activity architecture
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = CalendarFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    //Callback function from CalendarFragment. Launches NoteFragment and places CalendarFragment
    //on the back stack.
    override fun onClickAddNoteFab() {
        val fragment = NoteFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("MainActivity")
            .commit()
    }

}