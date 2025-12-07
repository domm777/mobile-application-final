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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_application_final.R
import com.example.mobile_application_final.components.CartCard
import com.example.mobile_application_final.data.viewModels.CartScreenViewModel
import java.text.NumberFormat

@Composable
fun CartScreen() {
    val viewModel: CartScreenViewModel = viewModel()
    val products by viewModel.products.collectAsState()
    val currencyFormat = NumberFormat.getCurrencyInstance()

    Column(
        Modifier
            .fillMaxSize()
            .padding(5.dp), verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.cart_screen_title),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(5.dp))
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            if (viewModel.cartItems.isEmpty()) {
                Text(text = stringResource(R.string.empty_cart), style = MaterialTheme.typography.titleMedium)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(vertical = 5.dp),
                    contentPadding = PaddingValues(horizontal = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(viewModel.cartItems.size, key = { viewModel.cartItems[it].id }) { index ->
                        val cartItem = viewModel.cartItems[index]
                        val product = products.firstOrNull { it.id == cartItem.itemId }
                        if (product != null) {
                            CartCard(
                                cartItem,
                                product,
                                { viewModel.removeItem(cartItem) },
                                { viewModel.updateItem(cartItem, product, 1) },
                                { viewModel.updateItem(cartItem, product, -1) })
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(4.dp), Arrangement.SpaceBetween, Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp), text = stringResource(
                            R.string.cart_summary, currencyFormat.format(
                                viewModel.subtotal
                            ), currencyFormat.format(viewModel.tax), currencyFormat.format(
                                viewModel.total
                            )
                        )
                    )
                    Button(onClick = { viewModel.checkOut() }) {
                        Text(stringResource(R.string.checkout_screen_btn))
                    }
                }
            }

        }

    }
}
