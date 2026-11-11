package ke.don.design.theme

import androidx.compose.ui.graphics.Color

/**
 * DevFest Nairobi 2026 brand colors, sampled from the official slide template.
 * Use these directly for brand moments (the wordmark, accents); use the
 * [androidx.compose.material3.ColorScheme] roles everywhere else.
 */
object DevFestColors {
    val Blue = Color(0xFF4385F5)
    val Red = Color(0xFFEA4235)
    val Yellow = Color(0xFFF9AB00)
    val Green = Color(0xFF34A853)

    /** Grey used for speaker names on the light template. */
    val Slate = Color(0xFF595959)

    /** Grey used for speaker names on the dark template. */
    val Mist = Color(0xFFD1D3D4)

    /** Blue, red, yellow, green: the order Google uses when cycling brand colors. */
    val cycle = listOf(Blue, Red, Yellow, Green)
}

// Light: white canvas, near-black ink, as on the light template slides.
val primaryLight = DevFestColors.Blue
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFD3E3FD)
val onPrimaryContainerLight = Color(0xFF0B3D91)
val secondaryLight = DevFestColors.Green
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFCEEAD6)
val onSecondaryContainerLight = Color(0xFF0D652D)
val tertiaryLight = DevFestColors.Yellow
val onTertiaryLight = Color(0xFF1F1F1F)
val tertiaryContainerLight = Color(0xFFFEEFC3)
val onTertiaryContainerLight = Color(0xFF7A4F01)
val errorLight = DevFestColors.Red
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFAD2CF)
val onErrorContainerLight = Color(0xFFA50E0E)
val backgroundLight = Color(0xFFFFFFFF)
val onBackgroundLight = Color(0xFF000000)
val surfaceLight = Color(0xFFFFFFFF)
val onSurfaceLight = Color(0xFF000000)
val surfaceVariantLight = Color(0xFFF1F3F4)
val onSurfaceVariantLight = DevFestColors.Slate
val outlineLight = Color(0xFF9AA0A6)
val outlineVariantLight = Color(0xFFDADCE0)
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = Color(0xFF000000)
val inverseOnSurfaceLight = Color(0xFFFFFFFF)
val inversePrimaryLight = Color(0xFF8AB4F8)
val surfaceDimLight = Color(0xFFDADCE0)
val surfaceBrightLight = Color(0xFFFFFFFF)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFF8F9FA)
val surfaceContainerLight = Color(0xFFF1F3F4)
val surfaceContainerHighLight = Color(0xFFE8EAED)
val surfaceContainerHighestLight = Color(0xFFDADCE0)

// Dark: pure black canvas, white ink, as on the dark template slides.
val primaryDark = DevFestColors.Blue
val onPrimaryDark = Color(0xFFFFFFFF)
val primaryContainerDark = Color(0xFF0B3D91)
val onPrimaryContainerDark = Color(0xFFD3E3FD)
val secondaryDark = DevFestColors.Green
val onSecondaryDark = Color(0xFF000000)
val secondaryContainerDark = Color(0xFF0D652D)
val onSecondaryContainerDark = Color(0xFFCEEAD6)
val tertiaryDark = DevFestColors.Yellow
val onTertiaryDark = Color(0xFF000000)
val tertiaryContainerDark = Color(0xFF7A4F01)
val onTertiaryContainerDark = Color(0xFFFEEFC3)
val errorDark = DevFestColors.Red
val onErrorDark = Color(0xFFFFFFFF)
val errorContainerDark = Color(0xFFA50E0E)
val onErrorContainerDark = Color(0xFFFAD2CF)
val backgroundDark = Color(0xFF000000)
val onBackgroundDark = Color(0xFFFFFFFF)
val surfaceDark = Color(0xFF000000)
val onSurfaceDark = Color(0xFFFFFFFF)
val surfaceVariantDark = Color(0xFF1F1F1F)
val onSurfaceVariantDark = DevFestColors.Mist
val outlineDark = Color(0xFF80868B)
val outlineVariantDark = Color(0xFF3C4043)
val scrimDark = Color(0xFF000000)
val inverseSurfaceDark = Color(0xFFFFFFFF)
val inverseOnSurfaceDark = Color(0xFF000000)
val inversePrimaryDark = Color(0xFF1A73E8)
val surfaceDimDark = Color(0xFF000000)
val surfaceBrightDark = Color(0xFF2D2E30)
val surfaceContainerLowestDark = Color(0xFF000000)
val surfaceContainerLowDark = Color(0xFF0E0E0E)
val surfaceContainerDark = Color(0xFF161616)
val surfaceContainerHighDark = Color(0xFF1F1F1F)
val surfaceContainerHighestDark = Color(0xFF282828)
