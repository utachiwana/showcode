package com.utachiwana.messenger.network

import com.utachiwana.messenger.main.pojo.CityClassItem
import com.utachiwana.messenger.main.pojo.CurrentWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("/geo/1.0/direct?")
    suspend fun searchCity(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = 10
    ): Response<List<CityClassItem>>

    @GET("/data/2.5/weather?")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") metric: String = "metric",
        @Query("lang") lang: String = "RU"
    ): Response<CurrentWeather>
}