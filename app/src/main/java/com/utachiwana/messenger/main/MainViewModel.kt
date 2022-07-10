package com.utachiwana.messenger.main

import android.util.Log
import androidx.lifecycle.*
import com.utachiwana.messenger.main.pojo.CityClassItem
import com.utachiwana.messenger.main.pojo.CurrentWeather
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _cities = MutableLiveData<List<CityClassItem>?>()
    val cities : LiveData<List<CityClassItem>?> = _cities

    private val _weather = MutableLiveData<CurrentWeather?>()
    val weather : LiveData<CurrentWeather?> = _weather

    private val _loading = MutableLiveData(false)
    val loading : LiveData<Boolean> = _loading

    fun searchCity(name: String){
        viewModelScope.launch {
            _cities.value = repository.searchCity(name)
        }
    }

    fun getCityWeather(city: CityClassItem) {
        _loading.value = true
        viewModelScope.launch {
            _weather.value = repository.getWeather(city)
            _loading.value = false
        }
    }

    class Factory @Inject constructor(private val repository: MainRepository) : ViewModelProvider.Factory{

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }
    }

}