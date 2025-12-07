package com.example.mobile_application_final.data.viewModels

import androidx.lifecycle.ViewModel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CreateAccountScreenViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set
    var username by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var registrationMessage by mutableStateOf<String?>(null)
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onUsernameChange(newUsername: String) {
        username = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    private fun validateEmail(email: String) {}

    private fun validateUsername(username: String) {}

    private fun validatePassword(password: String) {}

    fun onRegisterClicked() {
    // was gonna try and do the week 6 validation stuff here,
    // but it's not in the requirement
//        registrationMessage = null
//
//        val validations = listOf(
//            validateEmail(email),
//            validateUsername(username),
//            validatePassword(password)
//        )
//
//        val firstError = validations.firstOrNull { it is ValidationResult.Error }
//        if (firstError is ValidationResult.Error) {
//            registrationMessage = firstError.message
//        } else {
//            registrationMessage = "Registration successful!"
//        }
    }
}