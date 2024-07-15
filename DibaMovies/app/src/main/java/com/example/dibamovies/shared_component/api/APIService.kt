package com.example.dibamovies.shared_component.api

import com.example.dibamovies.domain.data.model.MovieItemResponse
import com.example.dibamovies.domain.data.model.movie.GenresResponse
import com.example.dibamovies.domain.data.model.user.User
import com.example.dibamovies.domain.data.model.user.UserRegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @GET("test")
    suspend fun getTest(): Response<MovieItemResponse.MovieItemResponseItem>

    @POST("register")
    suspend fun registerUser(@Body user: User): Response<UserRegisterResponse>

    @GET("genres")
    suspend fun getGenresList(): Response<GenresResponse>
}