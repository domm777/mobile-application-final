package com.example.mobile_application_final.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_application_final.components.orders.OrderCard
import com.example.mobile_application_final.data.viewModels.OrderScreenViewModel

@Composable
fun OrderScreen(modifier: Modifier) {
    val viewModel: OrderScreenViewModel = viewModel()
    val orders by viewModel.orders.collectAsState()
    Column(Modifier
        .fillMaxSize()
        .padding(6.dp), verticalArrangement = Arrangement.SpaceBetween) {

        Text(text = "Orders", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(5.dp))

        LazyColumn(modifier = Modifier.weight(1f).padding(10.dp)) {
            items(orders.size, key = { orders[it].id }) { index ->
                val order = orders[index]
                OrderCard(order)
                if (index != orders.size - 1) {
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