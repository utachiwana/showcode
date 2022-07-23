package com.utachiwana.messenger.network

import android.util.Log
import com.utachiwana.messenger.R

// codes https://openweathermap.org/weather-conditions#Weather-Condition-Codes-2

object WebConfig {
    const val URL_CITY = "https://api.openweathermap.org/"
    const val PICTURE_CODE = "https://openweathermap.org/img/w/%s.png"
    const val APPID = "ec4385c38cae3e94f9060c9f40ae934b"

    fun getPictureLinkByCode(code: String) = String.format(PICTURE_CODE, code)


}