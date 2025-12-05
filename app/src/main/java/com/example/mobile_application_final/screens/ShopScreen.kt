package com.example.mobile_application_final.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_application_final.components.HProductCard
import com.example.mobile_application_final.data.viewModels.ShopScreenViewModel
import com.example.mobile_application_final.ui.theme.MobileapplicationfinalTheme

@Composable
fun ShopScreen(modifier: Modifier) {
    val viewModel: ShopScreenViewModel = viewModel()
    val products by viewModel.products.collectAsState()
    val categories by viewModel.categories.collectAsState()

    LazyColumn(modifier = Modifier.padding(10.dp)) {
        //create a list of categories
        items(categories.size, key = { categories[it].id }, itemContent = {
            val category = categories[it]
            val productsInCategory = products.filter { it.category == category.category }
            Row(modifier = Modifier.padding(0.dp)) {
                Icon(Icons.Default.Folder, contentDescription = null)
                Text(text = category.category)
            }
            LazyRow(modifier = Modifier.padding(0.dp)) {
                items(productsInCategory.size, key = { productsInCategory[it].id }, itemContent = {
                    val product = productsInCategory[it]
                    HProductCard(modifier, product)
                })
            }
        })
    }

    //Button(onClick = {viewModel.seedDbBtn()}) {Text(text = "Seed Database")}
}

@Preview(showBackground = true)
@Composable
fun ShopScreenPreview() {
    MobileapplicationfinalTheme {
        ShopScreen(Modifier)
    }
}