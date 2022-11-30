package com.example.app

/**
 * Receive callbacks from the mapFragment
 */
interface MapCallBack {
    fun onBackFromMaps();
    fun onStationSelected(id: Int);
}