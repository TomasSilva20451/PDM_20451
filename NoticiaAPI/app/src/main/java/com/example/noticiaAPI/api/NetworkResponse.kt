package com.example.noticiaAPI.api

// T referes to WeatherModel
sealed class NetworkResponse<out T> {
    data class Success<out T> (val data : T) : NetworkResponse<T>()
    data class Error(val message : String ) : NetworkResponse<Nothing>()
    object Loading : NetworkResponse<Nothing>()
}