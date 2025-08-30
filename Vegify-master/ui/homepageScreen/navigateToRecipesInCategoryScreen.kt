package com.example.vegify.ui.homepageScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.vegify.ui.viewmodel.MainViewModel

@Composable
fun navigateToRecipesInCategoryScreen(
    currentCategory: String,
    viewModel: MainViewModel,
    navController: NavController
){

    //currentCategory has the name to be sent for filtering recipes in  particular category
    //we'll send this to fetchRecipe
    LaunchedEffect( currentCategory){
        viewModel.clearRecipes()
        viewModel.fetchRecipe(currentCategory)
    }
    val list = viewModel.recipeState.value.list ?: emptyList()
    LazyColumn(
        modifier= Modifier
            .fillMaxSize()
            .padding(6.dp)
    ){
        item{
            Button(onClick = { navController.navigate("homepageSection") }) {
                Text(text = "Back")
            }
        }
        items(list){listItem->
            ShowListItem(listItem,navController,currentCategory)
        }
    }
}
@Composable
fun ShowListItem(recipe: Recipe, navController: NavController, currentCategory: String){
    Card(
        modifier = Modifier
            .clickable {
                //navController
                // .navigate("detailedRecipe/${recipe.strMeal}/${currentCategory}")
                navController
                    .navigate("detailedRecipe/${recipe.idMeal}/${currentCategory}")

            }
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier=Modifier.fillMaxWidth().background(Color(0xFFF5E6CA)),
        ){
            Image(
                painter = rememberAsyncImagePainter(model = recipe.strMealThumb),
                contentDescription = recipe.strMeal,
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(9.dp))
            )
            Text(recipe.strMeal, Modifier.padding(16.dp))
        }
    }
}
