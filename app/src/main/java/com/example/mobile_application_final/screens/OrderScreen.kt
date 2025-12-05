package com.example.mobile_application_final.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun OrderScreen(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize().testTag("order_screen"),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "This is Order Screen",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}