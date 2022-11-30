package com.example.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates

/**
 * DetailActivity
 * To display the detail of the selected station
 */
class DetailActivity : AppCompatActivity() {
    private lateinit var station : Station
    private var position by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Get information from MainActivity
        station = intent.getSerializableExtra("station") as Station
        position = intent.getIntExtra("position", 1)

        // Set information to display
        setDetails(station)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_quit -> {
                quit(1)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDetails(station : Station){
        setFavorite(findViewById(R.id.a_detail_imv_fav)) // Set favorite button
        setMap(findViewById(R.id.a_detail_imv_loc)) // Set map button
        findViewById<TextView>(R.id.a_detail_txv_update).text = station.update // Set update time
        findViewById<TextView>(R.id.a_detail_txv_address).text = station.address + " " + station.pc + ", " + station.city // Set station's address
        setGas()
        setService()
    }

    /**
     * Set the favorite button
     * @param imvFav : ImageButton
     */
    private fun setFavorite(imvFav: ImageButton) {
        if(station.fav) {
            imvFav.setImageResource(R.drawable.fav_on_32)
        }
        else {
            imvFav.setImageResource(R.drawable.fav_off_32)
        }
        imvFav.setOnClickListener {
            station.fav = !station.fav
            setFavorite(imvFav)
        }
    }

    /**
     * Set the map button
     * @param button : ImageButton
     */
    private fun setMap(imvMap: ImageButton) {
        imvMap.setImageResource(R.drawable.location_32)
        imvMap.setOnClickListener {
            quit(2)
        }
    }

    /**
     * Set the gas' information
     */
    @SuppressLint("SetTextI18n")
    private fun setGas() {
        if ("SP95" in station.price_name){
            findViewById<TextView>(R.id.a_detail_txv_SP95_price).text = station.price_val[0].toString() + "€"
        }
        else if ("SP95" in station.shortage){
            findViewById<TextView>(R.id.a_detail_txv_SP95_price).text = "Rupture"
        }
        else{
            findViewById<TextView>(R.id.a_detail_txv_SP95_price).text = "Non disponible"
        }
        if("SP98" in station.price_name){
            findViewById<TextView>(R.id.a_detail_txv_SP98_price).text = station.price_val[1].toString() + "€"
        }
        else if ("SP98" in station.shortage){
            findViewById<TextView>(R.id.a_detail_txv_SP98_price).text = "Rupture"
        }
        else{
            findViewById<TextView>(R.id.a_detail_txv_SP98_price).text = "Non disponible"
        }
        if("Gazole" in station.price_name){
            findViewById<TextView>(R.id.a_detail_txv_Gazole_price).text = station.price_val[2].toString() + "€"
        }
        else if ("Gazole" in station.shortage){
            findViewById<TextView>(R.id.a_detail_txv_Gazole_price).text = "Rupture"
        }
        else{
            findViewById<TextView>(R.id.a_detail_txv_Gazole_price).text = "Non disponible"
        }
        if("E10" in station.price_name){
            findViewById<TextView>(R.id.a_detail_txv_E10_price).text = station.price_val[3].toString() + "€"
        }
        else if ("E10" in station.shortage){
            findViewById<TextView>(R.id.a_detail_txv_E10_price).text = "Rupture"
        }
        else{
            findViewById<TextView>(R.id.a_detail_txv_E10_price).text = "Non disponible"
        }
        if ("E85" in station.price_name){
            findViewById<TextView>(R.id.a_detail_txv_E85_price).text = station.price_val[4].toString() + "€"
        }
        else if ("E85" in station.shortage){
            findViewById<TextView>(R.id.a_detail_txv_E85_price).text = "Rupture"
        }
        else{
            findViewById<TextView>(R.id.a_detail_txv_E85_price).text = "Non disponible"
        }
        if ("GPLc" in station.price_name){
            findViewById<TextView>(R.id.a_detail_txv_GPLc_price).text = station.price_val[5].toString() + "€"
        }
        else if ("GPLc" in station.shortage){
            findViewById<TextView>(R.id.a_detail_txv_GPLc_price).text = "Rupture"
        }
        else{
            findViewById<TextView>(R.id.a_detail_txv_GPLc_price).text = "Non disponible"
        }
    }

    /**
     * Set the information about service
     */
    private fun setService(){
        var serviceTxv = ""
        for (service in station.service){
            serviceTxv += service + "\n"
        }
        findViewById<TextView>(R.id.a_detail_txv_services_data).text = serviceTxv
    }

    /**
     * Quit the activity - 1rst mode : go to the list, 2nd mode : go to the map
     * @param code : Int
     */
    private fun quit(position: Int){
        val intent = Intent()
        intent.putExtra("setFavorite", station.fav) // Send the favorite state
        intent.putExtra("position", position) // 1rst mode : go to the list, 2nd mode : go to the map
        intent.putExtra("clickedId", station.id) // Send the id of the station
        setResult(RESULT_OK, intent)
        finish()
    }
}