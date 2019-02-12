package com.aamernabi.moments.datasource.remote

import com.aamernabi.moments.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class CustomInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        builder.header(
                "Authorization",
                "Client-ID c6834e6a0bfd4519c07f720e00de2a4724a76e4a6c9a9e904bdb036e4c132396"
        )
        builder.header(
                "Accept-Version",
                "v1"
        )
        return chain.proceed(builder.build())
    }
}

fun getOkHttpBuilder(timeout: Long): OkHttpClient.Builder {
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
