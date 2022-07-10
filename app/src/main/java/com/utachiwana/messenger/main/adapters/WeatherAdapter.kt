package com.utachiwana.messenger.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utachiwana.messenger.R
import com.utachiwana.messenger.main.pojo.CurrentWeather
import kotlin.collections.ArrayList

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private val history = ArrayList<CurrentWeather>(10)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_current_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(history[position])
    }

    override fun getItemCount(): Int {
        return history.size
    }

    fun add(weather: CurrentWeather?) {
        if (weather != null)
            history.add(0,weather)
        notifyItemInserted(history.size - 1)
    }

    class WeatherViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val picture: ImageView = itemView.findViewById(R.id.weather_picture)
        private val cityName: TextView = itemView.findViewById(R.id.city_name)
        private val temperature: TextView = itemView.findViewById(R.id.current_temperature)
        private val description: TextView = itemView.findViewById(R.id.weather_desc)
        private val windSpeed: TextView = itemView.findViewById(R.id.wind_speed)
        private val date: TextView = itemView.findViewById(R.id.weather_date)

        fun bind(currentWeather: CurrentWeather) {
            picture.setImageResource(currentWeather.getPicture())
            cityName.text = currentWeather.name
            temperature.text = "${currentWeather.getTemperature()} °C"
            description.text = currentWeather.getWeatherDesc()
            windSpeed.text = "${currentWeather.getWindSpeed()} m/s"
            date.text = currentWeather.getDate()
        }

    }
}