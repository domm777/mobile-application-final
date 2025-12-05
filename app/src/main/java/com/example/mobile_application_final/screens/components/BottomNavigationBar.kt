package com.example.mobile_application_final.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf("shop", "cart", "orders", "account")
    // You can then use these strings in your Text Composables, for example:
    // items.forEach { screen -> Text(text = screen) }

    val icons = listOf(
        Icons.Default.ShoppingCart,
        Icons.Default.ShoppingCart, // Assuming you have a specific cart icon, you can change this
        Icons.Default.ShoppingCart,
        Icons.Default.AccountCircle
    )
    val labels = listOf("Shop", "Cart", "Orders", "Account")

    NavigationBar{
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEachIndexed { index, item ->
            val label = labels[index]
            NavigationBarItem(selected = currentRoute == item,
                onClick = { navController.navigate(item) },
                icon = { Icon(icons[index], contentDescription = label) },
                label = { Text(label) })
        }
    }
}