package com.utachiwana.messenger.ui

import com.utachiwana.messenger.data.local.LocalDatabase
import com.utachiwana.messenger.data.pojo.CityClassItem
import com.utachiwana.messenger.data.pojo.CurrentWeather
import com.utachiwana.messenger.data.network.RetrofitApi
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val retrofit: RetrofitApi,
    private val db: LocalDatabase
) {

    suspend fun searchCity(name: String): List<CityClassItem>? {
        return retrofit.searchCity(name).body()
    }

    suspend fun getWeather(city: CityClassItem): CurrentWeather? {
        val weather = retrofit.getCurrentWeather(city.lat, city.lon).body()
        saveWeather(weather)
        return weather
    }

    private suspend fun saveWeather(weather: CurrentWeather?) {
        if (weather != null) {
            db.getDao().saveWeather(weather)
        }
    }

    suspend fun getWeatherHistory() = db.getDao().getWeatherHistory()
}