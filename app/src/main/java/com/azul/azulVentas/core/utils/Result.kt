package com.azul.azulVentas.core.utils

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>() //Firebase
    data class ErrorPG(val throwable: Throwable) : Result<Nothing>() //PostGreSQL
}