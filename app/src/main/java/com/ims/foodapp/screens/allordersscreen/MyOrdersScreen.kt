package com.ims.foodapp.screens.allordersscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ims.foodapp.common.MyOrderCard
import com.ims.foodapp.common.LoadingSpinner
import com.ims.foodapp.screens.FireViewModel

@Composable
fun MyOrdersScreen(navController: NavController, fireViewModel: FireViewModel) {

        val myOrders = fireViewModel.myOrders.value?.sortedByDescending { it.time }
        if (fireViewModel.orderLoading.value) {
            LoadingSpinner()
        } else if (myOrders.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "No orders")
            }
        } else {
            LazyColumn(modifier = Modifier
                .fillMaxSize()) {
                items(myOrders) { order ->
                    MyOrderCard(order)
                }
            }
        }
}
