package com.ims.foodapp.screens.allordersscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ims.foodapp.common.LoadingSpinner
import com.ims.foodapp.common.NavAppBar
import com.ims.foodapp.common.MyCartCard
import com.ims.foodapp.screens.FireViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MyCart(navController: NavController, fireViewModel: FireViewModel) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState {OrderTabs.entries.size}
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage }}

    Scaffold(topBar = { TopAppBar(title = { Text(text = "My cart", fontSize = 30.sp, fontWeight = FontWeight.Bold)  },
        actions = {
            when (OrderTabs.entries[selectedTabIndex.value].text) {
            "My cart" -> OutlinedButton(shape = RoundedCornerShape(16), border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary),
                onClick = {
                          fireViewModel.myCart.value?.let { orders ->
                              orders.forEach {
                                  fireViewModel.order(it.orderId.toString())
                              }
                          }
            }, modifier = Modifier.padding(end = 12.dp)) {
                Text(text = "Confirm all Orders")
            }
            "My orders" -> {}
        }})},
        bottomBar = { NavAppBar(navController = navController, fireViewModel = fireViewModel) }) { padd ->
        Column (modifier = Modifier.padding(padd)){
        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier.fillMaxWidth()
        ) {

            OrderTabs.entries.forEachIndexed { index, currentsTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentsTab.ordinal)
                        }
                    },
                    text = { Text(text = currentsTab.text, fontSize = 20.sp) }
                )
            }
        }

        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) {
            when (OrderTabs.entries[selectedTabIndex.value].text) {
                "My cart" -> CartScreen(fireViewModel = fireViewModel, navController = navController)
                "My orders" -> MyOrdersScreen(fireViewModel = fireViewModel, navController = navController)
            }
        }

    }
    }
}

@Composable
fun CartScreen(fireViewModel: FireViewModel, navController: NavController) {
        val orders = fireViewModel.myCart.value?.sortedByDescending { it.time }
        if (fireViewModel.orderLoading.value) {
            LoadingSpinner()
        } else if (orders.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "The cart is empty")
            }
        } else {
            LazyColumn(modifier = Modifier
                .fillMaxSize()) {
                items(orders) { order ->
                    MyCartCard(order){
                        fireViewModel.removeFromCart(orderId = order.orderId.toString())
                    }
                }
            }
        }
    }


enum class OrderTabs(val text:String){
    Orders("My cart"),
    FinishedOrders("My orders")
}