package com.utachiwana.messenger.ui

import android.util.Log
import androidx.lifecycle.*
import com.utachiwana.messenger.data.pojo.CityClassItem
import com.utachiwana.messenger.data.pojo.CurrentWeather
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _cities = MutableLiveData<List<CityClassItem>?>()
    val cities: LiveData<List<CityClassItem>?> = _cities

    private val _historyWeather = MutableLiveData<ArrayList<CurrentWeather>>()
    val historyWeather: LiveData<ArrayList<CurrentWeather>> = _historyWeather

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun searchCity(name: String) {
        viewModelScope.launch {
            _cities.value = repository.searchCity(name)
        }
    }

    fun getCityWeather(city: CityClassItem) {
        _loading.value = true
        viewModelScope.launch {
            repository.getWeather(city)?.let { weather ->
                _historyWeather.value?.add(0,weather)
            }
            _loading.value = false
        }
    }

    fun loadHistoryWeather() {
        _loading.value = true
        viewModelScope.launch {
            _historyWeather.value = ArrayList(repository.getWeatherHistory()
                .sortedByDescending { it.uid })
            _loading.value = false
        }
    }

    class Factory @Inject constructor(private val repository: MainRepository) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }
    }

}