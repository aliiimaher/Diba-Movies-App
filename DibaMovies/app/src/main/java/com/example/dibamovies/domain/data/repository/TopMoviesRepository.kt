package com.example.dibamovies.domain.data.repository

import com.example.dibamovies.domain.data.model.movie.MoviesResponse
import com.example.dibamovies.shared_component.api.APIService

class TopMoviesRepository(private val api: APIService) {
    suspend fun getTopMovies(): Result<MoviesResponse> {
        val response = api.getTopMovies()
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(Throwable("سرویس خطا داشت"))
        } else {
            Result.failure(Throwable("سرویس انجام نشد"))
        }
    }
}