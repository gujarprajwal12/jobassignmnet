package com.jobassignmentproject.NetworkLayer


import com.jobassignmentproject.DataLayer.OpenWeather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {



    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
    ): WeatherResponse

}