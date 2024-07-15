package com.example.dibamovies.presentation.ui.register.validation

class UserValidator {
    fun validateName(name: String): ValidationResult<String> {
        val regex = "^[A-Za-z ]+$"
        return if (name.matches(regex.toRegex())) {
            ValidationResult.Success(name)
        } else {
            ValidationResult.Error("Invalid name. Please use only letters and spaces.")
        }
    }

    fun validateStudentNumber(studentNumber: String): ValidationResult<String> {
        val regex = "^\\d{7,8}$"
        return if (studentNumber.matches(regex.toRegex())) {
            ValidationResult.Success(studentNumber)
        } else {
            ValidationResult.Error("Invalid student number. It should be exactly 7 or 8 digits.")
        }
    }

    fun validateEmail(email: String): ValidationResult<String> {
        val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return if (email.matches(regex.toRegex())) {
            ValidationResult.Success(email)
        } else {
            ValidationResult.Error("Invalid email address.")
        }
    }

    fun validatePassword(password: String): ValidationResult<String> {
        val regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"
        return if (password.matches(regex.toRegex())) {
            ValidationResult.Success(password)
        } else {
            ValidationResult.Error("Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.")
        }
    }
}
