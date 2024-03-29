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

package com.aamernabi.core.dagger

import com.squareup.moshi.Moshi
import dagger.Component
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.converter.moshi.MoshiConverterFactory

@Singleton
@Component(modules = [CoreModule::class])
interface CoreComponent {

    @Component.Builder interface Builder {
        fun build(): CoreComponent
    }

    fun provideOkHttpClient(): OkHttpClient
    fun provideMoshi(): Moshi
    fun provideMoshiConverterFactory(): MoshiConverterFactory
}
