package com.example.dibamovies.domain.data.repository

import android.content.Context
import com.example.dibamovies.domain.data.model.movie.MoviesResponse
import com.example.dibamovies.utils.FavoriteMoviesManager

class FavoriteMoviesRepository(private val context: Context) {
    fun getFavorites(): List<MoviesResponse.Data> {
        return FavoriteMoviesManager.getFavorites(context)
    }
}