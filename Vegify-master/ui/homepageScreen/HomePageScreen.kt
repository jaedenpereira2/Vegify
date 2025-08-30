package com.example.vegify.ui.homepageScreen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vegify.ui.viewmodel.MainViewModel
import com.example.vegify.R

val LogoFont=FontFamily(
    Font(R.font.logofont) // Use the filename you added
)

@Composable
fun HomePageScreen(
    homePageViewModel: MainViewModel,
    navController: NavController
) {
    val RecipeVisible by homePageViewModel.CategoryState

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        heading()
                    }
                }
            //recommended categories
                item {
                    Column(
                        modifier = Modifier.padding(top = 16.dp)) {
                        when {
                            RecipeVisible.isLoading -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }

                            RecipeVisible.error != null -> {
                                Text("Error fetching data ${RecipeVisible.error}")
                            }

                            else -> {
                                if (RecipeVisible.list.isNotEmpty())
                                    recommendedSectionShow(RecipeVisible.list,navController)
                            }
                        }
                    }
                }
            //Verified Chef Section
                item {
                    Spacer(Modifier.height(16.dp))
                    Column {
                        Text(
                            text ="Check Out Recipes From Verified Chefs:",
                            modifier= Modifier
                                .fillMaxHeight(1f)
                                .padding(start = 12.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        userScreen(navController)
                    }
                }
            }

    }
}
