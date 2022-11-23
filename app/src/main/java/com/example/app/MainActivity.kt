package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val SERVER_BASE_URL = "https://station-npev.cleverapps.io"

class MainActivity : AppCompatActivity(), MapCallBack{

    private var stationList = StationList()
    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager2 : ViewPager2
    private lateinit var myViewPagerAdapter : MyViewPagerAdapter

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)
        .build()

    val stationService = retrofit.create(StationService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setData()
    }

    /**
     * Prevent from swiping while using MapsFragment
     */
    override fun onBackFromMaps(){
        viewPager2.isUserInputEnabled = true
        tabLayout.selectTab(tabLayout.getTabAt(1))
    }

    /**
     * Get data from API
     */
    fun setData(){
        stationService.getAllStations()
            .enqueue(object : Callback<List<Station>> {
                override fun onResponse(call: Call<List<Station>>, response: Response<List<Station>>) {
                    val allStations : List<Station>? = response.body()
                    for(station in allStations!!) {
                        stationList.addStation(station)
                    }
                    Log.d("StationList", stationList.getAllStations().toString())
                    displayFragments()
                }
                override fun onFailure(call: Call<List<Station>>, t: Throwable) {
                    t.printStackTrace()
                    Log.d("MainActivity", "onFailure: $t")
                }
            })
    }

    /**
     * Display the ViewPager2
     */
    fun displayFragments() {
        // Swipe between fragments
        tabLayout = findViewById(R.id.tab_layout)
        viewPager2 = findViewById(R.id.view_pager2)
        Log.d("MainActivity", "onCreate: ${stationList.getAllStations()}")
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
}
