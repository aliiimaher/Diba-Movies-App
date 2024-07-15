package com.example.dibamovies.domain.data.repository

import com.example.dibamovies.domain.data.model.movie.MoviesResponse
import com.example.dibamovies.shared_component.api.APIService

class LastMoviesRepository(private val api: APIService) {
    suspend fun getLastMovies(): Result<MoviesResponse> {
        val response = api.getLastMovies()
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(Throwable("سرویس خطا داشت"))
        } else {
            Result.failure(Throwable("سرویس انجام نشد"))
        }
    }
}