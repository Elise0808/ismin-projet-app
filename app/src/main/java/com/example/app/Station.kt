package com.example.app

data class Station (
    val id: Int,
    val address: String,
    val city: String,
    val price_update: String,
    val price_name: String,
    val price_val: Double,
    val service: String,
    val automate24: String,
    val pc: Int,
    val lat: Double,
    val long: Double,
    val fav: Boolean) : java.io.Serializable