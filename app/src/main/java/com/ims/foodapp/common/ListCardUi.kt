package com.ims.foodapp.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ims.foodapp.R
import com.ims.foodapp.model.MealX

@Composable
fun ListCard(meal: MealX, onClick:()->Unit) {
    Surface(modifier = Modifier
        .padding(8.dp)
        .height(230.dp)
        .width(170.dp).clickable { onClick.invoke() },
        shape = RoundedCornerShape(12),
        color = Color.Transparent){
        Box (modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Card(modifier = Modifier
                    .padding(8.dp)
                    .height(160.dp)
                    .width(180.dp),
                    shape = RoundedCornerShape(10),
                    elevation = CardDefaults.cardElevation(10.dp),
                    colors = CardDefaults.cardColors(Color.White)
                ) {
                    Spacer(modifier = Modifier.height(70.dp))
                    Text(text = meal.strMeal, modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 8.dp),
                        fontWeight = FontWeight.Bold, maxLines = 2, minLines = 2,
                        overflow = TextOverflow.Ellipsis)

                    Row (modifier = Modifier.padding(start = 8.dp)){
                        Icon(painter = painterResource(id = R.drawable.available), contentDescription = null,
                            modifier = Modifier.size(20.dp), tint = Color.Green.copy(blue = 0.1f))
                        Text(text = "Available", color = Color.Green.copy(blue = 0.1f))
                    }
                }
            }
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(model= meal.strMealThumb, contentDescription = null,
                    modifier = Modifier
                        .size(120.dp).clip(CircleShape),
                    contentScale = ContentScale.Fit)
            }
        }

    }
}
