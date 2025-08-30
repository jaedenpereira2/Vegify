package com.example.vegify.ui.searchsection

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchSectionViewModel: ViewModel() {
    private val _currentIngredient = mutableStateOf("")
    val currentIngredient= _currentIngredient
    private val _ingredients = mutableStateListOf<String>()
    val ingredients=_ingredients
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    var servings = mutableStateOf("")
    var timeOnHand = mutableStateOf("")

    fun updateServings(value: String) {
        servings.value = value
    }

    fun updateTimeOnHand(value: String) {
        timeOnHand.value = value
    }


    fun updateCurrentIngredient(ingredient: String) {
        _currentIngredient.value = ingredient
    }

    fun addIngredient() {
        val ingredient = _currentIngredient.value.trim()
        if (ingredient.isEmpty()) {
            _errorMessage.value = "Ingredient cannot be empty"
        } else if (ingredient.toIntOrNull() != null) {
            _errorMessage.value = "Ingredient cannot be a number"
        } else if (_ingredients.contains(ingredient)) {
            _errorMessage.value = "Ingredient already added"
        } else {
            _ingredients.add(ingredient)
            _currentIngredient.value = ""
            _errorMessage.value = null // only clear error on valid add
        }

    }

    fun removeIngredient(ingredient: String) {
        _ingredients.remove(ingredient)
    }

    fun validateIngredients(): Boolean {
        return if (_ingredients.size < 4) {
            false
        } else {
            _errorMessage.value = null
            true
        }
    }

    fun getIngredientsAsString(): String {
        return _ingredients.joinToString(",")
    }
}