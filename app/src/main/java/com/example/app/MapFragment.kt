package com.example.app

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Fragment that displays a Google map to show stations.
 */
private const val STATIONS = "stations"

class MapFragment : Fragment() {

    private var listener: MapCallBack? = null
    private var stations: ArrayList<Station> = arrayListOf()
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var btnBack : com.google.android.material.floatingactionbutton.FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stations = it.getSerializable(STATIONS) as ArrayList<Station>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_map, container, false)

        // Link to the map fragment
        mapFragment = childFragmentManager.findFragmentById(R.id.f_map_map) as SupportMapFragment
        btnBack = rootView.findViewById(R.id.f_map_backButton)
        btnBack.setOnClickListener {
            listener?.onBackFromMaps()
        }

        initMapFragment()
        setStations()

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MapCallBack) {
            listener = context
        }
        else {
            throw java.lang.RuntimeException("$context must implement MapCallBack")
        }
    }

    override fun onDetach(){
        super.onDetach()
        listener = null
    }

    /**
     * Initialize the map fragment.
     */
    private fun initMapFragment(){
        val supportMapFragment: SupportMapFragment = SupportMapFragment.newInstance()
        supportMapFragment.getMapAsync(OnMapReadyCallback {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@OnMapReadyCallback
            }
        })
    }

    private fun setStations(){
        mapFragment.getMapAsync(OnMapReadyCallback { googleMap ->
            for (station in stations){
                val latLng = LatLng(station.lat, station.long)
                googleMap.addMarker(MarkerOptions().position(latLng).title(station.address))
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.856614,2.3522219), 12f))
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(stations: ArrayList<Station>) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(STATIONS, stations)
                }
            }
    }
}