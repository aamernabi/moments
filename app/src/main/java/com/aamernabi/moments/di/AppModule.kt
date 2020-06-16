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

package com.aamernabi.moments.di

import com.aamernabi.core.dagger.scopes.FeatureScope
import com.aamernabi.moments.datasource.remote.AuthenticationInterceptor
import com.aamernabi.moments.datasource.remote.UnsplashApi
import com.aamernabi.moments.utils.Urls
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module(includes = [ViewModelsModule::class])
class AppModule {

    @com.aamernabi.moments.di.UnsplashApi
    @Provides
    fun provideUnsplashOkHttpClient(
        client: OkHttpClient
    ): OkHttpClient {
        return client.newBuilder()
            .addInterceptor(AuthenticationInterceptor())
            .build()
    }

    @com.aamernabi.moments.di.UnsplashApi
    @Provides
    @FeatureScope
    fun provideRetrofit(
        @com.aamernabi.moments.di.UnsplashApi okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Urls.UNSPLASH_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @FeatureScope
    fun provideUnSplashApi(
        @com.aamernabi.moments.di.UnsplashApi retrofit: Retrofit
    ): UnsplashApi {
        return retrofit.create(UnsplashApi::class.java)
    }
}
