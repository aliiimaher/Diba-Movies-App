package com.example.dibamovies.presentation.ui.register.validation

sealed class ValidationResult<out T> {
    data class Success<T>(val data: T) : ValidationResult<T>()
    data class Error(val message: String) : ValidationResult<Nothing>()
}
