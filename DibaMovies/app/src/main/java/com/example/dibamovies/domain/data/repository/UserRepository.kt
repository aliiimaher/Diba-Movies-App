package com.example.dibamovies.domain.data.repository

import com.example.dibamovies.domain.data.database.dao.UserDao
import com.example.dibamovies.domain.data.database.entities.UserEntity

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun getUserCount(): Int? {
        return userDao.getUserCount()
    }
}