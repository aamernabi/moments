package com.aamernabi.moments.datasource.remote

import com.aamernabi.moments.datasource.remote.photos.Photo
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {
    @GET("/photos")
    fun getPhotosAsync(@Query("page") page: Int, @Query("per_page") perPage: Int = 20): Deferred<List<Photo>>
}