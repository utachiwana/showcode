package com.utachiwana.messenger.main.pojo

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.utachiwana.messenger.network.WebConfig
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Entity
data class CurrentWeather(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "date")
    val dt: Int,
    @Embedded
    val main: Main,
    @ColumnInfo(name = "name")
    val name: String,
    val weather: List<Weather>,
    @Embedded
    val wind: Wind
) {
    fun getTemperature() = main.temp.toInt()
    fun getWeatherDesc() = if (weather.isNotEmpty()) weather[0].description else ""
    private fun getWeatherCode() = if (weather.isNotEmpty()) weather[0].icon else ""

    fun getWindSpeed() = wind.speed.roundToInt()

    fun getDate(): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = dt * 1000.toLong()
        return SimpleDateFormat("HH:mm dd/MM/yyyy").format(cal.time)
    }

    fun getPictureLink() = WebConfig.getPictureLinkByCode(getWeatherCode())
}