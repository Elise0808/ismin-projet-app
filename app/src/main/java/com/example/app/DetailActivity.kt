package com.example.app

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.app.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var station : Station

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        station = intent.getSerializableExtra("station") as Station
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
                val intent = Intent()
                intent.putExtra("setFavorite", station)
                setResult(RESULT_OK, intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setDetails(station : Station){
        setFavorite(findViewById<ImageButton>(R.id.a_detail_imv_fav), station)
        findViewById<TextView>(R.id.a_detail_txv_address).text = station.address + " " + station.pc + ", " + station.city

        if ("SP95" in station.price_name){
            findViewById<TextView>(R.id.a_detail_txv_SP95_price).text = station.price_val[station.price_name.indexOf("SP95")].toString() + "€"
            findViewById<TextView>(R.id.a_detail_txv_SP95_update).text = station.price_update[station.price_name.indexOf("SP95")]
        }
        else{
            findViewById<TextView>(R.id.a_detail_txv_SP95_price).text = "Non disponible / renseigné"
            findViewById<TextView>(R.id.a_detail_txv_SP95_update).text = "Non disponible / renseigné"
        }
        if ("SP98" in station.price_name){
            findViewById<TextView>(R.id.a_detail_txv_SP98_price).text = station.price_val[station.price_name.indexOf("SP98")].toString() + "€"
            findViewById<TextView>(R.id.a_detail_txv_SP98_update).text = station.price_update[station.price_name.indexOf("SP98")]
        }
        else{
            findViewById<TextView>(R.id.a_detail_txv_SP98_price).text = "Non disponible / renseigné"
            findViewById<TextView>(R.id.a_detail_txv_SP98_update).text = "Non disponible / renseigné"
        }
        if ("E10" in station.price_name){
            findViewById<TextView>(R.id.a_detail_txv_E10_price).text = station.price_val[station.price_name.indexOf("E10")].toString() + "€"
            findViewById<TextView>(R.id.a_detail_txv_E10_update).text = station.price_update[station.price_name.indexOf("E10")]
        }
        else{
            findViewById<TextView>(R.id.a_detail_txv_E10_price).text = "Non disponible / renseigné"
        }
        if ("E85" in station.price_name){
            findViewById<TextView>(R.id.a_detail_txv_E85_price).text = station.price_val[station.price_name.indexOf("E85")].toString() + "€"
            findViewById<TextView>(R.id.a_detail_txv_E85_update).text = station.price_update[station.price_name.indexOf("E85")]
        }
        else{
            findViewById<TextView>(R.id.a_detail_txv_E85_price).text = "Non disponible / renseigné"
            findViewById<TextView>(R.id.a_detail_txv_E85_update).text = "Non disponible / renseigné"
        }
        if("Gazole" in station.price_name){
            findViewById<TextView>(R.id.a_detail_txv_Gazole_price).text = station.price_val[station.price_name.indexOf("Gazole")].toString() + "€"
            findViewById<TextView>(R.id.a_detail_txv_Gazole_update).text = station.price_update[station.price_name.indexOf("Gazole")]
        }
        else{
            findViewById<TextView>(R.id.a_detail_txv_Gazole_price).text = "Non disponible / renseigné"
            findViewById<TextView>(R.id.a_detail_txv_Gazole_update).text = "Non disponible / renseigné"
        }
        if("GPLc" in station.price_name){
            findViewById<TextView>(R.id.a_detail_txv_GPLc_price).text = station.price_val[station.price_name.indexOf("GPLc")].toString() + "€"
            findViewById<TextView>(R.id.a_detail_txv_GPLc_update).text = station.price_update[station.price_name.indexOf("GPLc")]
        }
        else{
            findViewById<TextView>(R.id.a_detail_txv_GPLc_price).text = "Non disponible / renseigné"
            findViewById<TextView>(R.id.a_detail_txv_GPLc_update).text = "Non disponible / renseigné"
        }
        var serviceTxv : String = ""
        if(station.service != null) {
            for (service in station.service) {
                serviceTxv += service + "\n"
            }
        }
        else{
            serviceTxv = "Non disponible / renseigné"
        }
        findViewById<TextView>(R.id.a_detail_txv_services_data).text = serviceTxv
    }

    private fun setFavorite(imvFav: ImageButton,station: Station) {
        if(station.fav) {
            imvFav.setImageResource(R.drawable.fav_on)
        }
        else {
            imvFav.setImageResource(R.drawable.fav_off)
        }
        imvFav.setOnClickListener {
            station.fav = !station.fav
            setFavorite(imvFav, station)
        }
    }
}