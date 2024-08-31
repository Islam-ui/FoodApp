package com.ims.foodapp.screens.orderScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ims.foodapp.R
import com.ims.foodapp.common.CommonButton
import com.ims.foodapp.common.LoadingSpinner
import com.ims.foodapp.model.MealX
import com.ims.foodapp.screens.FireViewModel
import com.ims.foodapp.screens.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    fireViewModel: FireViewModel,
    mealId: String
) {

    val checkChicken = mainViewModel.chicken.collectAsState().value?.meals?.find { it.idMeal == mealId }
    val checkBeef = mainViewModel.beef.collectAsState().value?.meals?.find { it.idMeal == mealId }
    val checkLamb = mainViewModel.lamb.collectAsState().value?.meals?.find { it.idMeal == mealId }
    val checkMiscellaneous = mainViewModel.miscellaneous.collectAsState().value?.meals?.find { it.idMeal == mealId }
    val checkPasta = mainViewModel.pasta.collectAsState().value?.meals?.find { it.idMeal == mealId }

    val selectedMeal = checkChicken?:checkBeef?:checkLamb?:checkMiscellaneous?:checkPasta

    val mealSize = remember { mutableStateOf("Medium") }

    val mealCount = remember { mutableStateOf(1) }

    val mealPrice = remember { mutableStateOf(10) }


    if (mealCount.value <= 0){
        mealCount.value = 1
    }

    if (mealCount.value >= 20){
        mealCount.value = 20
    }

    Scaffold(topBar = { TopAppBar(title = { Text(text = "Order", fontSize = 30.sp, fontWeight = FontWeight.Bold) }) }) {
        if (selectedMeal == null){
            LoadingSpinner()
        }else {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OrderContent(meal = selectedMeal, mealSize = mealSize, mealCount = mealCount,
                    mealPrice = mealPrice, fireViewModel, navController)

            }
        }
    }
}

fun multiply(num1:Int, num2:Int):Int = num1 * num2

@Composable
fun OrderContent(meal: MealX, mealSize: MutableState<String>,
                 mealCount: MutableState<Int>, mealPrice: MutableState<Int>,
                 fireViewModel: FireViewModel, navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = meal.strMeal, fontSize = 30.sp, fontWeight = FontWeight.SemiBold, maxLines = 2,
            minLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(horizontal = 4.dp))

        Spacer(modifier = Modifier.height(50.dp))
        
        MealCard(meal)

        Spacer(modifier = Modifier.height(50.dp))
        
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically){

            CommonButton(mealSize = mealSize, size = 55.dp, value = "Small", text = "S") {
                mealSize.value = "Small"
                mealPrice.value = 5
            }

            CommonButton(mealSize = mealSize, size = 55.dp, value = "Medium", text = "M") {
                mealSize.value = "Medium"
                mealPrice.value = 10
            }

            CommonButton(mealSize = mealSize, size = 55.dp, value = "Large", text = "L") {
                mealSize.value = "Large"
                mealPrice.value = 15
            }

        }

        Spacer(modifier = Modifier.height(40.dp))

        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
            CommonButton(icon = Icons.Default.Remove, text = "Minus", size = 50.dp, value = "Minus", mealSize = mealSize) {
                mealCount.value--
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = mealCount.value.toString(), fontSize = 25.sp)
            Spacer(modifier = Modifier.width(10.dp))
            CommonButton(icon = Icons.Default.Add, text = "Add", size = 50.dp, value = "Add", mealSize = mealSize) {
                mealCount.value++
            }
        }

        Spacer(modifier = Modifier.height(80.dp))

        Row (modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){
            Spacer(modifier = Modifier.width(20.dp))

            Text(text = "$" + multiply(mealPrice.value, mealCount.value).toString(), fontSize = 25.sp,
                modifier = Modifier.defaultMinSize(minWidth = 60.dp))

            Button(
                onClick = {
                         fireViewModel.addToCart(meal.strMeal, mealCount.value.toString(),
                             multiply(mealPrice.value, mealCount.value).toString(), meal.strMealThumb, mealSize.value)
                         navController.popBackStack()
                },
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .height(70.dp),
                shape = RoundedCornerShape(12),
                colors = ButtonDefaults.buttonColors(Color(0xFFFF5722))
            ) {
                Text(text = "Order now", fontSize = 22.sp)
                Spacer(modifier = Modifier.width(60.dp))
                Icon(painter = painterResource(id = R.drawable.baseline_shopping_cart), contentDescription = null)
            }
        }


    }
}

@Composable
fun MealCard(meal: MealX) {
    Box(){

        Column(modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom) {
        Card(modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .height(200.dp),
            shape = RoundedCornerShape(12),
            elevation = CardDefaults.cardElevation(16.dp),
            colors = CardDefaults.cardColors(Color.White)) {

        }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {

        Card(shape = CircleShape, modifier = Modifier.size(250.dp),
            colors = CardDefaults.cardColors(Color.Transparent)) {
            AsyncImage(model = meal.strMealThumb, contentDescription = null,
                modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            }

        }

    }
}

