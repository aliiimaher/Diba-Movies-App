package com.example.dibamovies.shared_component.api

import com.example.dibamovies.domain.data.Constants
import com.example.dibamovies.domain.data.model.movie.GenresResponse
import com.example.dibamovies.domain.data.model.movie.MoviesResponse
import com.example.dibamovies.domain.data.model.movie.SingleMovieResponse
import com.example.dibamovies.domain.data.model.user.User
import com.example.dibamovies.domain.data.model.user.UserRegisterResponse
import retrofit2.Response
import retrofit2.http.*

interface APIService {
    @POST("register")
    suspend fun registerUser(@Body user: User): Response<UserRegisterResponse>

    @GET("genres/${Constants.PATH_ID}/movies")
    suspend fun getTopMovies(): Response<MoviesResponse>

    @GET("genres")
    suspend fun getGenresList(): Response<GenresResponse>

    @GET("movies")
    suspend fun getLastMovies(): Response<MoviesResponse>

    @GET("movies/${Constants.PATH_ID}/")
    suspend fun searchMovie(@Query("query") query: String): Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") id: Int): Response<SingleMovieResponse>
}