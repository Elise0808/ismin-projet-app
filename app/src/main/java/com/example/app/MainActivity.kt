package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
//import com.example.app.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), MapCallBack{

    private var stationList = StationList()
    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager2 : ViewPager2
    private lateinit var myViewPagerAdapter : MyViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize stations
        val station = Station( 1,"12 rue Clémenceau","Aix en Provence", "21 Octobre 2022 10:31 AM","E10", 1.67,"Toilettes","Oui",13100,-1.000,43.000, false)
        stationList.addStation(station)
        val anotherStation = Station(2,"2 rue du Val de Grace","Paris", "14 Octobre 2022 10:31 AM", "Gazole",0.67, "Nissou", "Non",75005,15.980,33.675, false)
        stationList.addStation(anotherStation)

        // Swipe between fragments
        tabLayout = findViewById(R.id.tab_layout)
        viewPager2 = findViewById(R.id.view_pager2)
        myViewPagerAdapter = MyViewPagerAdapter(this, stationList.getAllStations())
        viewPager2.adapter = myViewPagerAdapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager2.isUserInputEnabled = tab!!.position != 2
                viewPager2.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewPager2.isUserInputEnabled = position != 2
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }

    /**
     * Prevent from swiping while using MapsFragment
     */
    override fun onBackFromMaps(){
        viewPager2.isUserInputEnabled = true
        tabLayout.selectTab(tabLayout.getTabAt(1))
    }
}