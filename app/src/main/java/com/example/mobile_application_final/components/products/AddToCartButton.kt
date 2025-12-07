package com.example.mobile_application_final.components.products

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.mobile_application_final.R
import com.example.mobile_application_final.data.models.Product


@Composable
fun AddToCartButton(
    product: Product,
    addToCart: () -> Unit,
) {
    Button(
        enabled = product.isInStock(),
        modifier = Modifier.fillMaxWidth(),
        onClick = addToCart
    ) {
        Text(
            if (product.isInStock()) {
                stringResource(R.string.add_to_cart)
            } else {
                stringResource(R.string.out_of_stock)
            }
        )
    }
}
