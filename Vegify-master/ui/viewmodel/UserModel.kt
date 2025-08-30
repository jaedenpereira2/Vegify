package com.example.vegify.ui.viewmodel

class UserModel {
    data class User(
        val pfp:String,
        val name:String,
        val recipes:List<recipesSection>
    )
    data class recipesSection(
        val id:Int,
        val img:String,
        val name:String
    )
}