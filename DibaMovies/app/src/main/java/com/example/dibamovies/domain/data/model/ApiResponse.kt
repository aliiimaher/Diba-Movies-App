package com.example.dibamovies.domain.data.model

data class ApiResponse(
    val status: Int,
    val message: String? = null,
    val error: String? = null,
)