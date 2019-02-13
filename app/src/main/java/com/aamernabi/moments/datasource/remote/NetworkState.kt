package com.aamernabi.moments.datasource.remote


sealed class NetworkState<out T> {
    class Loading<out T> : NetworkState<T>()
    data class Success<out T>(val data: T?) : NetworkState<T>()
    data class Failure<out T>(val throwable: Throwable) : NetworkState<T>()
}