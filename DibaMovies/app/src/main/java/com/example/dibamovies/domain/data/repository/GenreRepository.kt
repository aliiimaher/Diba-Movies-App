package com.example.dibamovies.domain.data.repository

import com.example.dibamovies.domain.data.model.movie.GenresResponse
import com.example.dibamovies.shared_component.api.APIService

class GenreRepository(private val api: APIService) {
    suspend fun getAllGenres(): Result<GenresResponse> {
        val response = api.getGenresList()
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(Throwable("سرویس خطا داشت"))
        } else {
            Result.failure(Throwable("سرویس انجام نشد"))
        }
    }
}