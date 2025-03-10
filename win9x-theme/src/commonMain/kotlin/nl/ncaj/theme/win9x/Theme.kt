package nl.ncaj.theme.win9x

import androidx.compose.foundation.LocalIndication
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import nl.ncaj.theme.win9x.controls.TypographyTokens

@Stable
class ColorScheme(
    windowFrame: Color = Color(0xFF0A0A0A),
    buttonFace: Color = Color(0xFFB3B3B3),
    buttonHighlight: Color = Color(0xFFFFFFFF),
    buttonShadow: Color = Color(0xFF808080),
    selection: Color = Color(0, 0, 123),
    activeCaption: Color = Color(0, 0, 123),
    captionText: Color = Color(0xFFFFFFFF),
) {
    var windowFrame by mutableStateOf(windowFrame, structuralEqualityPolicy())
        internal set
    var buttonFace by mutableStateOf(buttonFace, structuralEqualityPolicy())
        internal set
    var buttonHighlight by mutableStateOf(buttonHighlight, structuralEqualityPolicy())
        internal set
    var buttonShadow by mutableStateOf(buttonShadow, structuralEqualityPolicy())
        internal set
    var selection by mutableStateOf(selection, structuralEqualityPolicy())
        internal set
    var activeCaption by mutableStateOf(activeCaption, structuralEqualityPolicy())
        internal set
    var captionText by mutableStateOf(captionText, structuralEqualityPolicy())
        internal set

    fun copy(
        windowFrame: Color = this.windowFrame,
        buttonFace: Color = this.buttonFace,
        buttonHighlight: Color = this.buttonHighlight,
        buttonShadow: Color = this.buttonShadow,
        selection: Color = this.selection,
        activeCaption: Color = this.activeCaption,
        captionText: Color = this.captionText,
    ) = ColorScheme(
        windowFrame,
        buttonFace,
        buttonHighlight,
        buttonShadow,
        selection,
        activeCaption,
        captionText,
    )
}

internal fun ColorScheme.updateColorSchemeFrom(other: ColorScheme) {
    windowFrame = other.windowFrame
    buttonFace = other.buttonFace
    buttonHighlight = other.buttonHighlight
    buttonShadow = other.buttonShadow
    selection = other.selection
    activeCaption = other.activeCaption
    captionText = other.captionText
}

@Stable
class Typography(
    val default: TextStyle,
    val disabled: TextStyle,
    val caption: TextStyle,
    val focused: TextStyle,
)

@Composable
fun Win9xTheme(
    colorScheme: ColorScheme = Win9xTheme.colorScheme,
    typography: Typography = Win9xTheme.typography,
    content: @Composable () -> Unit
) {
    val rememberedColorScheme = remember {
        // Explicitly creating a new object here so we don't mutate the initial [colorScheme]
        // provided, and overwrite the values set in it.
        colorScheme.copy()
    }.apply { updateColorSchemeFrom(colorScheme) }

    CompositionLocalProvider(
        LocalColorScheme provides rememberedColorScheme,
        LocalTypography provides typography,
        LocalIndication provides FocusDashIndication(Dp.Unspecified),
        content = content
    )
}

internal val LocalColorScheme = staticCompositionLocalOf { ColorScheme() }
internal val LocalTypography: ProvidableCompositionLocal<Typography>
    @Composable get() {
        val default = TypographyTokens.defaultTextStyle
        val disabled = TypographyTokens.disabledTextStyle
        val caption = TypographyTokens.captionTextStyle
        val focused = TypographyTokens.focusedTextStyle
        return staticCompositionLocalOf { Typography(default, disabled, caption, focused ) }
    }

object Win9xTheme {
    val colorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

    val typography: Typography
        @Composable
        get() = LocalTypography.current

    val borderWidthDp: Dp = 1.5.dp

    val borderWidthPx: Float
        @Composable
        @ReadOnlyComposable
        get() = with(LocalDensity.current) { borderWidthDp.toPx() }
}
