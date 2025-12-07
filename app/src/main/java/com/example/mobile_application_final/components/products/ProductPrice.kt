package com.example.mobile_application_final.components.products

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import java.text.NumberFormat

@Composable
fun ProductPrice(price: Double) {
    // make use of the local currency formatter
    val currencyFormatter = NumberFormat.getCurrencyInstance()

    Text(
        currencyFormatter.format(price),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.tertiary,
    )
}
