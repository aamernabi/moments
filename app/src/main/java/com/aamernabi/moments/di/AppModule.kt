package com.aamernabi.moments.di

import com.aamernabi.moments.BuildConfig
import com.aamernabi.moments.datasource.remote.AuthenticationInterceptor
import com.aamernabi.moments.datasource.remote.UnsplashApi
import com.aamernabi.moments.utils.CONNECTION_TIMEOUT
import com.aamernabi.moments.utils.READ_TIMEOUT
import com.aamernabi.moments.utils.Urls
import com.aamernabi.moments.utils.WRITE_TIMEOUT
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ViewModelsModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.interceptors().add(AuthenticationInterceptor())

        builder.interceptors().add(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })

        builder.retryOnConnectionFailure(false)

        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)

        return builder.build()
    }

    @Provides
    @Named("GlideModule")
    @Singleton
    fun provideGlideHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Urls.UNSPLASH_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideUnSplashApi(retrofit: Retrofit): UnsplashApi {
        return retrofit.create(UnsplashApi::class.java)
    }

}