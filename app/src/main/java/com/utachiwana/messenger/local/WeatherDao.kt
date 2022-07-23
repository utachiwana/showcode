package com.utachiwana.messenger.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.utachiwana.messenger.main.pojo.CurrentWeather
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert
    suspend fun saveWeather(weather: CurrentWeather)

    @Query("select * from CurrentWeather limit 20")
    suspend fun getWeatherHistory() : List<CurrentWeather>

}