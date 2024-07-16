package com.example.dibamovies.presentation.ui.register

import android.util.Log
import androidx.lifecycle.*
import com.example.dibamovies.domain.data.model.user.User
import com.example.dibamovies.domain.data.model.user.UserRegisterResponse
import com.example.dibamovies.domain.data.repository.RegisterRepository
import com.example.dibamovies.presentation.ui.register.validation.UserValidator
import com.example.dibamovies.presentation.ui.register.validation.ValidationResult
import com.example.dibamovies.shared_component.api.API
import kotlinx.coroutines.launch
import java.security.MessageDigest

class RegisterViewModel(private val repository: RegisterRepository) : ViewModel() {
    //region Properties
    private val userValidator = UserValidator()
    private val _registrationResult = MutableLiveData<ValidationResult<UserRegisterResponse>>()
    val registrationResult: LiveData<ValidationResult<UserRegisterResponse>> get() = _registrationResult
    //endregion

    //region Methods
    fun validateUser(
        name: String,
        stdNumber: String,
        email: String,
        password: String,
    ): ValidationResult<Unit> {
        val nameResult = userValidator.validateName(name)
        if (nameResult is ValidationResult.Error) return nameResult

        val stdNumberResult = userValidator.validateStudentNumber(stdNumber)
        if (stdNumberResult is ValidationResult.Error) return stdNumberResult

        val emailResult = userValidator.validateEmail(email)
        if (emailResult is ValidationResult.Error) return emailResult

        val passwordResult = userValidator.validatePassword(password)
        if (passwordResult is ValidationResult.Error) return passwordResult

        return ValidationResult.Success(Unit)
    }

    fun md5Hash(password: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(password.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }

    fun registerUser(
        name: String,
        stdNumber: String,
        email: String,
        password: String,
    ) {
        val validationResult = validateUser(name, stdNumber, email, password)
        if (validationResult is ValidationResult.Success) {
            val hashedPassword = md5Hash(password)
            val user = User(name, stdNumber, email, hashedPassword)
            viewModelScope.launch {
                try {
                    val response = repository.registerUser(user)
                    Log.d("RegisterViewModel", "Registration response: $response")
                    if (response.isSuccess) {
                        _registrationResult.postValue(ValidationResult.Success(response.getOrThrow()))
                    } else {
                        _registrationResult.postValue(ValidationResult.Error("Registration failed"))
                    }
                } catch (e: Exception) {
                    _registrationResult.postValue(
                        ValidationResult.Error(
                            e.message ?: "Unknown error"
                        )
                    )
                }
            }
        } else {
            @Suppress("UNCHECKED_CAST")
            _registrationResult.postValue(validationResult as ValidationResult<UserRegisterResponse>)
        }
    }
    //endregion
}

class RegisterModule {
    companion object {
        val watchRepository: RegisterRepository by lazy {
            RegisterRepository(API.baseUserService)
        }
    }
}

class RegisterViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(RegisterModule.watchRepository) as T
        }
        throw java.lang.IllegalArgumentException("wrong dependency")
    }
}