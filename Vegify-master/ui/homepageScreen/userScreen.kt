package com.example.vegify.ui.homepageScreen

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.vegify.ui.viewmodel.UserViewModel

@Composable
fun userScreen(navController: NavController, viewModel: UserViewModel = viewModel()){
    val user=viewModel.user //get list of users
    Column(
        modifier= Modifier
            .padding(16.dp)
    ) {
        user.forEach{userItem->
            Row(
                modifier= Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                //Image
                Image(
                    painter= rememberAsyncImagePainter(model = userItem.pfp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier= Modifier.width(8.dp))
                //name
                Text(
                    text=userItem.name,
                    fontSize = 16.sp
                )
                Spacer(modifier= Modifier.width(4.dp))
                //Verified Icon
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription =null,
                    modifier= Modifier.size(16.dp),
                    tint = Color.Blue
                )
            }

            LazyRow(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(200.dp)
            ){
                items(userItem.recipes){item->
                    Card(
                        modifier = Modifier
                            .clickable {
                                navController.navigate("ChefRecipeDetailScreen/${item.name}")
                               /* navigateToDetailScreen("ChefRecipeDetailScreen/${item.name}")*/

                            }
                            .padding(end = 12.dp)
                            .width(145.dp)
                            .height(200.dp),
                        elevation = CardDefaults.cardElevation(4.dp)

                    ) {
                        Column (
                            modifier= Modifier
                                .fillMaxSize()
                                .padding(14.dp),
                            verticalArrangement = Arrangement.Center
                        ){
                            Image(
                                painter = rememberAsyncImagePainter(model = item.img) ,
                                contentDescription = null,
                                modifier= Modifier
                                    .size(120.dp)
                                    .padding(bottom = 8.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Text(
                                text=item.name,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
