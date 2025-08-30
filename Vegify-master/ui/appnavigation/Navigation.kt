package com.example.vegify.ui.appnavigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vegify.ui.homepageScreen.ChefRecipeDetailScreen
import com.example.vegify.ui.homepageScreen.DetailedRecipe
import com.example.vegify.ui.homepageScreen.HomePageScreen
import com.example.vegify.ui.homepageScreen.navigateToRecipesInCategoryScreen
import com.example.vegify.ui.myaccount.MyAccount
import com.example.vegify.ui.nutitionsection.NutritionScreen
import com.example.vegify.ui.searchsection.GeneratedRecipeScreen
import com.example.vegify.ui.searchsection.SearchSection
import com.example.vegify.ui.viewmodel.MainViewModel
import com.example.vegify.ui.viewmodel.MealViewModel
import com.example.vegify.ui.viewmodel.NutritionViewModel

@Composable
fun Navigation(homePageViewModel: MainViewModel) {
    val navController = rememberNavController()
    val mealViewModel: MealViewModel = viewModel()
    val nutritionViewModel: NutritionViewModel = viewModel()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "homepageSection",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("homepageSection") {
                HomePageScreen(homePageViewModel, navController)
            }
            composable("detailScreen/{category}") {
                val categoryName = it.arguments?.getString("category") ?: "Unknown"
                navigateToRecipesInCategoryScreen(categoryName, homePageViewModel, navController)
            }

            composable("detailedRecipe/{mealId}/{category}") { backStackEntry ->
                val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
                val category = backStackEntry.arguments?.getString("category") ?: ""
                DetailedRecipe(navController, mealId, category, nutritionViewModel)
            }
            composable("ChefRecipeDetailScreen/{mealName}") { backStackEntry ->
                val mealName = backStackEntry.arguments?.getString("mealName") ?: ""
                ChefRecipeDetailScreen(navController, mealName, mealViewModel,nutritionViewModel)
            }

            composable("searchSection") {
                SearchSection(navController)
            }

            composable(
                route = "recipeDetail/{ingredients}/{servings}/{timeOnHand}"
            ) { backStackEntry ->
                val ingredients = backStackEntry.arguments?.getString("ingredients") ?: ""
                val servings = backStackEntry.arguments?.getString("servings") ?: ""
                val timeOnHand = backStackEntry.arguments?.getString("timeOnHand") ?: ""

                GeneratedRecipeScreen(
                    ingredients = ingredients,
                    servings = servings,
                    timeOnHand = timeOnHand,
                    navController = navController
                )
            }

            composable("nutritionSection") {
                NutritionScreen(navController, nutritionViewModel)
            }

            composable("myaccountSection") {
                MyAccount(navController)
            }
        }
    }
}