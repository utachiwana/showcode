package com.utachiwana.messenger.network

import com.utachiwana.messenger.R

// codes https://openweathermap.org/weather-conditions#Weather-Condition-Codes-2

object WebConfig {
    const val URL_CITY = "https://api.openweathermap.org/"
    const val APPID = "ec4385c38cae3e94f9060c9f40ae934b"


    fun getPictureByWeatherCode(code : Int) : Int {
        return when (code){
            in 200..232 -> R.drawable.ic_thunderstorm
            in 801..804 -> R.drawable.ic_cloudy
            in 600..622 -> R.drawable.ic_snowy
            in 500..531 -> R.drawable.ic_rainy
            in 300..321 -> R.drawable.ic_rainy
            else -> R.drawable.ic_sunny
        }
    }

}