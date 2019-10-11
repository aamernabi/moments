package com.aamernabi.moments.utils

/*
fun something(success: LoanOffer.() -> Unit, error: String.() -> Unit,
              failure: Throwable.() -> Unit): Disposable {
    val loanService = ApiClient.retrofit.create(LoanApiInterface::class.java)

    return loanService.customizeLoan(2, "", "")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            val data = it.data ?: return@subscribe failure(Throwable(CONNECTION_ERROR))
            when {
                "success" == it.status -> success(data)
                "error" == it.status && !it.errors.isNullOrEmpty() -> error(it.errors[0])
                else -> failure(Throwable(CONNECTION_ERROR))
            }
        }, { failure(it) })
}

fun test() {
    success(LoanOffer("", "", "", "", ""))

    something({

    }, {

    }, {

    })
}

sealed class Result<out T> {
    data class Success<out T>(val data: T?) : Result<T>()
    data class Failure<out T>(val throwable: Throwable) : Result<T>()
    data class Error<out T>(val message: String, val errorCode: String = "") : Result<T>()
}

fun <T> success(data: T): Result.Success<T>.() -> Unit = {
    Result.Success(data)
}*/
