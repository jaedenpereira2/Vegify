package com.example.vegify.ui.appnavigation

import androidx.compose.runtime.Composable
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.BottomNavigation
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItems("Home", Icons.Default.Home, "homepageSection"),
        BottomNavItems("Search", Icons.Default.Search, "searchSection"),
        BottomNavItems("Nutrition", Icons.Default.Favorite, "nutritionSection"),
        /*BottomNavItems("Profile", Icons.Default.Person, "myaccountSection")*/
    )

    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 8.dp
    ) {
        val currentRoute = navController.currentDestination?.route
        items.forEach { item ->
            BottomNavigationItem( // ✅ Correct Composable
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label, fontSize = 12.sp) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
