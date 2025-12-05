package com.example.mobile_application_final.screens.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.mobile_application_final.R

@Composable
fun BottomNavigationBar() {
    val items = listOf(
        "shop", "cart", "orders", "account"
    )
    // You can then use these strings in your Text Composables, for example:
    // items.forEach { screen -> Text(text = screen) }

    val icons = listOf(Icons.Default.ShoppingCart, Icons.Default.ShoppingCart, Icons.Default.ShoppingCart, Icons.Default.ShoppingCart)
    val labels = listOf(
        Text(stringResource(id = R.string.bottom_nav_shop)),
        Text(stringResource(id = R.string.bottom_nav_cart)),
        Text(stringResource(id = R.string.bottom_nav_orders)),
        Text(stringResource(id = R.string.bottom_nav_account))
    )

//    NavigationBar{
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//
//        items.forEach {
//            NavigationBarItem(
//                selected = currentRoute == items,
//                onClick = TODO(),
//                icon = TODO(),
//                modifier = Modifier,
//                enabled = TODO(),
//                label = TODO(),
//                alwaysShowLabel = TODO(),
//                colors = TODO(),
//                interactionSource = TODO(),
//            )
//        }
//    }
}