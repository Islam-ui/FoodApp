package com.ims.foodapp.common

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ims.foodapp.navigation.Screens
import com.ims.foodapp.screens.FireViewModel


@Composable
fun NavAppBar(navController: NavController, fireViewModel: FireViewModel) {
    val destination = navController.currentBackStackEntry?.destination
    BottomAppBar(modifier = Modifier.height(110.dp)) {
        NavigationBar {
            NavigationBarItem(
                selected = destination?.route == Screens.Main.name,
                onClick = { navController.navigate(Screens.Main.name) },
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                label = { Text(text = "Home") })

            NavigationBarItem(
                selected = destination?.route == Screens.Cart.name,
                onClick = {
                    navController.navigate(Screens.Cart.name)
                    fireViewModel.getMyCart()
                    fireViewModel.getMyOrders()
                          },
                icon = { Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null) },
                label = { Text(text = "My cart") })
        }
    }
}

