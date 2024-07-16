package com.example.dibamovies.domain.data.model.user


import com.google.gson.annotations.SerializedName

data class UserRegisterResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("description")
    val description: Description,
    @SerializedName("status")
    val status: Int
) {
    data class Data(
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("studentNumber")
        val studentNumber: String,
        @SerializedName("updated_at")
        val updatedAt: String
    )

    data class Description(
        @SerializedName("en")
        val en: String,
        @SerializedName("fa")
        val fa: String
    )
}