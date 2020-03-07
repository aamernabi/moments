package com.aamernabi.moments.utils

const val CONNECTION_TIMEOUT: Long = 60
const val READ_TIMEOUT: Long = 60
const val WRITE_TIMEOUT: Long = 90

class Errors {
    companion object {
        const val NO_DATA = 10
    }
}

object Urls {
    const val UNSPLASH_BASE_URL = "https://api.unsplash.com"
}