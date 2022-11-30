package com.example.app

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.maps.android.clustering.ClusterManager
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Fragment that displays a Google map to show stations.
 */
private const val DATA_PATH = "data path"
private const val CLICKED_ID = "clickedId"

class MapFragment : Fragment() {

    private var listener: MapCallBack? = null
    private var stations: ArrayList<Station> = arrayListOf()
    private var clickedId: Int = 0
    private lateinit var dataPath: String
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var btnBack : com.google.android.material.floatingactionbutton.FloatingActionButton
    private lateinit var clusterManager: ClusterManager<MarkerClusterItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dataPath = it.getString(DATA_PATH).toString()
            clickedId = it.getInt(CLICKED_ID)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_map, container, false)

        readFromGSON() // Read data from GSON file

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
            if(clickedId == 0){ googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.856614, 2.3522219), 10f)) }
            else{
                for (station in stations){
                    if(station.id == clickedId){
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
     * @param googleMap The map.
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
                station.address + " " + station.pc + ", " + station.city,
                "${station.brand}",
                station.id,
            )
            stationItems.add(offsetItem)
        }
        return stationItems
    }

    /**
     * Read the data from the GSON file.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun readFromGSON() {
        try {
            val data = Files.readAllBytes(Paths.get(dataPath))
            stations = Gson().fromJson(String(data), Array<Station>::class.java).toCollection(ArrayList())
        } catch (error : Exception) {
            Log.e("Error", error.toString())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(dataPath: String, clickedId: Int) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putString(DATA_PATH, dataPath)
                    putInt(CLICKED_ID, clickedId)
                }
            }
    }



}