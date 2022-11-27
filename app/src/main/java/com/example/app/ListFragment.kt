package com.example.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Fragment that displays the list of stations and some of their information.
 */

private const val STATIONS = "stations"

class ListFragment : Fragment() {
    private lateinit var stationAdapter: StationAdapter
    private lateinit var rcvStations: RecyclerView
    private var stations: ArrayList<Station> = arrayListOf()
    private var listener: ListCallBack? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stations = it.getSerializable(STATIONS) as ArrayList<Station>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_list, container, false)

        //Link to a recycler view
        stationAdapter = StationAdapter(stations, listener)
        rcvStations = rootView.findViewById(R.id.f_list_rcv_stations)
        rcvStations.adapter = stationAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        rcvStations.layoutManager = linearLayoutManager

        //SeachView
        setUpSearchView(rootView)

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ListCallBack) {
            listener = context
        }
        else {
            throw java.lang.RuntimeException("$context must implement Favorite")
        }
    }

    override fun onDetach(){
        super.onDetach()
        listener = null
    }

    private fun setUpSearchView(rootView: View) {
        val searchView : SearchView = rootView.findViewById(R.id.f_list_searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                listener?.onSearch(query)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(stations: ArrayList<Station>) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(STATIONS, stations)
                }
            }
    }
}