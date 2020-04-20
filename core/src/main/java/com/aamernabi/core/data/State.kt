package com.aamernabi.core.data

sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Success<out T>(val data: T?) : State<T>()
    data class Error(val t: Throwable) : State<Nothing>()
}

fun <T> State<T>.data(): T? {
    return when (this) {
        is State.Success -> this.data
        else -> null
    }
}