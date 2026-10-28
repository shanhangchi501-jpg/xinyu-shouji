package com.innerisle.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LightColors = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF5C6B8A),
    onPrimary = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
    primaryContainer = androidx.compose.ui.graphics.Color(0xFFDDE3F2),
    onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFF1B2430),
    secondary = androidx.compose.ui.graphics.Color(0xFFA7805C),
    onSecondary = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
    secondaryContainer = androidx.compose.ui.graphics.Color(0xFFF1E2D0),
    onSecondaryContainer = androidx.compose.ui.graphics.Color(0xFF3A2A1B),
    tertiary = androidx.compose.ui.graphics.Color(0xFF6F8A7A),
    onTertiary = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
    tertiaryContainer = androidx.compose.ui.graphics.Color(0xFFDDEBE2),
    onTertiaryContainer = androidx.compose.ui.graphics.Color(0xFF1E2B24),
    background = androidx.compose.ui.graphics.Color(0xFFF7F4EE),
    onBackground = androidx.compose.ui.graphics.Color(0xFF1B2430),
    surface = androidx.compose.ui.graphics.Color(0xFFFFFDF9),
    onSurface = androidx.compose.ui.graphics.Color(0xFF1B2430),
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFFE8E2D8),
    onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF4A4F58),
    outline = androidx.compose.ui.graphics.Color(0xFF8A8F99),
    error = androidx.compose.ui.graphics.Color(0xFFB3261E)
)

private val DarkColors = darkColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFFB8C6E6),
    onPrimary = androidx.compose.ui.graphics.Color(0xFF233047),
    primaryContainer = androidx.compose.ui.graphics.Color(0xFF34445E),
    onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFFDDE3F2),
    secondary = androidx.compose.ui.graphics.Color(0xFFE0C3A5),
    onSecondary = androidx.compose.ui.graphics.Color(0xFF3F2D1B),
    secondaryContainer = androidx.compose.ui.graphics.Color(0xFF57412B),
    onSecondaryContainer = androidx.compose.ui.graphics.Color(0xFFF1E2D0),
    tertiary = androidx.compose.ui.graphics.Color(0xFFA9C7B7),
    onTertiary = androidx.compose.ui.graphics.Color(0xFF1F342B),
    tertiaryContainer = androidx.compose.ui.graphics.Color(0xFF334C40),
    onTertiaryContainer = androidx.compose.ui.graphics.Color(0xFFDDEBE2),
    background = androidx.compose.ui.graphics.Color(0xFF12161D),
    onBackground = androidx.compose.ui.graphics.Color(0xFFE8E6E1),
    surface = androidx.compose.ui.graphics.Color(0xFF191F28),
    onSurface = androidx.compose.ui.graphics.Color(0xFFE8E6E1),
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFF2A313C),
    onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFFC3C7CF),
    outline = androidx.compose.ui.graphics.Color(0xFF8A8F99),
    error = androidx.compose.ui.graphics.Color(0xFFF2B8B5)
)

private val InnerIsleTypography = Typography(
    displaySmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Medium,
        fontSize = 21.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 26.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)

private val InnerIsleShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

@Composable
fun InnerIsleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val reduceMotion = rememberReduceMotion()
    CompositionLocalProvider(LocalReduceMotion provides reduceMotion) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColors else LightColors,
            typography = InnerIsleTypography,
            shapes = InnerIsleShapes,
            content = content
        )
    }
}
