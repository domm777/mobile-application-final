package com.example.mobile_application_final.screens.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.AirportShuttle
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobile_application_final.R
import com.example.mobile_application_final.data.viewModels.OrderScreenViewModel
import com.example.mobile_application_final.data.viewModels.ThemeViewModel
import com.example.mobile_application_final.screens.CartScreen
import com.example.mobile_application_final.screens.CreateAccountScreen
import com.example.mobile_application_final.screens.LoginScreen
import com.example.mobile_application_final.screens.OrderDetailsScreen
import com.example.mobile_application_final.screens.OrderScreen
import com.example.mobile_application_final.screens.ShopScreen
import com.example.mobile_application_final.screens.SplashScreen
import com.example.mobile_application_final.screens.account.AccountScreen
import com.google.firebase.auth.FirebaseAuth

sealed class Screen(
    val route: String,
    val titleResourceId: Int? = null,
    val icon: ImageVector? = null,
) {
    // Auth / System Screens (No Bottom Bar)
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")

    // Main Tabs (Have Bottom Bar)
    object Shop : Screen("shop", R.string.store_screen_title, Icons.Default.ShoppingBasket)
    object Cart : Screen("cart", R.string.cart_screen_title, Icons.Default.AddShoppingCart)
    object Orders : Screen("orders", R.string.order_screen_title, Icons.Default.AirportShuttle)
    object Account : Screen("account", R.string.account_screen_title, Icons.Default.AccountCircle)

    object OrderDetails: Screen("order_details",R.string.order_screen_title, Icons.Default.AirportShuttle)
}

@Composable
fun AppNavigation(themeModel: ThemeViewModel? = null) {
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

    val sharedOrder: OrderScreenViewModel = viewModel()

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNav(bottomBarScreens, currentDestination, navController)
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
                    onCreateAccountClicked = {
                        navController.navigate(Screen.Register.route)
                    }
                )
            }

            composable(Screen.Register.route) {
                CreateAccountScreen(onAccountCreated = {
                    navController.navigate(Screen.Shop.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                })
            }

            // --- MAIN TABS ---
            composable(Screen.Shop.route) { ShopScreen() }
            composable(Screen.Cart.route) { CartScreen() }
            composable(Screen.Orders.route) { OrderScreen(modifier = Modifier, sharedOrder, {
                navController.navigate(Screen.OrderDetails.route) {
            }}) }
            composable(Screen.OrderDetails.route) { OrderDetailsScreen(modifier = Modifier, sharedOrder) }
            composable(Screen.Account.route) {
                AccountScreen(modifier = Modifier, themeModel, onLogOut = {
                    // Log out logic: Go back to Login, clear everything
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true } // Clears entire stack
                    }

                    FirebaseAuth.getInstance().signOut()
                })
            }
        }
    }
}

@Composable
fun BottomNav(
    bottomBarScreens: List<Screen>,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    NavigationBar {
        bottomBarScreens.forEach { screen ->
            val title =
                if (screen.titleResourceId != null) stringResource(screen.titleResourceId) else ""

            NavigationBarItem(
                label = { Text(title) },
                icon = { Icon(screen.icon!!, contentDescription = title) },
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