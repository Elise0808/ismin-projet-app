package com.example.app

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Fragment that displays the list of stations and some of their information.
 */

private const val DATA_PATH = "data path"

class ListFragment : Fragment() {
    private lateinit var stationAdapter: StationAdapter
    private lateinit var rcvStations: RecyclerView
    private lateinit var dataPath: String
    private var stations: ArrayList<Station> = arrayListOf()
    private var listener: ListCallBack? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dataPath = it.getString(DATA_PATH).toString()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_list, container, false)

        readFromGSON()

        //Link to a recycler view
        stationAdapter = StationAdapter(stations, listener)
        rcvStations = rootView.findViewById(R.id.f_list_rcv_stations)
        rcvStations.adapter = stationAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        rcvStations.layoutManager = linearLayoutManager

        //Button
        rootView.findViewById<View>(R.id.f_list_imv_clear).setOnClickListener {
            listener?.onLeaveSearch()
        }

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun readFromGSON() {
        try {
            val data = Files.readAllBytes(Paths.get( dataPath))
            stations = Gson().fromJson(String(data), Array<Station>::class.java).toCollection(ArrayList())
        } catch (error : Exception) {
            Log.e("Error", error.toString())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(dataPath: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(DATA_PATH, dataPath)
                }
            }
    }
}