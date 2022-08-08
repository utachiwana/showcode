package com.utachiwana.messenger.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.utachiwana.messenger.data.pojo.Weather

class RoomConvertor {

    private val gson = Gson()

    @TypeConverter
    fun fromWeatherList(list: List<Weather>): String = gson.toJson(list)

    @TypeConverter
    fun toWeatherList(list: String): List<Weather> =
        gson.fromJson(list, object : TypeToken<List<Weather>>() {}.type)
}