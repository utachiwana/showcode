package com.utachiwana.messenger.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.utachiwana.messenger.main.pojo.CurrentWeather

@Database(entities = [CurrentWeather::class], version = 1)
@TypeConverters(RoomConvertor::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getDao(): WeatherDao
}