package com.utachiwana.messenger.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.utachiwana.messenger.data.pojo.CurrentWeather

@Dao
interface WeatherDao {

    @Insert
    suspend fun saveWeather(weather: CurrentWeather)

    @Query("select * from CurrentWeather order by uid desc limit 20")
    suspend fun getWeatherHistory() : List<CurrentWeather>

}