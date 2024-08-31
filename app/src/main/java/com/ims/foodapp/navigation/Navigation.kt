package com.ims.foodapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ims.foodapp.screens.FireViewModel
import com.ims.foodapp.screens.MainViewModel
import com.ims.foodapp.screens.allordersscreen.MyCart
import com.ims.foodapp.screens.authscreens.LogInScreen
import com.ims.foodapp.screens.authscreens.SignUpScreen
import com.ims.foodapp.screens.mainscreen.MainScreen
import com.ims.foodapp.screens.orderScreen.OrderScreen

enum class Screens{
    Main,
    Order,
    Cart,
    Login,
    Signup
}

@Composable
fun NavAppHost(navController: NavHostController = rememberNavController()) {
    val fireViewModel:FireViewModel = hiltViewModel()

    val mainViewModel:MainViewModel = hiltViewModel()

    NavHost(navController = navController,
        startDestination = if (fireViewModel.signedIn.value) Screens.Main.name else Screens.Signup.name) {
        composable(Screens.Main.name){ MainScreen(navController, mainViewModel, fireViewModel)}
        composable(Screens.Order.name + "/{mealId}",
            arguments = listOf(navArgument("mealId"){
                type = NavType.StringType
            })
        ) { navBack->
            navBack.arguments?.getString("mealId")?.let { mealId->
            OrderScreen(navController = navController,mainViewModel, fireViewModel, mealId = mealId)
            }
        }
        composable(Screens.Cart.name){ MyCart(navController = navController, fireViewModel) }

        composable(Screens.Login.name){ LogInScreen(navController = navController, fireViewModel = fireViewModel)}
        composable(Screens.Signup.name){ SignUpScreen(navController = navController, fireViewModel = fireViewModel)}

    }
}