package com.utachiwana.messenger.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.utachiwana.messenger.R
import com.utachiwana.messenger.appComponent
import com.utachiwana.messenger.databinding.ActivityMainBinding
import com.utachiwana.messenger.local.AppSharedPreferences
import com.utachiwana.messenger.local.LocalDatabase
import com.utachiwana.messenger.main.adapters.CityAdapter
import com.utachiwana.messenger.main.adapters.WeatherAdapter
import com.utachiwana.messenger.start.StartActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory
    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var authPref: AppSharedPreferences
    private val weatherAdapter = WeatherAdapter()
    private lateinit var view: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)
        setSupportActionBar(view.mainToolbar)


        setupCitySearch()
        setupWeather()
        viewModel.loadHistoryWeather()
        viewModel.loading.observe(this) { isLoading ->
            view.progressBar.isVisible = isLoading ?: false
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.logout_button -> {
                val dialog = AlertDialog.Builder(this)
                dialog.setMessage(R.string.logout_accept)
                val listener = DialogInterface.OnClickListener { mInterface, btnId ->
                    if (btnId == DialogInterface.BUTTON_POSITIVE)
                        logout()
                }
                dialog.setPositiveButton(R.string.yes, listener)
                dialog.setNegativeButton(R.string.cancel, listener)
                dialog.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun logout() {
        authPref.logout()
        startActivity(Intent(this, StartActivity::class.java))
        finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun setupWeather() {
        view.weatherRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        view.weatherRecyclerView.adapter = weatherAdapter
        viewModel.historyWeather.observe(this) { history ->
            weatherAdapter.addAll(history)
        }
        viewModel.weather.observe(this) { weather ->
            weatherAdapter.add(weather)
            view.weatherRecyclerView.scrollToPosition(0)
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