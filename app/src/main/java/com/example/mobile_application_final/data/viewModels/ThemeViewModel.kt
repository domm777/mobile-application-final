package com.example.mobile_application_final.data.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class ThemeViewModel(
    private val stateHandle: SavedStateHandle,
) : ViewModel() {
    // this state is initially null so we can go with the system setting first
    val isDarkMode = stateHandle.getStateFlow<Boolean?>("isDarkMode", null)

    fun onDarkModeChanged(value: Boolean?) {
        stateHandle["isDarkMode"] = value
    }
}
