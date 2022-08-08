package com.utachiwana.messenger.ui

import androidx.lifecycle.*
import com.utachiwana.messenger.data.network.FConfig
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _state = MutableLiveData<String?>()
    val state: LiveData<String?> get() = _state

    fun auth(login: String, pwd: String) {
        val result = FConfig.checkRules(login, pwd)
        if (result != null) {
            _state.value = result
            return
        }
        _isLoading.value = true
        viewModelScope.launch {
            _state.value = repository.auth(login, pwd)
            _isLoading.value = false
        }
    }

    fun register(login: String, pwd: String, repeatPwd: String = pwd) {
        val result = FConfig.checkRules(login, pwd, repeatPwd)
        if (result != null) {
            _state.value = result
            return
        }
        _isLoading.value = true
        viewModelScope.launch {
            _state.value = repository.register(login, pwd)
            _isLoading.value = false
        }
    }

    fun isAuth() {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.isAuth()
            if (result != null)
                _state.value = result
            _isLoading.value = false
        }
    }

    fun clearState() {
        _state.value = null
    }

    class Factory @Inject constructor(private val repository: AuthRepository): ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AuthViewModel(repository) as T
        }
    }

}