package com.utachiwana.messenger.data.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Main(
    @ColumnInfo(name = "feels")
    val feels_like: Double,
    @ColumnInfo(name = "humidity")
    val humidity: Int,
    @ColumnInfo(name = "pressure")
    val pressure: Int,
    @ColumnInfo(name = "temp")
    val temp: Double,
    @ColumnInfo(name = "temp_max")
    val temp_max: Double,
    @ColumnInfo(name = "temp_min")
    val temp_min: Double
)