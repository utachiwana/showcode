package com.utachiwana.messenger.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.utachiwana.messenger.appComponent
import com.utachiwana.messenger.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory
    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    private val weatherAdapter = WeatherAdapter()
    private lateinit var view: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)

        setupCitySearch()
        setupWeather()
        viewModel.loading.observe(this) { isLoading ->
            view.progressBar.isVisible = isLoading ?: false
        }

    }

    private fun setupWeather() {
        view.weatherRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        view.weatherRecyclerView.adapter = weatherAdapter
        viewModel.weather.observe(this) { weather ->
            weatherAdapter.add(weather)
        }
    }

    private fun setupCitySearch() {
        val adapter = CityAdapter()
        view.citySearch.setAdapter(adapter)
        viewModel.cities.observe(this) { cities ->
            adapter.clear()
            adapter.addAll(cities)
            adapter.notifyDataSetChanged()
        }
        view.citySearch.setOnItemClickListener { adapterView, view, pos, rowid ->
            viewModel.getCityWeather(adapter.getCity(pos))
        }

        view.citySearch.addTextChangedListener { editable ->
            val text = editable?.toString()
            if (text != null)
                viewModel.searchCity(text)
        }
    }

}