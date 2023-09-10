package com.dew.dewunittesting.retrofit

import retrofit2.Response

sealed class Resources<out T> {
    data class Success<out T>(val data: T): Resources<T>()
    data class Error<T>(val response: Response<T>): Resources<T>()
}

fun <T> Response<T>.parseResponse(): Resources<T> {
    return if (this.isSuccessful && this.body() != null) {
        val responseBody = this.body()
        Resources.Success(responseBody!!)
    } else {
        Resources.Error(this)
    }
}