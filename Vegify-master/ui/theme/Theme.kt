package com.example.vegify.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun VegifyTheme(content: @Composable () -> Unit) {
    val colorScheme = lightColorScheme( // ✅ Use `lightColorScheme` for Material 3
        primary = Color(0xFF3F553F), // Example: Green
        background = Color.White,
        onBackground = Color.Black,
        surface = Color.White,
        onSurface = Color.Black
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme, // ✅ Use `colorScheme` for Material 3
        typography = Typography,
        content = content
    )
}


