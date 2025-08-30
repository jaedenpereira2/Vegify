package com.example.vegify.ui.homepageScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vegify.R

@Composable
fun heading(){
    Spacer(Modifier.width(14.dp))
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription =null,
        modifier= Modifier
            .size(45.dp) // Set the size of the circle
            .clip(CircleShape) // Make it circular
            .border(BorderStroke(1.dp, Color.White), CircleShape)
    )
    Spacer(modifier = Modifier.width(9.dp))
    Text(
        text = "Vegify",
        color = Color(0xFF677700),
        fontFamily = LogoFont,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
    )
}