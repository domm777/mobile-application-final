package com.example.mobile_application_final.screens.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobile_application_final.screens.AccountScreen
import com.example.mobile_application_final.screens.CartScreen
import com.example.mobile_application_final.screens.OrderScreen
import com.example.mobile_application_final.screens.ShopScreen

@Composable
fun NavGraph(navController: NavController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController as NavHostController, startDestination = "shop"
    ) {
        composable( "shop") { ShopScreen(modifier = Modifier.padding(innerPadding))}
        composable( "cart") { CartScreen(modifier = Modifier.padding(innerPadding)) }
        composable( "orders") { OrderScreen(modifier = Modifier.padding(innerPadding)) }
        composable( "account") { AccountScreen(modifier = Modifier.padding(innerPadding)) }
    }
}