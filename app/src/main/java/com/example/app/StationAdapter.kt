package com.example.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class StationAdapter(private var stations: List<Station>) : RecyclerView.Adapter<StationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(
            R.layout.row_station, parent,
            false
        )
        return StationViewHolder(row)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val station = stations[position];
        holder.txvName.text = station.price_name
        holder.txvCityPC.text = station.city + ", " + station.pc
        holder.txvPrice.text = station.price_val.toString()
    }

    override fun getItemCount(): Int {
        return stations.size
    }

    fun refreshData(allStations: List<Station>) {
        this.stations = allStations;
    }
}