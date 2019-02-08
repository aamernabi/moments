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
