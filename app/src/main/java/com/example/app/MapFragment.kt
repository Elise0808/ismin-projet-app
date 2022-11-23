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
class MapFragment : Fragment() {

    private var listener: MapCallBack? = null
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var btnBack : com.google.android.material.floatingactionbutton.FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Initialize view
        var rootView: View = inflater.inflate(R.layout.fragment_map, container, false)

        mapFragment = childFragmentManager.findFragmentById(R.id.f_map_map) as SupportMapFragment
        btnBack = rootView.findViewById(R.id.f_map_backButton)

        //Initialize map fragment
        val supportMapFragment: SupportMapFragment = SupportMapFragment.newInstance()
        supportMapFragment.getMapAsync(OnMapReadyCallback {
            //Check permission
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                //When permission granted
                //Enable location
                it.isMyLocationEnabled = true
                //Zoom to current location
                it.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.8566, 2.3522), 10f))
            } else {
                //When permission denied
                //Request permission
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    44
                )
            }
        })

        //button go back
        btnBack.setOnClickListener{
            listener?.onBackFromMaps()
        }

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

    companion object {
        @JvmStatic
        fun newInstance() =
            MapFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}