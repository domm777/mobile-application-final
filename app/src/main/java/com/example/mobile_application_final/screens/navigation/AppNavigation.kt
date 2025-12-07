package com.example.mobile_application_final.screens.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.AirportShuttle
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobile_application_final.screens.*

sealed class Screen(val route: String, val title: String = "", val icon: ImageVector? = null) {
    // Auth / System Screens (No Bottom Bar)
    object Splash : Screen("splash")
    object Login : Screen("login")
    //object Register : Screen("register")

    // Main Tabs (Have Bottom Bar)
    object Shop : Screen("shop", "Shop", Icons.Default.ShoppingBasket)
    object Cart : Screen("cart", "Cart", Icons.Default.AddShoppingCart)
    object Orders : Screen("orders", "Orders", Icons.Default.AirportShuttle)
    object Account : Screen("account", "Account", Icons.Default.AccountCircle)
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val bottomBarScreens = listOf(
        Screen.Shop,
        Screen.Cart,
        Screen.Orders,
        Screen.Account
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // we are only showing the bottom bar if the current route is one in our nav bar
    // otherwise we're assuming it should be hidden
    val showBottomBar = bottomBarScreens.any { it.route == currentDestination?.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomBarScreens.forEach { screen ->
                        NavigationBarItem(
                            label = { Text(screen.title) },
                            icon = { Icon(screen.icon!!, contentDescription = screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    // Standard Bottom Navigation Logic
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // --- SPLASH & LOGIN ---
            composable(Screen.Splash.route) {
                SplashScreen(onTimeout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                })
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        // Navigate to Shop and clear Login from backstack
                        navController.navigate(Screen.Shop.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onCreateAccountClicked = { /* Handle Create Account */ }
                )
            }

            // --- MAIN TABS ---
            composable(Screen.Shop.route) { ShopScreen()}
            composable(Screen.Cart.route) { CartScreen() }
            composable(Screen.Orders.route) { OrderScreen(modifier = Modifier) }
            composable(Screen.Account.route) {
                AccountScreen(modifier = Modifier, onLogOut = {
                    // Log out logic: Go back to Login, clear everything
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true } // Clears entire stack
                    }
                })
            }
        }
    }
}