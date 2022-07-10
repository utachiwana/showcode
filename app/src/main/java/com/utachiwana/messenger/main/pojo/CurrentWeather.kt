package com.utachiwana.messenger.main.pojo

import com.utachiwana.messenger.network.WebConfig
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

data class CurrentWeather(
    val dt: Int,
    val main: Main,
    val name: String,
    val weather: List<Weather>,
    val wind: Wind
) {
    fun getTemperature() = main.temp.toInt()
    fun getWeatherDesc() = if (weather.isNotEmpty()) weather[0].description else ""
    private fun getWeatherCode() = if (weather.isNotEmpty()) weather[0].id else 800

    fun getWindSpeed() = wind.speed.roundToInt()

    fun getDate(): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = dt * 1000.toLong()
        return SimpleDateFormat("hh:mm dd/MM/yyyy").format(cal.time)
    }

    fun getPicture() = WebConfig.getPictureByWeatherCode(getWeatherCode())
}