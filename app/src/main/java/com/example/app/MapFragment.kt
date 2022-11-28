package com.example.app

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager

/**
 * Fragment that displays a Google map to show stations.
 */
private const val STATIONS = "stations"
private const val MAP_MARKER = "mapMarker"

class MapFragment : Fragment() {

    private var listener: MapCallBack? = null
    private var stations: ArrayList<Station> = arrayListOf()
    private var mapMarker: Int = 0
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var btnBack : com.google.android.material.floatingactionbutton.FloatingActionButton
    private lateinit var clusterManager: ClusterManager<MarkerClusterItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stations = it.getSerializable(STATIONS) as ArrayList<Station>
            mapMarker = it.getInt(MAP_MARKER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_map, container, false)

        // Back button
        btnBack = rootView.findViewById(R.id.f_map_backButton)
        btnBack.setOnClickListener {
            listener?.onBackFromMaps()
        }

        // Link to the map fragment
        mapFragment = childFragmentManager.findFragmentById(R.id.f_map_map) as SupportMapFragment
        initMapFragment()

        // Add markers to the map
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
        //val supportMapFragment: SupportMapFragment = SupportMapFragment.newInstance()
        mapFragment.getMapAsync(OnMapReadyCallback {
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

    /**
     * Set the stations on the map.
     */
    private fun setStations(){
        mapFragment.getMapAsync(OnMapReadyCallback { googleMap ->
            // Zoom
            googleMap.uiSettings.isZoomControlsEnabled = true
            // Camera
            if(mapMarker == 0){ googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.856614, 2.3522219), 12f)) }
            else{
                for (station in stations){
                    if(station.id == mapMarker){
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(station.lat, station.long), 16f))
                    }
                }
            }
            // Clusters
            setCluster(googleMap)
        })
    }

    /**
     * Set the clusters on the map.
     */
    private fun setCluster(googleMap: GoogleMap){
        clusterManager = ClusterManager(requireContext(), googleMap)
        googleMap.setOnCameraIdleListener(clusterManager)
        val stationItems = getItems()
        clusterManager.setOnClusterItemInfoWindowClickListener {
            it.getId()?.let { it1 -> listener?.onStationSelected(it1) }
        }
        clusterManager.addItems(stationItems)
        clusterManager.cluster()
    }

    /**
     * Add clustered items to the map.
     */
    private fun getItems() : List<MarkerClusterItem> {
        val stationItems = arrayListOf<MarkerClusterItem>()
        for (station in stations) {
            val offsetItem = MarkerClusterItem(
                station.lat,
                station.long,
                station.address,
                "${station.pc}",
                station.id,
            )
            stationItems.add(offsetItem)
        }
        return stationItems
    }

    companion object {
        @JvmStatic
        fun newInstance(stations: ArrayList<Station>, mapMarker: Int) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(STATIONS, stations)
                    putInt(MAP_MARKER, mapMarker)
                }
            }
    }



}