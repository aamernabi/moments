package com.aamernabi.moments.datasource.remote.photos

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosApiInterface {
    @GET("/photos")
    fun getPhotosAsync(@Query("page") page: Int, @Query("per_page") perPage: Int = 20): Deferred<List<Photo>>
}