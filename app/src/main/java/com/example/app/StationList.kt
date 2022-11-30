package com.example.app

import com.google.android.gms.maps.model.LatLng
import java.lang.RuntimeException

/**
 * A class that represents a list of stations.
 */
class StationList {
    private val stations: HashMap<Int, Station> = HashMap();

    fun addStation(station: Station) {
        stations[station.id] = station;
    }

    fun getStation(id: Int): Station {
        return stations[id] ?: throw RuntimeException("No book with id: $id");
    }

    fun getAllStations(): ArrayList<Station> {
        return ArrayList(stations.values.sortedBy { it.id })
    }

    fun getCoordinates() : ArrayList<LatLng> {
        var coordinates = arrayListOf<LatLng>()
        for (station in stations.values) {
            coordinates.add(LatLng(station.lat, station.long))
        }
        return coordinates
    }

    fun getTotalNumberOfStations(): Int {
        return stations.size;
    }

    fun clean() {
        stations.clear();
    }

    fun setFavorite(id: Int, fav: Boolean) {
        stations[id]?.fav = fav
    }
}