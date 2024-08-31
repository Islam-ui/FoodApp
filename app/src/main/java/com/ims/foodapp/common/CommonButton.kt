package com.ims.foodapp.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CommonButton(icon:ImageVector? = null, text:String,size:Dp, value:String? = null, mealSize:MutableState<String>, onClick:()->Unit) {
    Card(shape = CircleShape, modifier = Modifier
        .size(size)
        .clickable { onClick.invoke() },
        colors = CardDefaults.cardColors(if (mealSize.value == value) Color(0xffffd100) else Color.White),
        elevation = CardDefaults.cardElevation(6.dp)) {
        Column (modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            if (icon != null){
                Icon(imageVector = icon, contentDescription = null)
            }else {
                Text(text = text, fontSize = 15.sp)
            }
        }
    }
}