package com.example.dibamovies.domain.data.repository

import com.example.dibamovies.domain.data.model.movie.SingleMovieResponse
import com.example.dibamovies.shared_component.api.APIService

class SingleMovieRepository(private val api: APIService) {
    suspend fun getMovieDetail(movieId: Int): Result<SingleMovieResponse> {
        val response = api.getMovieDetail(movieId)
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(Throwable("سرویس خطا داشت"))
        } else {
            Result.failure(Throwable("سرویس انجام نشد"))
        }
    }
}