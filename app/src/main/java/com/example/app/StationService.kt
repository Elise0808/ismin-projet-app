package com.example.app

import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import retrofit2.Call
import retrofit2.http.*

interface StationService {

    @GET("stations")
    fun getAllStations(): Call<List<Station>>

    @GET("stations/{id}")
    fun getStationInfo(@Path("id") id: String): Call<Station>

    @PUT("stations/{id}")
    fun setFavoriteStation(@Path("id") id:String, @Body fav: Boolean ) : Call<Station>
}