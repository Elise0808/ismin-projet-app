package com.example.app

import com.google.gson.annotations.SerializedName

data class Station (
    val id: Int,
    val address: String,
    val city: String,
    val price_update: Array<String>,
    val price_name: Array<String>,
    val price_val: Array<Double>,
    val service: Array<String>,
    val automate24: String,
    val pc: Int,
    val lat: Double,
    val long: Double,
    @SerializedName("fav")
    var fav: Boolean) : java.io.Serializable