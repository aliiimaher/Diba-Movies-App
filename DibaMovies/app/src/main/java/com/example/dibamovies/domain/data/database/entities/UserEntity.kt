package com.example.dibamovies.domain.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dibamovies.domain.data.Constants

@Entity(tableName = Constants.USER_TABLE)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val fullName: String,
    val studentNumber: String,
    val email: String,
    val password: String
)