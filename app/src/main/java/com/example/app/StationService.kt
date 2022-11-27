package com.example.app

import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import retrofit2.Call
import retrofit2.http.*

interface StationService {

    @GET("stations")
    fun getAllStations(): Call<List<Station>>

    @POST("stations/search")
    fun searchStation(@Body elem : Search): Call<List<Station>>

    @Headers("Content-Type: application/json", "Content-Length: <calculated when request is send>")
    @PUT("stations/{id}")
    fun setFavoriteStation(@Path("id") id:String, @Body elem : FavRequest): Call<Station>
}
