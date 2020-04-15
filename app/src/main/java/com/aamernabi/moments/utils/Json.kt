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

package com.aamernabi.moments.utils

import com.squareup.moshi.Moshi

val moshi: Moshi = Moshi.Builder().build()

inline fun <reified T> String?.fromJson(): T? {
    this ?: return null
    return try {
        val adapter = moshi.adapter<T>(T::class.java)
        adapter.fromJson(this)
    } catch (e: Exception) {
        null
    }
}

inline fun <reified T> T?.toJson(): String? {
    this ?: return null
    return try {
        val adapter = moshi.adapter<T>(T::class.java)
        adapter.toJson(this)
    } catch (e: Exception) {
        null
    }
}
