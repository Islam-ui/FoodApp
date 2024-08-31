package com.ims.foodapp.screens.mainscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ims.foodapp.R
import com.ims.foodapp.common.ListCard
import com.ims.foodapp.common.LoadingSpinner
import com.ims.foodapp.common.NavAppBar
import com.ims.foodapp.model.MealX
import com.ims.foodapp.navigation.Screens
import com.ims.foodapp.screens.FireViewModel
import com.ims.foodapp.screens.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel, fireViewModel: FireViewModel) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { Tabs.entries.size }
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    Scaffold (topBar = { TopAppBar(title = { Text(text = "Food App",
        fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 4.dp)) },
        actions = { 
            Text(text = fireViewModel.userData.value?.username?:"")
            
            TextButton(onClick = {
            fireViewModel.signout()
        }) {
            Text(text = "Sign out")
        }}) },
        bottomBar = {NavAppBar(navController = navController, fireViewModel = fireViewModel) }){padd->
        Column(modifier = Modifier.padding(padd)) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tabs.entries.forEachIndexed { index, currentTab ->
                    Tab(selected = selectedTabIndex.value == index,
                        selectedContentColor = Color.Unspecified,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(currentTab.ordinal)
                            }
                        },
                        text = { if(selectedTabIndex.value == index) Text(text = currentTab.text, fontWeight = FontWeight.ExtraBold)
                        else Text(text = currentTab.text) },
                        icon = {
                            if(selectedTabIndex.value == index) Icon(
                                painter = painterResource(id = currentTab.selectedIcon),
                                contentDescription = null,
                                modifier = Modifier.size(55.dp)
                            )
                            else Icon(painter = painterResource(id = currentTab.unselectedIcon), contentDescription = null,
                                modifier = Modifier.size(40.dp))
                        })

                }
            }

            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) {

                when (Tabs.entries[selectedTabIndex.value].name) {
                    "Chicken" -> MainContent(meals = mainViewModel.chicken.collectAsState().value?.meals,navController)
                    "Beef" -> MainContent(meals = mainViewModel.beef.collectAsState().value?.meals,navController)
                    "Lamb" -> MainContent(meals = mainViewModel.lamb.collectAsState().value?.meals,navController)
                    "Miscellaneous" -> MainContent(meals = mainViewModel.miscellaneous.collectAsState().value?.meals,navController)
                    "Pasta" -> MainContent(meals = mainViewModel.pasta.collectAsState().value?.meals,navController)
                }
            }

        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainContent(meals: List<MealX>?, navController: NavController) {
    val scope = rememberCoroutineScope()

    if (meals.isNullOrEmpty()) {
            LoadingSpinner()
        scope.launch {
            delay(2000L)
        }
    }else {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()) {
            items(meals) { meal ->
                ListCard(meal = meal){
                    navController.navigate(Screens.Order.name + "/${meal.idMeal}")
                }
            }
        }
    }
}

enum class Tabs(
    val selectedIcon:Int,
    val unselectedIcon:Int,
    val text:String
){
    Chicken(R.drawable.chicken1, R.drawable.chicken0, "Chicken"),
    Beef(R.drawable.beef1, R.drawable.beef0, "Beef"),
    Lamb(R.drawable.lamb1, R.drawable.lamb0, "Lamb"),
    Miscellaneous(R.drawable.miscellaneous1, R.drawable.miscellaneous0, "Miscellaneous"),
    Pasta(R.drawable.pasta1, R.drawable.pasta0, "Pasta")
}