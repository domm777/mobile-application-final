package com.example.mobile_application_final.data.viewModels

import android.app.Application
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.trimmedLength
import androidx.lifecycle.AndroidViewModel
import com.example.mobile_application_final.data.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser

@Immutable
data class RegistrationException(val msg: String) : IllegalStateException(msg)

class RegisterViewModel(app: Application) : AndroidViewModel(app) {
    private val userRepo = UserRepository()

    var isLoading by mutableStateOf(false)
        private set

    var displayName by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    suspend fun onRegister(): FirebaseUser {
        try {
            isLoading = true

            if (displayName.isBlank()) {
                throw RegistrationException("Display name cannot be blank")
            }

            if (email.isBlank()) {
                throw RegistrationException("Email cannot be blank")
            }

            if (password.trimmedLength() < 8) {
                throw RegistrationException("Password must be at least 8 characters")
            }

            val user = userRepo.register(displayName, email, password)
            user?.let {
                return it
            }

            throw RegistrationException("Registration failed")
        } catch (e: FirebaseAuthException) {
            throw RegistrationException(e.message ?: "Invalid registration credentials")
        } finally {
            isLoading = false
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