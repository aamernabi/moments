package com.aamernabi.moments.datasource.remote

import com.aamernabi.moments.BuildConfig
import com.aamernabi.moments.utils.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

abstract class RetrofitFactory {

    companion object {
        fun getInstance(baseUrl: String = BASE_URL, timeout: Long = 120): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(getOkHttpBuilder(timeout).build())
                .build()
        }
    }
}

private fun getOkHttpBuilder(timeout: Long): OkHttpClient.Builder {
    val builder = OkHttpClient.Builder()
    builder.readTimeout(timeout, TimeUnit.SECONDS)
    builder.connectTimeout(timeout, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
    }
    builder.addInterceptor(CustomInterceptor())

    return builder
}