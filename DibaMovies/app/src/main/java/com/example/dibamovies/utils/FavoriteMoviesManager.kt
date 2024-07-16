package com.example.dibamovies.utils

import android.content.Context
import com.example.dibamovies.domain.data.model.movie.MoviesResponse
import com.example.dibamovies.domain.data.model.movie.SingleMovieResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FavoriteMoviesManager {
    private const val PREF_NAME = "favorite_movies"
    private const val KEY_FAVORITES = "favorites"

    fun saveFavorite(context: Context, movie: MoviesResponse.Data) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val favorites = getFavorites(context).toMutableList()
        favorites.add(movie)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_FAVORITES, Gson().toJson(favorites))
        editor.apply()
    }

    fun removeFavorite(context: Context, movieId: Int) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val favorites = getFavorites(context).filterNot { it.id == movieId }
        val editor = sharedPreferences.edit()
        editor.putString(KEY_FAVORITES, Gson().toJson(favorites))
        editor.apply()
    }

    fun isFavorite(context: Context, movieId: Int): Boolean {
        return getFavorites(context).any { it.id == movieId }
    }

    fun getFavorites(context: Context): List<MoviesResponse.Data> {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(KEY_FAVORITES, null) ?: return emptyList()
        val type = object : TypeToken<List<MoviesResponse.Data>>() {}.type
        return Gson().fromJson(json, type)
    }
}