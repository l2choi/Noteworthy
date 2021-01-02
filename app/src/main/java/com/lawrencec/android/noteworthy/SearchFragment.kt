package com.lawrencec.android.noteworthy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment

class SearchFragment : Fragment() {

    private lateinit var searchTerm : EditText
    private lateinit var searchButton : Button
    private lateinit var searchType : Spinner

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        searchTerm = view.findViewById(R.id.search_term) as EditText
        searchType = view.findViewById(R.id.search_type)
        ArrayAdapter.createFromResource(
                requireContext(),
                R.array.search_type_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            searchType.adapter = adapter
        }
        searchButton = view.findViewById(R.id.search_button) as Button

        return view
    }


}