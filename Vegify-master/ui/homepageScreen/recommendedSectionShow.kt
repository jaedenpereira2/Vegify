package com.example.vegify.ui.homepageScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun recommendedSectionShow(
    recipesList:List<Category>,
    navController: NavController
){
    Column()
    {
        Text(
            text = "Recommended Categories",
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 12.dp, bottom = 10.dp),
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            items(recipesList){
                recipe->
                RecipeTab(recipe,navController)
            }
        }
    }
}
@Composable
fun RecipeTab(
    x: Category,
    navController: NavController
){
    Card(
        modifier = Modifier
            .padding(end = 12.dp)
            .width(145.dp)
            .height(170.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                /*.clickable { navigateToDetailScreen(x.strCategory) }*/
                .clickable { navController.navigate("detailScreen/${x.strCategory}") }
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Image(
                painter = rememberAsyncImagePainter(model = x.strCategoryThumb), // Fixed image loading
                contentDescription = x.strCategory, // Fixed property name
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp)) // Rounded image corners
            )
            Text(
                text = x.strCategory, // Fixed property name
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

