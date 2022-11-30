package com.example.app

import com.google.gson.annotations.SerializedName
/**
 * Station
 */
data class Station (
    val id: Int,
    val address: String,
    val city: String,
    val pc: Int,
    val brand : String,
    val update: String,
    val shortage: Array<String>,
    val price_name: Array<String>,
    var price_val: Array<Double>,
    val service: Array<String>,
    val automate24_24: String,
    val lat: Double,
    val long: Double,
    @SerializedName("fav")
    var fav: Boolean) : java.io.Serializable