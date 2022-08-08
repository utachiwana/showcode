package com.utachiwana.messenger.data.pojo

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.utachiwana.messenger.R
import com.utachiwana.messenger.data.network.WebConfig
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Entity
data class CurrentWeather(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    val uid: Int,
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

    @Composable
    fun getInlineTemperature(size: TextUnit, mod: Modifier) {
        val temp = buildAnnotatedString {
            appendInlineContent(id = "tempId")
            append("${getTemperature()} °C")
        }
        val content = mapOf("tempId" to InlineTextContent(
            Placeholder(
                size,size,
                PlaceholderVerticalAlign.TextCenter
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_temperature),
                contentDescription = null
            )
        })
        Text(text = temp, inlineContent = content, style = TextStyle(fontSize = size), modifier = mod)
    }

    @Composable
    fun getInlineWindSpeed(size: TextUnit, mod: Modifier) {
        val speed = buildAnnotatedString {
            appendInlineContent(id = "windId")
            append("${getWindSpeed()} m/s")
        }
        val content = mapOf("windId" to InlineTextContent(
            Placeholder(
                size,size,
                PlaceholderVerticalAlign.TextCenter
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_wind),
                contentDescription = null
            )
        })
        Text(text = speed, inlineContent = content, style = TextStyle(fontSize = size), modifier = mod)
    }

}