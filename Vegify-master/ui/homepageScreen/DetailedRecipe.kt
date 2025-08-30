package com.example.vegify.ui.homepageScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.vegify.NutritionService
import com.example.vegify.ui.nutitionsection.CategoryDialog
import com.example.vegify.ui.viewmodel.MealViewModel
import com.example.vegify.ui.viewmodel.NutritionViewModel
import kotlinx.coroutines.launch

@Composable
fun DetailedRecipe(
    navController: NavController,
    mealId: String,
    category:String,
    nutritionViewModel: NutritionViewModel
) {
    val viewModel: MealViewModel = viewModel()
    LaunchedEffect(mealId) {
        viewModel.fetchMealById(mealId)
    }

    val meal = remember { mutableStateOf(viewModel.meal) }
    RecipeDetailView(meal.value.value,navController,category,nutritionViewModel)
}

@Composable
fun RecipeDetailView(
    meal: Meal?,
    navController: NavController,
    category: String,
    nutritionViewModel: NutritionViewModel
) {
    var selectedCategory by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF5E6CA))
        ) {
            if (showDialog) {
                CategoryDialog(
                    onCategorySelected = { selected ->
                        selectedCategory = selected
                        showDialog = false
                        meal?.let {
                            nutritionViewModel.addMeal(selected, it.strMeal)
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Meal added to $selected" +
                                        "\nFor more information check out Nutrition Section!")
                            }
                        }
                    },
                    onDismiss = {
                        showDialog = false
                    }
                )
            }

            meal?.let {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(onClick = {
                                if (category != "") {
                                    navController.navigate("detailScreen/${category}")
                                } else {
                                    navController.navigate("homepageSection")
                                }
                            }) {
                                Text(text = "Back")
                            }

                            Button(onClick = { showDialog = true }) {
                                Text("Add to meal")
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(
                                modifier = Modifier
                                    .width(165.dp)
                            ) {
                                Text(
                                    text = it.strMeal,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "Category: ${it.strCategory}\nArea: ${it.strArea}",
                                    color = Color(0xFF3C3C3C),
                                    fontSize = 12.sp,
                                    modifier = Modifier.scale(0.9f),
                                    lineHeight = 15.sp
                                )
                            }
                            Image(
                                painter = rememberAsyncImagePainter(it.strMealThumb),
                                contentDescription = it.strMeal,
                                modifier = Modifier
                                    .size(170.dp) // Size first
                                    .padding(8.dp) // Padding inside card
                            )
                        }
                    }
                    //Ingredients section
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(start = 24.dp, end = 24.dp, bottom = 12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(24.dp)) // Apply clipping first
                                    .background(Color.White) // Then apply background
                                    .padding(16.dp)
                            ) {
                                Column {
                                    Text(
                                        text = "Ingredients:",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    val ingredientsList = listOf(
                                        it.ingredient1 to it.measure1,
                                        it.ingredient2 to it.measure2,
                                        it.ingredient3 to it.measure3,
                                        it.ingredient4 to it.measure4,
                                        it.ingredient5 to it.measure5,
                                        it.ingredient6 to it.measure6,
                                        it.ingredient7 to it.measure7,
                                        it.ingredient8 to it.measure8,
                                        it.ingredient9 to it.measure9,
                                        it.ingredient10 to it.measure10,
                                        it.ingredient11 to it.measure11,
                                        it.ingredient12 to it.measure12,
                                        it.ingredient13 to it.measure13,
                                        it.ingredient14 to it.measure14,
                                        it.ingredient15 to it.measure15,
                                        it.ingredient16 to it.measure16,
                                        it.ingredient17 to it.measure17
                                    ).filter { pair -> !pair.first.isNullOrEmpty() }

                                    ingredientsList.forEach { (ingredient, measure) ->
                                        Text(
                                            text = "$ingredient - $measure",
                                            fontSize = 16.sp,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 8.dp),
                                            lineHeight = 18.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                    // Instructions Section with Dark Green Background
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF3F553F)) // Dark green background
                        ) {
                            Text(
                                "Instructions:",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(12.dp),
                                color = Color.White,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = "\n${it.strInstructions ?: "No Instructions Available"}",
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                color = Color.White,
                                modifier = Modifier.padding(12.dp) // Padding inside, no external gaps
                            )
                        }
                    }
                    //Yt video
                    item {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .background(Color.White)
                                    .fillMaxWidth()
                                    .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .width(120.dp)
                                        .padding(top = 12.dp, start = 12.dp)
                                ) {
                                    val tag = it.strTags
                                        ?.split(",")
                                        ?.map { tag ->
                                            "#${tag.trim()}"
                                        } ?: emptyList()
                                    if (tag.isNotEmpty()) {
                                        Column {
                                            tag.forEach { tag ->
                                                Text(
                                                    text = tag,
                                                    fontSize = 14.sp,
                                                    lineHeight = 16.sp,
                                                    color = Color(0xFF018EFF),
                                                    modifier = Modifier
                                                        .padding(end = 4.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                                Column {
                                    Spacer(Modifier.width(18.dp))
                                    //Adding the function to redirect to youtube
                                    val youtubeUrl = it.strYoutube
                                    if (!youtubeUrl.isNullOrEmpty()) {
                                        Text(
                                            "Need Help?",
                                            fontSize = 16.sp,
                                        )
                                        VideoTutorialLink(it)
                                    }
                                }
                            }
                        }
                    }
                }
            } ?: CircularProgressIndicator(
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun VideoTutorialLink(meal: Meal) {
    val context = LocalContext.current
    val youtubeUrl = meal.strYoutube ?: ""

    Text(
        text = "Click to watch a Video Tutorial!",
        color = Color(0xFF3F553F),
        fontSize = 14.sp,
        lineHeight = 18.sp,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .clickable {
                if (youtubeUrl.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                    context.startActivity(intent)
                }
            }
    )
}

fun sendMealToNutrition(mealName: String, category: String, navController: NavController) {
    navController.navigate("nutritionScreen/${mealName}/${category}")
}

