package com.example.mobile_application_final.components

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mobile_application_final.data.models.Product

@Composable
fun HProductCard(modifier: Modifier, product: Product) {
    Card(
            modifier = Modifier
                .width(300.dp)
                .height(150.dp)
                .padding(4.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            shape = MaterialTheme.shapes.large
        ){
        Row(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(modifier = Modifier.padding(3.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = product.image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                    Row (verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center){
                        val rating = product.rating
                        for (i in 0 until 5) {
                            val fullStar = i < rating.toInt()
                            val halfStar = !fullStar && (rating - i) >= 0.5

                            when {
                                fullStar -> Icon(Icons.Filled.Star, contentDescription = null, Modifier.size(20.dp))
                                halfStar -> Icon(Icons.AutoMirrored.Filled.StarHalf, contentDescription = null, Modifier.size(20.dp))
                                else -> Icon(Icons.Filled.StarBorder, contentDescription = null, Modifier.size(20.dp))
                            }
                        }
                    }

            }
            Spacer(modifier = Modifier.width(8.dp))
            Column() {
                Text(product.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    product.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "$${String.format("%.2f", product.price)}",
                    style = MaterialTheme.typography.titleLarge
                )
                Button(modifier = Modifier.fillMaxWidth(), onClick = {}) { Text("Add to Cart") }
            }
        }
    }
}