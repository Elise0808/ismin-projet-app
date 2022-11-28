package com.example.app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val SERVER_BASE_URL = "https://station-npev.cleverapps.io"

class MainActivity : AppCompatActivity(), MapCallBack, ListCallBack{

    private var stationList = StationList()
    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager2 : ViewPager2
    private lateinit var myViewPagerAdapter : MyViewPagerAdapter

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)
        .build()

    private val stationService: StationService = retrofit.create(StationService::class.java)

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val station = result.data?.getSerializableExtra("setFavorite") as Station
            val position = result.data?.getIntExtra("position", 1)
            val mapMarker = result.data?.getIntExtra("map", 0)
            onFavorite(station.id, station.fav)
            displayFragments(stationList, position!!, mapMarker!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                setData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Prevent from swiping while using MapsFragment
     */
    override fun onBackFromMaps(){
        viewPager2.isUserInputEnabled = true
        tabLayout.selectTab(tabLayout.getTabAt(1))
    }

    /**
     * Show details about one station
     */
    override fun onStationSelected(id: Int) {
        onDetails(id, 2)
    }

    /**
     * Set favorite for station
     * @param id Int
     * @param favorite Boolean
     */
    override fun onFavorite(id: Int, fav: Boolean){
        var favRequest = FavRequest(fav)

        stationList.setFavorite(id, fav)

        stationService.setFavoriteStation(id.toString(), favRequest)
            .enqueue(object : Callback<Station> {
            override fun onResponse(call: Call<Station>, response: Response<Station>) {
            }
            override fun onFailure(call: Call<Station>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    /**
     * Open a new activity with the station's details
     * @param id Int
     */
    override fun onDetails(id: Int, position : Int){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("station", stationList.getStation(id))
        intent.putExtra("position", position)
        startForResult.launch(intent)

    }

    override fun onSearch(search: String){
        val searchTerm = Search(search)
        stationService.searchStation(searchTerm).enqueue(object : Callback<List<Station>> {
            override fun onResponse(call: Call<List<Station>>, response: Response<List<Station>>) {
                val allStations : List<Station>? = response.body()
                val stationListSearch = StationList()
                for(station in allStations!!) {
                    stationListSearch.addStation(station)
                }
                displayFragments(stationListSearch)
            }
            override fun onFailure(call: Call<List<Station>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    /**
     * Get data from API
     */
    private fun setData(){
        stationService.getAllStations()
            .enqueue(object : Callback<List<Station>> {
                override fun onResponse(call: Call<List<Station>>, response: Response<List<Station>>) {
                    val allStations : List<Station>? = response.body()
                    for(station in allStations!!) {
                        stationList.addStation(station)
                    }
                    displayFragments(stationList)
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
    fun displayFragments(stationListShow : StationList, position : Int = 1, map : Int = 0){
        // Swipe between fragments
        tabLayout = findViewById(R.id.tab_layout)
        viewPager2 = findViewById(R.id.view_pager2)
        myViewPagerAdapter = MyViewPagerAdapter(this, stationListShow.getAllStations(), map)
        viewPager2.adapter = myViewPagerAdapter

        viewPager2.currentItem = position
        tabLayout.selectTab(tabLayout.getTabAt(position))

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
