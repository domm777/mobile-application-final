package com.example.mobile_application_final.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_application_final.components.HProductCard
import com.example.mobile_application_final.components.VProductCard
import com.example.mobile_application_final.data.viewModels.ShopScreenViewModel
import com.example.mobile_application_final.ui.theme.MobileapplicationfinalTheme

@Composable
fun ShopScreen() {
    val viewModel: ShopScreenViewModel = viewModel()
    val products by viewModel.products.collectAsState()
    val categories by viewModel.categories.collectAsState()
    Column(Modifier.fillMaxSize().padding(5.dp), verticalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Store", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(5.dp))
    LazyColumn(modifier = Modifier.padding(10.dp)) {
        item() {
            val featuredProducts = products.filter { it.featured }
            Row(modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)) {
                Icon(Icons.Default.Stars, contentDescription = null)
                Text("Featured")
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 5.dp),
                contentPadding = PaddingValues(horizontal = 5.dp)
            ) {
                items(featuredProducts.size, key = { featuredProducts[it].id }, itemContent = {
                    val product = featuredProducts[it]
                    VProductCard( product, { viewModel.addToCart(product) })
                })
            }
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp), thickness = 2.dp, color = Color.LightGray
            )
        }

        items(categories.size, key = { categories[it].id }) { index ->
            val category = categories[index]
            val productsInCategory = products.filter { it.category == category.category }
            Row(modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)) {
                Icon(Icons.Default.Folder, contentDescription = null)
                Text(text = category.category)
            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 5.dp),
                contentPadding = PaddingValues(horizontal = 5.dp)
            ) {
                items(productsInCategory.size, key = { productsInCategory[it].id }, itemContent = {
                    val product = productsInCategory[it]
                    HProductCard( product, { viewModel.addToCart(product) })
                })
            }
            if (index != categories.size - 1) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp), thickness = 2.dp, color = Color.LightGray
                )
            }
        }
    }
    }
}