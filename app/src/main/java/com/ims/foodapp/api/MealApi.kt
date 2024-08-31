package com.ims.foodapp.api

import com.ims.foodapp.data.Resource
import com.ims.foodapp.model.Meal
import com.ims.foodapp.model.MealX
import com.ims.foodapp.model.MealXX
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("filter.php?")
    suspend fun getMealsByName(@Query("c") mealName:String):Meal

    @GET("lookup.php?")
    suspend fun getMealsById(@Query("i") mealId:String):MealXX
}