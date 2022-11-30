package com.example.app

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

/**
 * class for marker cluster items
 * @param position position of the marker
 * @param title title of the marker
 * @param snippet snippet of the marker
 * @param id id of the station
 */

class MarkerClusterItem(
    lat: Double,
    lng: Double,
    title: String,
    snippet: String,
    id: Int
    ) : ClusterItem {
        private val position: LatLng
        private val title: String
        private val snippet: String
        private val id: Int

        override fun getPosition(): LatLng {
            return position
        }

        override fun getTitle(): String? {
            return title
        }

        override fun getSnippet(): String? {
            return snippet
        }

        fun getId(): Int? {
        return id
        }

        init {
            position = LatLng(lat, lng)
            this.title = title
            this.snippet = snippet
            this.id = id
        }
    }
