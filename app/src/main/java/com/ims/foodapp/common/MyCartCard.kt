package com.ims.foodapp.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ims.foodapp.model.Order


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyCartCard(order: Order, onRemove:()->Unit = {}) {

    Box(modifier = Modifier.combinedClickable(onLongClick = {onRemove.invoke()}){ }){
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center) {
            Card(modifier = Modifier
                .padding(12.dp)
                .width(300.dp)
                .height(150.dp),
                shape = RoundedCornerShape(12),
                elevation = CardDefaults.cardElevation(16.dp),
                colors = CardDefaults.cardColors(Color.White)) {
            }
        }

        Row {

            Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)) {
                Spacer(modifier = Modifier.width(100.dp))
                Card(
                    shape = CircleShape, modifier = Modifier
                        .padding(start = 20.dp)
                        .size(130.dp),
                    colors = CardDefaults.cardColors(Color.Transparent)
                ) {
                    AsyncImage(model = order.mealImg, contentDescription = null,
                        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                }
            }
            Column (modifier= Modifier
                .height(150.dp)
                .padding(10.dp), verticalArrangement = Arrangement.Center){
                Text(text = order.mealName?:"No Name", modifier= Modifier.padding(end = 8.dp),
                    fontSize = 20.sp, maxLines = 2, minLines = 2, overflow = TextOverflow.Ellipsis)

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Text(text = "x${order.numOfMeals}", fontSize = 15.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = order.mealSize?:"", fontSize = 15.sp, color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Total Price: $${order.price}", fontSize = 15.sp, color = Color.Gray)
            }
        }
    }
}