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

package com.aamernabi.moments.datasource.remote.photos

import com.squareup.moshi.Json

data class Photo(
    val id: String,
    val width: Int,
    val height: Int,
    val color: String,
    val description: String?,
    val urls: Urls,
    val links: Links
)

data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)

data class Links(
    val download: String,
    @field:Json(name = "download_location") val downloadLocation: String
)

data class Error(val errors: List<String>?)
