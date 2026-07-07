package com.example.pokedexpokeapi.ui.components.font

import androidx.compose.animation.core.copy
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.Font
import pokedexpokeapi.composeapp.generated.resources.Res
import pokedexpokeapi.composeapp.generated.resources.pixel_font
import androidx.compose.ui.text.TextStyle

@Composable
fun AppTypography() : Typography{
    val pixelFont = FontFamily(Font(Res.font.pixel_font))
    val default = Typography()
    val scale = 2f

    fun TextStyle.scaled() = this.copy(
        fontFamily = pixelFont,
        fontSize = this.fontSize * scale
    )
    return Typography(
        displayLarge = default.displayLarge.scaled(),
        displayMedium = default.displayMedium.scaled(),
        displaySmall = default.displaySmall.scaled(),
        headlineLarge = default.headlineLarge.scaled(),
        headlineMedium = default.headlineMedium.scaled(),
        headlineSmall = default.headlineSmall.scaled(),
        titleLarge = default.titleLarge.scaled(),
        titleMedium = default.titleMedium.scaled(),
        titleSmall = default.titleSmall.scaled(),
        bodyLarge = default.bodyLarge.scaled(),
        bodyMedium = default.bodyMedium.scaled(),
        bodySmall = default.bodySmall.scaled(),
        labelLarge = default.labelLarge.scaled(),
        labelMedium = default.labelMedium.scaled(),
        labelSmall = default.labelSmall.scaled()
    )
}