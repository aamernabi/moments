package com.aamernabi.moments.datasource.remote

import com.aamernabi.moments.datasource.remote.photos.Photo
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {

    @GET("/photos")
    fun getPhotosAsync(@Query("page") page: Int, @Query("per_page") perPage: Int = 20): Deferred<List<Photo>>

    companion object {
        private const val BASE_URL = "https://api.unsplash.com"

        fun getInstance(timeout: Long = 120) = create(HttpUrl.parse(BASE_URL)!!, timeout)

        private fun create(httpUrl: HttpUrl, timeout: Long): UnsplashApi {
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(getOkHttpBuilder(timeout).build())
                .build()
                .create(UnsplashApi::class.java)
        }
    }
}