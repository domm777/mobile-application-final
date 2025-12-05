package com.example.mobile_application_final.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_application_final.viewModels.ShopScreenViewModel

@Composable
fun ShopScreen(modifier: Modifier) {
    val viewModel: ShopScreenViewModel = viewModel()
    val products by viewModel.products.collectAsState()

    LazyColumn() {
        items(products.size, key = { products[it].id }, itemContent = {
            val product = products[it]
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(product.name)
            }
        })
    }
}