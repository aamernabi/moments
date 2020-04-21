package com.aamernabi.moments.playground

import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LiveDataCouroutineBuilder : ViewModel() {

    private val _weather = MutableLiveData<String>()
    val weather: LiveData<String> get() = _weather

    init {
        viewModelScope.launch {
            _weather.value = "Loading"
            _weather.value = Api.fetchWeather()
        }
    }

    //using coroutine builder
    val weather2: LiveData<String> = liveData {
        emit("Loading")
        emit(Api.fetchWeather())
    }
}

object Api {

    suspend fun fetchWeather(): String {
        delay(2000)
        return listOf("Sunny", "Rainy", "Cloudy", "Snowy").random()
    }

}