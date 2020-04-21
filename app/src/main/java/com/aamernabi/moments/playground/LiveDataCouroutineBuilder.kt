/*
 * Copyright 2020 aamernabi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aamernabi.moments.playground

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
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
