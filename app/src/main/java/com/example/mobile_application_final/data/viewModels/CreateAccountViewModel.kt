package com.example.mobile_application_final.data.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_application_final.data.models.User
import com.example.mobile_application_final.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CreateAccountViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val _user = MutableStateFlow<List<User>>(emptyList())
    val users: MutableStateFlow<List<User>> = _user

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _user.value = userRepository.getUsers()
        }
    }

    fun seedDbBtn() {
        userRepository.seedDb()
    }
}
