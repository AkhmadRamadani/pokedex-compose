package com.example.pokedex.common

sealed class Result<out T> {
    data object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val throwable: Throwable, val message: String? = throwable.message) : Result<Nothing>()
}

inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> = when (this) {
    is Result.Loading -> Result.Loading
    is Result.Error -> this
    is Result.Success -> Result.Success(transform(data))
}
