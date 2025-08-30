package com.example.vegify.ui.searchsection

// -- Imports --
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vegify.MealService
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import com.example.vegify.ui.components.MealCategory
import com.example.vegify.ui.components.MealCategoryDialog
import kotlinx.coroutines.launch

// -- UI Composable --
@Composable
fun GeneratedRecipeScreen(
    ingredients: String,
    servings: String,
    timeOnHand: String,
    navController: NavController
) {
    val viewModel: SearchSectionViewModel = viewModel()
    var recipeS by remember { mutableStateOf(RecipeState()) }
    var showDialog by remember { mutableStateOf(false) }
    var recipeName by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(ingredients, servings, timeOnHand) {
        recipeS = RecipeState(isLoading = true)
        try {
            val result = fetchRecipe(ingredients, servings, timeOnHand)
            recipeS = RecipeState(recipe = result, isLoading = false)
            // Extract recipe name from the first line of the recipe
            recipeName = result.split("\n").firstOrNull() ?: "Custom Recipe"
        } catch (e: Exception) {
            recipeS = RecipeState(error = e.message, isLoading = false)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5E6CA))
    ) {
        when {
            recipeS.isLoading -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(150.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            recipeS.recipe != null -> Column(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    item {


                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = recipeS.recipe!!,
                                modifier = Modifier.padding(12.dp),
                                fontSize = 18.sp
                            )
                        }
                    }
                }
                

            }
        }
    }

    if (showDialog) {
        MealCategoryDialog(
            onDismiss = { showDialog = false },
            onConfirm = { category ->
                scope.launch {
                    MealService.addMeal(recipeName, category)
                    showDialog = false
                }
            }
        )
    }
}


// -- State Class --
data class RecipeState(
    val recipe: String? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)



