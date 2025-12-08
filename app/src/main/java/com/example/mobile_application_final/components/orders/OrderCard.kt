package com.example.mobile_application_final.components.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mobile_application_final.R
import com.example.mobile_application_final.data.models.OrderItem
import com.example.mobile_application_final.data.viewModels.OrderScreenViewModel

@Composable
fun OrderCard(order: OrderItem, orderScreenViewModel: OrderScreenViewModel, onDetailsClick: (OrderItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                stringResource(R.string.order_card_status, order.status),
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(R.string.order_card_tracking_label),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(order.tracking_number.toString(), style = MaterialTheme.typography.bodyMedium)
            }
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(R.string.order_card_date_placed_label),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(order.date_placed, style = MaterialTheme.typography.bodyMedium)
            }
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(R.string.order_card_estimated_delivery_label),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(order.estimated_date, style = MaterialTheme.typography.bodyMedium)
            }
            Column {
                order.items.forEach { item ->
                    OrderItemCard(item)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {
                    orderScreenViewModel.selectOrder(order)
                    onDetailsClick(order)
                }) {
                    Text(
                        stringResource(R.string.order_card_details_btn),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}