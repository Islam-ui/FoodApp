package com.ims.foodapp.screens

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ims.foodapp.model.Meal
import com.ims.foodapp.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MealRepository):ViewModel() {
    private val _chicken = MutableStateFlow<Meal?>(null)
    val chicken = _chicken.asStateFlow()
    private val _beef = MutableStateFlow<Meal?>(null)
    val beef = _beef.asStateFlow()
    private val _lamb = MutableStateFlow<Meal?>(null)
    val lamb = _lamb.asStateFlow()
    private val _miscellaneous = MutableStateFlow<Meal?>(null)
    val miscellaneous = _miscellaneous.asStateFlow()
    private val _pasta = MutableStateFlow<Meal?>(null)
    val pasta = _pasta.asStateFlow()


    init {
        getChicken()
        getBeef()
        getLamb()
        getMiscellaneous()
        getPasta()

    }


    private fun getChicken(mealName:String = "chicken"){
        viewModelScope.launch {
            try {
                _chicken.value = repository.getMealsByName(mealName)
            }catch (e:Exception){
                Log.d("Failed", "getMealsByName: ")
                _chicken.value = null
            }
        }
    }

    private fun getBeef(mealName:String = "beef"){
        viewModelScope.launch {
            try {
                _beef.value = repository.getMealsByName(mealName)
            }catch (e:Exception){
                Log.d("Failed", "getMealsByName: ")
                _beef.value = null
            }
        }
    }

    private fun getLamb(mealName:String = "lamb"){
        viewModelScope.launch {
            try {
                _lamb.value = repository.getMealsByName(mealName)
            }catch (e:Exception){
                Log.d("Failed", "getMealsByName: ")
                _lamb.value = null
            }
        }
    }

    private fun getMiscellaneous(mealName:String = "miscellaneous"){
        viewModelScope.launch {
            try {
                _miscellaneous.value = repository.getMealsByName(mealName)
            }catch (e:Exception){
                Log.d("Failed", "getMealsByName: ")
                _miscellaneous.value = null
            }
        }
    }

    private fun getPasta(mealName:String = "pasta"){
        viewModelScope.launch {
            try {
                _pasta.value = repository.getMealsByName(mealName)
            }catch (e:Exception){
                Log.d("Failed", "getMealsByName: ")
                _pasta.value = null
            }
        }
    }
}