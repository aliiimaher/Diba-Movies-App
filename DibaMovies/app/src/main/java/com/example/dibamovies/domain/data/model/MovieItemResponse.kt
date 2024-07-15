package com.example.dibamovies.domain.data.model

class MovieItemResponse : ArrayList<MovieItemResponse.MovieItemResponseItem>() {
    data class MovieItemResponseItem(
        val country: String,
        val genres: List<String>,
        val id: Int,
        val images: List<String>,
        val imdb_rating: String,
        val poster: String,
        val title: String,
        val year: String,
    )
}