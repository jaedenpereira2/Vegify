package com.example.vegify.ui.nutitionsection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vegify.ui.viewmodel.NutritionViewModel

@Composable
fun NutritionScreen(
    navController: NavController,
    nutritionViewModel: NutritionViewModel
) {
    val mealsByCategory = nutritionViewModel.mealsByCategory
    val totalNutrition = nutritionViewModel.totalNutrition
    val isLoading = nutritionViewModel.isLoading

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total Nutritional Intake",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
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
        }

        items(listOf("Breakfast", "Lunch", "Dinner", "Snacks")) { category ->
            Text(
                text = category,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            val meals = mealsByCategory[category] ?: emptyList()

            if (meals.isEmpty()) {
                Text("No meals added yet.", fontSize = 14.sp, color = Color.Gray)
            } else {
                meals.forEach { mealName ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "• $mealName",
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = { nutritionViewModel.removeMeal(category, mealName) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete meal"
                            )
                        }
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
        Text(text = value, fontWeight = FontWeight.Bold)
    }
}