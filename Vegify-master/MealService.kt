package com.example.vegify

import androidx.compose.runtime.mutableStateListOf
import com.example.vegify.ui.components.MealCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Meal(
    val name: String,
    val category: MealCategory,
    val nutritionInfo: NutritionResponse
)

object MealService {
    private val _meals = mutableStateListOf<Meal>()
    private val _mealsFlow = MutableStateFlow(_meals.toList())
    val mealsFlow: StateFlow<List<Meal>> = _mealsFlow.asStateFlow()

    suspend fun addMeal(name: String, category: MealCategory) {
        val nutritionInfo = NutritionService.getNutritionalInfo(name)
        _meals.add(Meal(name, category, nutritionInfo))
        _mealsFlow.value = _meals.toList()
    }

    fun removeMeal(name: String) {
        _meals.removeAll { it.name == name }
        _mealsFlow.value = _meals.toList()
    }

    fun getMealsByCategory(category: MealCategory): List<Meal> {
        return _meals.filter { it.category == category }
    }

    fun getAllMeals(): List<Meal> {
        return _meals.toList()
    }

    fun getTotalNutrition(): NutritionResponse {
        return if (_meals.isEmpty()) {
            NutritionResponse(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
        } else {
            NutritionResponse(
                calories = _meals.sumOf { it.nutritionInfo.calories.toDouble() }.toFloat(),
                protein = _meals.sumOf { it.nutritionInfo.protein.toDouble() }.toFloat(),
                fat = _meals.sumOf { it.nutritionInfo.fat.toDouble() }.toFloat(),
                carbohydrates = _meals.sumOf { it.nutritionInfo.carbohydrates.toDouble() }.toFloat(),
                fiber = _meals.sumOf { it.nutritionInfo.fiber.toDouble() }.toFloat(),
                sugar = _meals.sumOf { it.nutritionInfo.sugar.toDouble() }.toFloat(),
                sodium = _meals.sumOf { it.nutritionInfo.sodium.toDouble() }.toFloat(),
                calcium = _meals.sumOf { it.nutritionInfo.calcium.toDouble() }.toFloat(),
                iron = _meals.sumOf { it.nutritionInfo.iron.toDouble() }.toFloat()
            )
        }
    }
} 