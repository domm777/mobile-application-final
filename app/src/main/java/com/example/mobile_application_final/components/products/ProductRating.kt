package com.example.mobile_application_final.components.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductRating(rating: Double) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 0 until 5) {
            val fullStar = i < rating.toInt()
            val halfStar = !fullStar && (rating - i) >= 0.5

            when {
                fullStar -> Icon(Icons.Filled.Star, contentDescription = null, Modifier.size(20.dp))
                halfStar -> Icon(
                    Icons.AutoMirrored.Filled.StarHalf,
                    contentDescription = null,
                    Modifier.size(20.dp)
                )

                else -> Icon(
                    Icons.Filled.StarBorder,
                    contentDescription = null,
                    Modifier.size(20.dp)
                )
            }
        }
    }
}
