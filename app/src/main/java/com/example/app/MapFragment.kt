package com.example.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
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

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment() {

    private lateinit var mapFragment: SupportMapFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Initialize view
        var view: View = inflater.inflate(R.layout.fragment_map, container, false)

        //Initialize map fragment
        val supportMapFragment: SupportMapFragment = SupportMapFragment.newInstance()
        mapFragment = childFragmentManager.findFragmentById(R.id.f_map_map) as SupportMapFragment
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
        return view
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