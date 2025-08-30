package com.example.vegify.ui.searchsection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun SearchSection(navController : NavController){
    val viewModel: SearchSectionViewModel = viewModel()
    val currentIngredient = viewModel.currentIngredient.value
    val ingredients = viewModel.ingredients
    val errorMessage by viewModel.errorMessage.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Recipe Finding Made Easy 🍽️",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Let us help you find the perfect recipe based on your ingredients, servings, and time!",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        OutlinedTextField(
            value = viewModel.servings.value,
            onValueChange = { viewModel.updateServings(it) },
            label = { Text("Number of Servings") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = viewModel.timeOnHand.value,
            onValueChange = { viewModel.updateTimeOnHand(it) },
            label = { Text("Time on Hand (in minutes)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = currentIngredient,
                onValueChange = { viewModel.updateCurrentIngredient(it) },
                label = { Text("Ingredient") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )

            IconButton(
                onClick = { viewModel.addIngredient() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Ingredient"
                )
            }
        }

        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Text(
            text = "Your Ingredients (${ingredients.size}/4 minimum):",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(ingredients) { ingredient ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = ingredient)
                        IconButton(onClick = { viewModel.removeIngredient(ingredient) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove Ingredient"
                            )
                        }
                    }
                }
            }
        }
        val isFormValid = viewModel.validateIngredients() &&
                viewModel.servings.value.isNotBlank() &&
                viewModel.timeOnHand.value.isNotBlank()

        Button(
            onClick = {
                if (isFormValid) {
                    navController.navigate(
                        "recipeDetail/${viewModel.getIngredientsAsString()}/${viewModel.servings.value}/${viewModel.timeOnHand.value}"
                    )
                }
            },
            enabled = isFormValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Generate Recipe")
        }
    }

}