package com.example.mobile_application_final.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobile_application_final.screens.ShopScreen
import com.example.mobile_application_final.screens.LoginScreen
import com.example.mobile_application_final.screens.SplashScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "splash_screen",
        modifier = modifier
    ) {
        composable("splash_screen") {
            SplashScreen(onTimeout = {
                navController.navigate("login_screen") {
                    popUpTo("splash_screen") { inclusive = true }
                }
            })
        }
        composable("login_screen") {
            LoginScreen(onLoginSuccess = {
                navController.navigate("home_screen") {
                    popUpTo("login_screen") { inclusive = true }
                }
            })
        }
        composable("shop_screen") {
            ShopScreen()
        }


//    var showSplash by remember { mutableStateOf(true) }
//    if (showSplash) {
//        SplashScreen(onTimeout = { showSplash = false })
//    } else {
//        LoginScreen()
//    }
    }
}