package com.aamernabi.moments.playground

import androidx.lifecycle.*
import kotlinx.coroutines.delay

class LiveDataCouroutineBuilder : ViewModel() {

    private val _city = MutableLiveData<String>()

    val weather: LiveData<String> = _city.switchMap {
        liveData {
            emit("Loading")
            emit(Api.fetchWeather(it))
        }
    }


    fun setCity(city: String) {
        _city.value = city
    }
}

object Api {

    suspend fun fetchWeather(city: String): String {
        delay(2000)
        return when (city) {
            "London" -> "Rainy"
            else -> listOf("Sunny", "Rainy", "Cloudy", "Snowy").random()
        }
    }

}