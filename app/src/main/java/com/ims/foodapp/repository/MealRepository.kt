package com.ims.foodapp.repository

import com.ims.foodapp.api.MealApi
import com.ims.foodapp.data.Resource
import com.ims.foodapp.model.Meal
import com.ims.foodapp.model.MealX
import com.ims.foodapp.model.MealXX
import retrofit2.http.Query
import javax.inject.Inject

class MealRepository @Inject constructor(private val api:MealApi) {

    suspend fun getMealsByName(mealName:String): Meal{
        return api.getMealsByName(mealName)
    }

    suspend fun getMealsById(mealId:String): MealXX {
        return api.getMealsById(mealId)
    }
}