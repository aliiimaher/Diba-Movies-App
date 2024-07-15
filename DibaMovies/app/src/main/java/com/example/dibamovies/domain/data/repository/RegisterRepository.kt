package com.example.dibamovies.domain.data.repository

import com.example.dibamovies.domain.data.model.user.User
import com.example.dibamovies.domain.data.model.user.UserRegisterResponse
import com.example.dibamovies.shared_component.api.APIService

class RegisterRepository(private val api: APIService) {
    suspend fun registerUser(user: User): Result<UserRegisterResponse>
//    = api.registerUser(user)
    {
        val response = api.registerUser(user)
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(Throwable("سرویس خطا داشت"))
        } else {
            Result.failure(Throwable("سرویس انجام نشد"))
        }
    }
}