package com.aamernabi.moments.datasource.remote

import com.aamernabi.moments.utils.UNSPLASH_API_KEY
import okhttp3.Interceptor
import okhttp3.Response


class AuthenticationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        builder.header(
            "Authorization",
            "Client-ID $UNSPLASH_API_KEY"
        )
        builder.header(
            "Accept-Version",
            "v1"
        )
        return chain.proceed(builder.build())
    }
}
