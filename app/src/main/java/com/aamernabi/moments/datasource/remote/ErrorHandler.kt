package com.aamernabi.moments.datasource.remote

import com.aamernabi.moments.datasource.remote.photos.Error
import com.aamernabi.moments.utils.CONNECTION_ERROR
import com.aamernabi.moments.utils.State
import com.aamernabi.moments.utils.fromJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend inline fun <reified T> coroutineErrorHandler(t: Throwable): State<T> {
   return when (t) {
      is IOException -> State.Error(t.message)
      is HttpException -> {
         val code = t.code()
         val errBodyStr = withContext(Dispatchers.IO) { t.response()?.errorBody()?.string() }
         val errors = errBodyStr.fromJson<Error>()?.errors
         val message = if (!errors.isNullOrEmpty()) errors[0] else CONNECTION_ERROR

         State.Error(message, code)
      }
      else -> State.Error(CONNECTION_ERROR)
   }
}