package com.example.mobile_application_final.data.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.mobile_application_final.R
import com.example.mobile_application_final.data.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

sealed class LoginValidationResult {
    data class Success(val user: FirebaseUser) : LoginValidationResult()
    data class InvalidCredentials(val ex: FirebaseAuthException) : LoginValidationResult()
    object InvalidEmail : LoginValidationResult()
    object InvalidPassword : LoginValidationResult()
}

class LoginViewModel(app: Application) : AndroidViewModel(app) {
    private val userRepository = UserRepository()

    var errorMessage: String? by mutableStateOf(null)
        private set
    var isLoading by mutableStateOf(false)
        private set

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun onAttemptLogin(onSuccess: (FirebaseUser) -> Unit) {
        if (isLoading) return
        isLoading = true
        errorMessage = null

        // using coroutines so this doesn't block the rendering thread
        viewModelScope.launch {
            when (val result = login()) {
                is LoginValidationResult.Success -> onSuccess(result.user)
                is LoginValidationResult.InvalidCredentials ->
                    errorMessage = result.ex.localizedMessage ?: result.ex.message

                LoginValidationResult.InvalidEmail -> errorMessage =
                    application.getString(R.string.login_invalid_email)

                LoginValidationResult.InvalidPassword -> errorMessage =
                    application.getString(R.string.login_invalid_password)
            }

            // always return loading back to false so this can be tried again
            isLoading = false
        }
    }

    suspend fun login(): LoginValidationResult {
        try {
            val validation = listOf(
                validateEmail(email),
                validatePassword(password)
            )

            // finds the first validation that isn't null and returns that
            validation.find { it != null }?.let {
                return it
            }

            // this will either throw or return a success
            return LoginValidationResult.Success(userRepository.login(email, password))
        } catch (e: FirebaseAuthException) {
            return LoginValidationResult.InvalidCredentials(e)
        }
    }

    private fun validateEmail(email: String): LoginValidationResult? {
        if (email.isBlank()) {
            return LoginValidationResult.InvalidEmail
        }

        return null
    }

    private fun validatePassword(password: String): LoginValidationResult? {
        if (password.isBlank()) {
            return LoginValidationResult.InvalidPassword
        }

        return null
    }

    fun onEmailChanged(username: String) {
        this.email = username
    }

    fun onPasswordChanged(password: String) {
        this.password = password
    }
}