package com.example.dibamovies.domain.data.repository

import com.example.dibamovies.domain.data.model.movie.MoviesResponse
import com.example.dibamovies.shared_component.api.APIService

class SearchRepository(private val api: APIService) {
    suspend fun searchMovie(query: String): Result<MoviesResponse> {
        val response = api.searchMovie(query)
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(Throwable("سرویس خطا داشت"))
        } else {
            Result.failure(Throwable("سرویس انجام نشد"))
        }
    }
}