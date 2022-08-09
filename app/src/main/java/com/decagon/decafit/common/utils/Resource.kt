package com.decagon.decafit.common.utils

sealed class Resource<out T>(val datas: T? = null, val messages: String? = null) {

    data class Success<T>(val data: T) : Resource<T>(data)
    class Loading<T>: Resource<T>()
//    data class Loading<T>(val data: T? = null) : Resource<T>(data)
    data class Error<T>(val message: String) : Resource<T>(null, message)

}

