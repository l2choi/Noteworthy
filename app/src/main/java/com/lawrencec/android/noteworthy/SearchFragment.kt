package com.lawrencec.android.noteworthy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

private const val TAG = "SearchFragment"

class SearchFragment : Fragment() {

    private lateinit var searchTerm : EditText
    private lateinit var searchButton : Button
    private lateinit var searchType : Spinner
    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //Populate the search type dropdown
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        searchType = view.findViewById(R.id.search_type)
        ArrayAdapter.createFromResource(
                requireContext(),
                R.array.search_by_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            searchType.adapter = adapter
        }
        searchTerm = view.findViewById(R.id.search_term) as EditText
        searchButton = view.findViewById(R.id.search_button) as Button

        activity?.setTitle(R.string.search)

        return view
    }

    override fun onStart() {
        super.onStart()

        searchButton.setOnClickListener {
            val textToSearch = searchTerm.text.toString()
            Log.d(TAG, "Text to search:$textToSearch")
        }

    }

}