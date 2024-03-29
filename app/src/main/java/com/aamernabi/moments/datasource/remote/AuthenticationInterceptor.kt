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

package com.aamernabi.moments.datasource.remote

import com.aamernabi.moments.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        builder.header(
            "Authorization",
            "Client-ID ${BuildConfig.UNSPLASH_API_KEY}"
        )
        builder.header(
            "Accept-Version",
            "v1"
        )
        return chain.proceed(builder.build())
    }
}
