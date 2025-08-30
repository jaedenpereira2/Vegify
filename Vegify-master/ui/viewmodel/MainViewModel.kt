package com.example.vegify.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vegify.recipeService
import com.example.vegify.ui.homepageScreen.Category
import com.example.vegify.ui.homepageScreen.Recipe
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    init {
        FetchCategory()
    }
    private fun FetchCategory(){
        viewModelScope.launch {
            try {
                val FetchedCategory= recipeService.getCategory()
                _CategoryState.value=_CategoryState.value.copy(
                    isLoading = false,
                    error = null,
                    list= FetchedCategory.categories
                )
            }catch (e:Exception){
                _CategoryState.value=_CategoryState.value.copy(
                    isLoading = false,
                    error = "Error Loading Category: ${e.message}"
                )
            }
        }
    }
    fun clearRecipes() {
        _recipeState.value = RecipeListState(isLoading = true, list = emptyList())
    }
    fun fetchRecipe(category: String){
        viewModelScope.launch{
            println("Navigating to recipeDetail with mealName: ${category}")

            try {
                val fetchedRecipes= recipeService.getRecipeForCategory(category)
                _recipeState.value=_recipeState.value.copy(
                    isLoading = false,
                    error = null,
                    //list of all recipes in a particular category
                    list = fetchedRecipes.meals
                )
            }catch (e:Exception){
                _recipeState.value=_recipeState.value.copy(
                    isLoading = false,
                    error = "Error fetching recipe:${e.message}"
                )
            }
        }
    }
    private val _CategoryState= mutableStateOf(RecipeState())
    val CategoryState: State<RecipeState> = _CategoryState
    data class RecipeState(
        val isLoading:Boolean=true,
        val error:String?=null,
        val list:List<Category> = emptyList()
    )
    private val _recipeState= mutableStateOf(RecipeListState())
    val recipeState: State<RecipeListState> = _recipeState
    data class RecipeListState(
        val isLoading:Boolean=true,
        val error:String?=null,
        val list:List<Recipe>?= emptyList()
    )
}