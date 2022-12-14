package com.example.app

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * View holder for the list of stations displayed in a recyclerView.
 */
class StationViewHolder (rootView: View) : RecyclerView.ViewHolder(rootView) {
    var txvName : TextView = rootView.findViewById(R.id.r_station_txv_name)
    var txvCityPC : TextView = rootView.findViewById(R.id.r_station_txv_citypc)
    var txvPrice : TextView = rootView.findViewById(R.id.r_station_txv_price)
    var imvFav : ImageButton = rootView.findViewById(R.id.r_station_imv_fav)
    var imvDetails : ImageButton = rootView.findViewById(R.id.r_station_imv_details)
    var imvPicture : ImageView = rootView.findViewById(R.id.r_station_imv_picture)
}