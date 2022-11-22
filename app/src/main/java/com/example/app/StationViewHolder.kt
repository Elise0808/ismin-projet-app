package com.example.app

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StationViewHolder (rootView: View) : RecyclerView.ViewHolder(rootView) {
    var txvName : TextView = rootView.findViewById(R.id.r_station_txv_name)
    var txvCityPC : TextView = rootView.findViewById(R.id.r_station_txv_citypc)
    var txvPrice : TextView = rootView.findViewById(R.id.r_station_txv_price)
}