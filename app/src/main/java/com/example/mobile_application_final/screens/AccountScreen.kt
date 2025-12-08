package com.example.mobile_application_final.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.mobile_application_final.R
import com.example.mobile_application_final.data.viewModels.ThemeViewModel

@Composable
fun AccountScreen(
    modifier: Modifier,
    themeModel: ThemeViewModel? = null,
    onLogOut: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("account_screen"),
        contentAlignment = Alignment.Center
    ) {
        themeModel?.let { model ->
            val isDarkMode by model.isDarkMode.collectAsState()

            Row {
                Text(stringResource(R.string.light_theme))
                Switch(
                    checked = isDarkMode ?: isSystemInDarkTheme(),
                    onCheckedChange = model::onDarkModeChanged,
                    modifier = Modifier.testTag("dark_theme_switch")
                )
                Text(stringResource(R.string.dark_theme))
            }
        }

//        Text(
//            text = "This is Account Screen",
//            style = MaterialTheme.typography.headlineMedium
//        )
    }
}
