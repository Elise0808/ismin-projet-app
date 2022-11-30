package com.example.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


const val SERVER_BASE_URL = "https://station-npev.cleverapps.io"

class MainActivity : AppCompatActivity(), MapCallBack, ListCallBack{

    private var stationList = StationList()
    private lateinit var dataPath: String
    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager2 : ViewPager2
    private lateinit var myViewPagerAdapter : MyViewPagerAdapter

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)
        .build()

    private val stationService: StationService = retrofit.create(StationService::class.java)

    // Exchange information between DetailActivity and MainActivity
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val fav = result.data?.getBooleanExtra("setFavorite", false)
            val position = result.data?.getIntExtra("position", 1)
            val clickedId = result.data?.getIntExtra("clickedId", 0)
            onFavorite(clickedId!!, fav!!)
            displayFragments(position!!, clickedId!!)
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
     * @param id : selected station's id
     */
    override fun onStationSelected(id: Int) {
        onDetails(id, 2)
    }

    /**
     * Set favorite for station on the API
     * @param id Int
     * @param fav Boolean
     */
    override fun onFavorite(id: Int, fav: Boolean){
        var favRequest = FavRequest(fav)

        stationList.setFavorite(id, fav)

        stationService.setFavoriteStation(id.toString(), favRequest)
            .enqueue(object : Callback<Station> {
            override fun onResponse(call: Call<Station>, response: Response<Station>) {
                setData()
            }
            override fun onFailure(call: Call<Station>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    /**
     * Open a new activity with the station's details
     * @param id Int
     * @param position Int : to know if details are displayed in ListFragment(=1) or MapsFragment(=2)
     */
    override fun onDetails(id: Int, position : Int){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("station", stationList.getStation(id))
        intent.putExtra("position", position)
        startForResult.launch(intent)
    }

    /**
     * Display stations containing what user typed in SearchView
     * @param search String : user's input
     */
    override fun onSearch(search: String){
        val searchTerm = Search(search)
        stationService.searchStation(searchTerm).enqueue(object : Callback<List<Station>> {
            override fun onResponse(call: Call<List<Station>>, response: Response<List<Station>>) {
                val allStations : List<Station>? = response.body()
                val stationListSearch = StationList()
                for(station in allStations!!) {
                    stationListSearch.addStation(station)
                }
                writeJSON(stationListSearch)
                displayFragments()
            }
            override fun onFailure(call: Call<List<Station>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    /**
     * Display all stations after leaving search
     */
    override fun onLeaveSearch(){
        setData()
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
                    writeJSON(stationList)
                    displayFragments()
                }
                override fun onFailure(call: Call<List<Station>>, t: Throwable) {
                    t.printStackTrace()
                    Log.d("MainActivity", "onFailure: $t")
                }
            })
    }

    /**
     * Display the ViewPager2 to swipe
     * @param position Int : to disable swipe from MapsFragment
     * @param clickedId Int : to zoom on the selected station if MapsFragment is displayed
     */
    fun displayFragments(position : Int = 1, clickedId : Int = 0){
        // Swipe between fragments
        tabLayout = findViewById(R.id.tab_layout)
        viewPager2 = findViewById(R.id.view_pager2)
        myViewPagerAdapter = MyViewPagerAdapter(this, dataPath, clickedId)
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

    /**
     * Write data in JSON file to be able to read it later
     */
    fun writeJSON(stations: StationList) {
        val file = File(filesDir, "stations.json")
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()

        val jsonTutsListPretty: String = gsonPretty.toJson(stations.getAllStations())
        file.writeText(jsonTutsListPretty)
        dataPath = file.toString()
    }

}
