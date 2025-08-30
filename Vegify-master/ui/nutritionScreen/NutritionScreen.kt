package com.example.vegify.ui.nutritionScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vegify.MealService
import com.example.vegify.ui.components.MealCategory

@Composable
fun NutritionScreen() {
    val meals by MealService.mealsFlow.collectAsState()
    val totalNutrition = MealService.getTotalNutrition()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Nutrition Summary",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Display total nutrition
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Total Daily Nutrition",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                NutritionRow("Calories", "${totalNutrition.calories} kcal")
                NutritionRow("Protein", "${totalNutrition.protein}g")
                NutritionRow("Fat", "${totalNutrition.fat}g")
                NutritionRow("Carbohydrates", "${totalNutrition.carbohydrates}g")
                NutritionRow("Fiber", "${totalNutrition.fiber}g")
                NutritionRow("Sugar", "${totalNutrition.sugar}g")
                NutritionRow("Sodium", "${totalNutrition.sodium}mg")
                NutritionRow("Calcium", "${totalNutrition.calcium}mg")
                NutritionRow("Iron", "${totalNutrition.iron}mg")
            }
        }

        // Display meals by category
        MealCategory.values().forEach { category ->
            val categoryMeals = meals.filter { it.category == category }
            if (categoryMeals.isNotEmpty()) {
                Text(
                    text = category.name,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                LazyColumn {
                    items(categoryMeals) { meal ->
                        MealCard(meal)
                    }
                }
            }
        }
    }
}

@Composable
fun NutritionRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label)
        Text(text = value)
    }
}

@Composable
fun MealCard(meal: com.example.vegify.Meal) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = meal.name,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            NutritionRow("Calories", "${meal.nutritionInfo.calories} kcal")
            NutritionRow("Protein", "${meal.nutritionInfo.protein}g")
            NutritionRow("Fat", "${meal.nutritionInfo.fat}g")
            NutritionRow("Carbohydrates", "${meal.nutritionInfo.carbohydrates}g")
            
            Button(
                onClick = { MealService.removeMeal(meal.name) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Remove")
            }
        }
    }
} 