package com.example.dibamovies.domain.data.model.movie


import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("description")
    val description: Description,
    @SerializedName("metadata")
    val metadata: Metadata,
    @SerializedName("status")
    val status: Int
) {
    data class Data(
        @SerializedName("genres")
        val genres: List<String>,
        @SerializedName("id")
        val id: Int,
        @SerializedName("images")
        val images: List<String>,
        @SerializedName("poster")
        val poster: String,
        @SerializedName("title")
        val title: String
    )

    data class Description(
        @SerializedName("en")
        val en: String,
        @SerializedName("fa")
        val fa: String
    )

    data class Metadata(
        @SerializedName("current_page")
        val currentPage: Int,
        @SerializedName("has_next")
        val hasNext: Boolean,
        @SerializedName("has_prev")
        val hasPrev: Boolean,
        @SerializedName("page_count")
        val pageCount: Int,
        @SerializedName("per_page")
        val perPage: Int,
        @SerializedName("total_count")
        val totalCount: Int
    )
}