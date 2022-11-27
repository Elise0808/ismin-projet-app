package com.example.app

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        stationAdapter = StationAdapter(stations, listener)

        //Link to a recycler view
        rcvStations = rootView.findViewById(R.id.f_list_rcv_stations)
        rcvStations.adapter = stationAdapter

        val linearLayoutManager = LinearLayoutManager(context)
        rcvStations.layoutManager = linearLayoutManager

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