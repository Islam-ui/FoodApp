package com.ims.foodapp.model

import androidx.compose.runtime.Immutable

@Immutable
data class Order(
    val userId: String? = null,
    val orderId: String? = null,
    val mealName: String? = null,
    val numOfMeals: String? = null,
    val price: String? = null,
    val mealImg: String? = null,
    val mealSize: String? = null,
    val time: Long? = null,
    val orderState: String? = null,
    val location:String? = null,
    val phoneNumber:String? = null,
    val username:String? = null
    )
