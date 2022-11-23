package com.example.app

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
interface StationService {

    @GET("stations")
    fun getAllStations(): Call<List<Station>>
}