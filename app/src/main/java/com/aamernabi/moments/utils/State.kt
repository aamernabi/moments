package com.aamernabi.moments.utils

sealed class State<out T> {
    class Loading<out T> : State<T>()
    data class Success<out T>(val data: T?) : State<T>()
    data class Error<out T>(val message: String? = null, val errorCode: Int = -1) : State<T>()
}

/*fun <T> success(data: T): State.Success<T>.() -> Unit = {
    State.Success(data)
}*/


