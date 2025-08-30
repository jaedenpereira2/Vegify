package com.example.vegify.ui.homepageScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.runtime.livedata.observeAsState
import com.example.vegify.ui.viewmodel.MealViewModel
import com.example.vegify.ui.viewmodel.NutritionViewModel


@Composable
fun ChefRecipeDetailScreen(
    navController: NavController,
    mealName: String,
    viewModel: MealViewModel,
    nutritionViewModel: NutritionViewModel
) {

    // Observe LiveData properly
    val meal by viewModel.meal.observeAsState()

    LaunchedEffect(mealName) {
        viewModel.fetchMealByName(mealName)
    }

    if (meal == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        RecipeDetailView(meal, navController, "",nutritionViewModel)
    }
}