package com.example.mobile_application_final.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_application_final.R
import com.example.mobile_application_final.components.orders.OrderItemCard
import com.example.mobile_application_final.components.products.HProductCard
import com.example.mobile_application_final.data.viewModels.OrderDetailsScreenViewModel
import com.example.mobile_application_final.data.viewModels.OrderScreenViewModel
import java.text.NumberFormat

@Composable
fun OrderDetailsScreen(modifier: Modifier, viewModel: OrderScreenViewModel) {
    val order by viewModel.selectedOrder.collectAsState()

    val currencyFormatter = NumberFormat.getCurrencyInstance()
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("order_screen"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.status, order?.status ?: ""),
                style = MaterialTheme.typography.headlineMedium
            )

            LinearProgressIndicator(
                // .1f   .5
                progress = { .50f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
                color = ProgressIndicatorDefaults.linearColor,
                trackColor = ProgressIndicatorDefaults.linearTrackColor,
                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap
            )
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(stringResource(R.string.order_recieved))
                Box(Modifier.height(48.dp), contentAlignment = Alignment.Center) {Text("En Route") }
                Box(Modifier.height(48.dp), contentAlignment = Alignment.Center) {Text("Delivered") }
            }
            Spacer(Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(stringResource(R.string.tracking_number))
                Text(order?.tracking_number.toString())
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(stringResource(R.string.date_ordered))
                Text(order?.date_placed.toString())
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(stringResource(R.string.estimated_delivery))
                Text(order?.estimated_date.toString())
            }
            Spacer(Modifier.height(24.dp))

            Text(
                stringResource(R.string.order_items),
                style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth().weight(1f)
            ) {
                items(order?.items ?: emptyList()) { product ->
                    OrderItemCard(product)
                }
            }

            Spacer(Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    stringResource(R.string.subtotal),
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(currencyFormatter.format(order?.subtotal),
                    style = MaterialTheme.typography.titleLarge,)
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    stringResource(R.string.taxes),
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(currencyFormatter.format(order?.tax),
                    style = MaterialTheme.typography.titleLarge,)
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    stringResource(R.string.total),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold)
                Text(currencyFormatter.format(order?.total),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(24.dp))

            Text(
                stringResource(R.string.delivery_address),
                style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            Column(Modifier
                .padding(4.dp)
                .fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                Text(order?.user_id.toString())
                Text(order?.address.toString())
            }
        }
    }
}
