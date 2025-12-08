package com.example.mobile_application_final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_application_final.data.viewModels.ThemeViewModel
import com.example.mobile_application_final.screens.navigation.AppNavigation
import com.example.mobile_application_final.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeModel: ThemeViewModel = viewModel()
            val isDarkMode by themeModel.isDarkMode.collectAsState()

            AppTheme(darkTheme = isDarkMode ?: isSystemInDarkTheme()) {
                // Remove any Scaffold here. It is handled inside AppNavigation.
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(themeModel)
                }
            }
        }
    }
}
