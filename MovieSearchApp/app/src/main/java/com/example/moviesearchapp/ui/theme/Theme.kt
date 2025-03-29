package com.example.moviesearchapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define the colors you want to use
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1A1A1A), // Custom dark color
    secondary = Color(0xFF2E2E2E), // Custom dark color
    surface = Color(0xFF121212), // Custom dark color
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE), // Default primary color
    secondary = Color(0xFF03DAC6), // Default secondary color
    surface = Color(0xFFFFFFFF), // Default surface color
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onSurface = Color.Black
)

@Composable
fun MovieSearchAppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    // Choose theme based on darkTheme boolean
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
