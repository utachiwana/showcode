package com.utachiwana.messenger.main

import android.util.Log
import com.utachiwana.messenger.main.pojo.CityClassItem
import com.utachiwana.messenger.main.pojo.CurrentWeather
import com.utachiwana.messenger.network.RetrofitApi
import javax.inject.Inject

class MainRepository @Inject constructor(private val api: RetrofitApi) {

    suspend fun searchCity(name: String) : List<CityClassItem>?{
        return api.searchCity(name).body()
    }

    suspend fun getWeather(city: CityClassItem) : CurrentWeather? {
        return api.getCurrentWeather(city.lat, city.lon).body()
    }

}