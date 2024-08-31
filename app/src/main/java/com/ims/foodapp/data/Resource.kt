package com.ims.foodapp.data

sealed class Resource<out T>(val data: T? = null, val exception: Exception? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T> : Resource<T>()
    class Error<T>(exception: Exception) : Resource<T>(exception = exception)
}