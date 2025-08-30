package com.example.vegify.ui.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vegify.MealRepository
import com.example.vegify.ui.homepageScreen.Meal
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.vegify.NutritionResponse
import com.example.vegify.NutritionService

class MealViewModel : ViewModel() {

    private val repository = MealRepository()
    private val _meal = MutableLiveData<Meal?>()
    val meal: LiveData<Meal?> = _meal

    fun fetchMealById(mealId: String) {
        viewModelScope.launch {
            _meal.value = repository.getMealById(mealId)
        }
    }

    fun fetchMealByName(mealName: String) {
        viewModelScope.launch {
            _meal.value = repository.getMealByName(mealName)
        }
    }
}

class NutritionViewModel : ViewModel() {
    private val _mealsByCategory = mutableStateMapOf<String, MutableList<String>>()
    val mealsByCategory: Map<String, List<String>> get() = _mealsByCategory

    private val _totalNutrition = mutableStateOf(
        NutritionResponse(
            calories = 0f,
            protein = 0f,
            fat = 0f,
            carbohydrates = 0f,
            fiber = 0f,
            sugar = 0f,
            sodium = 0f,
            calcium = 0f,
            iron = 0f
        )
    )
    val totalNutrition: NutritionResponse get() = _totalNutrition.value

    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading.value

    fun addMeal(category: String, mealName: String) {
        val list = _mealsByCategory.getOrPut(category) { mutableListOf() }
        if (!list.contains(mealName)) {
            list.add(mealName)
            // Force state update
            _mealsByCategory[category] = list.toMutableList()
            
            // Fetch and update nutritional information
            viewModelScope.launch {
                try {
                    _isLoading.value = true
                    val nutritionInfo = NutritionService.getNutritionalInfo(mealName)
                    updateNutritionInfo(nutritionInfo, true)
                } catch (e: Exception) {
                    Log.e("NutritionViewModel", "Error fetching nutrition info: ${e.message}")
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun updateNutritionInfo(nutritionInfo: NutritionResponse, isAdding: Boolean) {
        val multiplier = if (isAdding) 1f else -1f
        val newTotal = NutritionResponse(
            calories = _totalNutrition.value.calories + (nutritionInfo.calories * multiplier),
            protein = _totalNutrition.value.protein + (nutritionInfo.protein * multiplier),
            fat = _totalNutrition.value.fat + (nutritionInfo.fat * multiplier),
            carbohydrates = _totalNutrition.value.carbohydrates + (nutritionInfo.carbohydrates * multiplier),
            fiber = _totalNutrition.value.fiber + (nutritionInfo.fiber * multiplier),
            sugar = _totalNutrition.value.sugar + (nutritionInfo.sugar * multiplier),
            sodium = _totalNutrition.value.sodium + (nutritionInfo.sodium * multiplier),
            calcium = _totalNutrition.value.calcium + (nutritionInfo.calcium * multiplier),
            iron = _totalNutrition.value.iron + (nutritionInfo.iron * multiplier)
        )
        _totalNutrition.value = newTotal
    }

    fun removeMeal(category: String, mealName: String) {
        val list = _mealsByCategory[category]
        list?.let {
            it.remove(mealName)
            if (it.isEmpty()) {
                _mealsByCategory.remove(category)
            } else {
                // Force state update
                _mealsByCategory[category] = it.toMutableList()
            }
            
            // Update nutritional information when removing a meal
            viewModelScope.launch {
                try {
                    _isLoading.value = true
                    val nutritionInfo = NutritionService.getNutritionalInfo(mealName)
                    updateNutritionInfo(nutritionInfo, false)
                } catch (e: Exception) {
                    Log.e("NutritionViewModel", "Error fetching nutrition info: ${e.message}")
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun clearMeals() {
        _mealsByCategory.clear()
        _totalNutrition.value = NutritionResponse(
            calories = 0f,
            protein = 0f,
            fat = 0f,
            carbohydrates = 0f,
            fiber = 0f,
            sugar = 0f,
            sodium = 0f,
            calcium = 0f,
            iron = 0f
        )
    }
}
