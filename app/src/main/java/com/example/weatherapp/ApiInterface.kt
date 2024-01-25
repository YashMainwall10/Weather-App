package com.example.weatherapp


import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun getweatherdata(
        @Query("q") city : String ,
        @Query("appid") apikey : String ,
        @Query("units") units : String
    ) : retrofit2.Call<weatherData>
}