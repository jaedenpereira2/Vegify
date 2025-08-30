package com.example.vegify.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.vegify.ui.viewmodel.UserModel

class UserViewModel: ViewModel() {
    private val _user = mutableStateOf(
        listOf(
            UserModel.User(
                "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcS7j7RWzqyxdBsaPWt69xy0lI3zvRCO4qpozWqyUEdxIDNTXKaHLPNyW7DohOXacG_WO5Nc7i2EeZtPCU-MsBfExA",
                "Gordan ramsay",
                listOf(
                    UserModel.recipesSection(
                        1,
                        "https://www.themealdb.com/images/media/meals/wvqpwt1468339226.jpg",
                        "Mediterranean Pasta Salad"
                    ),
                    UserModel.recipesSection(
                        2,
                        "https://www.themealdb.com/images/media/meals/qxutws1486978099.jpg",
                        "Chocolate cake"
                    )
                )
            ),
            UserModel.User(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTBxp94o9gr3DK_wctMZCzRbzd3J4u9cm7P6Q&s",
                "Sanjeev Kapoor",
                listOf(
                    UserModel.recipesSection(
                        1,
                        "https://www.themealdb.com/images/media/meals/xrttsx1487339558.jpg",
                        "Biryani"

                    ),
                    UserModel.recipesSection(
                        2,
                        "https://www.themealdb.com/images/media/meals/qrqywr1503066605.jpg",
                        "Chicken Fajita Mac and Cheese"
                    ),
                    UserModel.recipesSection(
                        3,
                        "https://www.themealdb.com/images/media/meals/rwuyqx1511383174.jpg",
                        "Pancakes"
                    )
                )
            )
        )
    )
    val user=_user.value
}
