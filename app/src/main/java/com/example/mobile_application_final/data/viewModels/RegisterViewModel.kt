package com.example.mobile_application_final.data.viewModels

import android.app.Application
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.trimmedLength
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.mobile_application_final.R
import com.example.mobile_application_final.data.repositories.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch


sealed class RegistrationResult {
    data class Success(val user: FirebaseUser) : RegistrationResult()
    data class Invalid(val ex: FirebaseAuthException) : RegistrationResult()
    object InvalidDisplayName : RegistrationResult()
    object InvalidEmail : RegistrationResult()
    object InvalidPassword : RegistrationResult()
}

class RegisterViewModel(app: Application) : AndroidViewModel(app) {
    private val userRepo = UserRepository()

    var errorMessage: String? by mutableStateOf(null)
        private set
    var isLoading by mutableStateOf(false)
        private set

    var displayName by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun onRegister(successCallback: (FirebaseUser) -> Unit) {
        if (isLoading) return
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            when (val result = register()) {
                is RegistrationResult.Success -> successCallback(result.user)
                is RegistrationResult.Invalid -> errorMessage =
                    result.ex.localizedMessage ?: result.ex.message

                RegistrationResult.InvalidDisplayName -> errorMessage =
                    application.getString(R.string.display_name_cannot_be_blank)

                RegistrationResult.InvalidEmail,
                    -> errorMessage = application.getString(R.string.email_cannot_be_blank)

                RegistrationResult.InvalidPassword -> errorMessage =
                    application.getString(R.string.password_min_len)
            }

            isLoading = false
        }
    }

    private suspend fun register(): RegistrationResult {
        try {
            if (displayName.isBlank()) {
                return RegistrationResult.InvalidDisplayName
            }

            if (email.isBlank()) {
                return RegistrationResult.InvalidEmail
            }

            if (password.trimmedLength() < 8) {
                return RegistrationResult.InvalidPassword
            }

            return RegistrationResult.Success(userRepo.register(displayName, email, password))
        } catch (e: FirebaseAuthException) {
            return RegistrationResult.Invalid(e)
        }
    }

    fun onEmailChanged(email: String) {
        this.email = email
    }

    fun onDisplayNameChanged(name: String) {
        displayName = name
    }

    fun onPasswordChanged(password: String) {
        this.password = password
    }
}