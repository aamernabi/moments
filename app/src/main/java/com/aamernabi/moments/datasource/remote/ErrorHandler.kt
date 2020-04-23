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

import com.aamernabi.core.data.State
import com.aamernabi.moments.datasource.remote.photos.Error
import com.aamernabi.moments.utils.CONNECTION_ERROR
import com.aamernabi.moments.utils.fromJson
import java.io.IOException
import java.net.UnknownHostException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend inline fun coroutineErrorHandler(t: Throwable): State.Error {
    return when (t) {
        is UnknownHostException -> State.Error(t)
        is IOException -> State.Error(t)
        is HttpException -> {
            val code = t.code()
            val errBodyStr = withContext(Dispatchers.IO) { t.response()?.errorBody()?.string() }
            val errors = errBodyStr.fromJson<Error>()?.errors
            val message = if (!errors.isNullOrEmpty()) errors[0] else CONNECTION_ERROR

            State.Error(Throwable(message), code)
        }
            else -> State.Error(t = t)
    }
}
