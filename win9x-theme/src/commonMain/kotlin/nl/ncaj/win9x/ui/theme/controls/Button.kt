package nl.ncaj.win9x.ui.theme.controls

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.ncaj.win9x.ui.theme.Win9xBorder
import nl.ncaj.win9x.ui.theme.Win9xTheme
import nl.ncaj.win9x.ui.theme.rememberVectorResourcePainter
import nl.ncaj.win9x.ui.theme.win9xBorder

@Composable
internal fun ButtonPreview() {
    Column {
        Text("- Button -")
        Spacer(modifier = Modifier.height(2.dp))
        Button(onClick = {}) {
            Text("Default")
        }
        Spacer(modifier = Modifier.height(2.dp))
        Button(onClick = {}, enabled = false) {
            Text("Disabled", enabled = false)
        }
        Spacer(modifier = Modifier.height(2.dp))
        Button(onClick = {}, Modifier.size(20.dp)) {
            Image(
                painter = rememberVectorResourcePainter("vector_images/ic_cross.xml"),
                contentDescription = ""
            )
        }
    }
}

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    borders: Win9xButtonBorders = defaultButtonBorders(),
    background: Color = Win9xTheme.colorScheme.buttonFace,
    enabled: Boolean = true,
    defaultPadding: PaddingValues = PaddingValues(4.dp),
    interactionSource: MutableInteractionSource = remember(enabled) { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = modifier
            .defaultMinSize(75.dp, 23.dp)
            .background(background)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick,
            )
            .then(
                if (enabled && isPressed) Modifier.win9xBorder(borders.pressed)
                else Modifier.win9xBorder(borders.normal)
            )
            .padding(defaultPadding)
            .then(if (enabled && isPressed) Modifier.offset(1.dp, 1.dp) else Modifier),
        contentAlignment = Alignment.Center,
        content = { content() }
    )
}

class Win9xButtonBorders(
    val normal: Win9xBorder,
    val pressed: Win9xBorder
) {
    fun copy(
        normal: Win9xBorder = this.normal,
        pressed: Win9xBorder = this.pressed
    ) = Win9xButtonBorders(normal, pressed)
}

@Composable
internal fun defaultButtonBorders() = Win9xButtonBorders(
    normal = Win9xBorder(
        outerStartTop = Win9xTheme.colorScheme.buttonHighlight,
        innerStartTop = Win9xTheme.colorScheme.buttonFace,
        outerEndBottom = Win9xTheme.colorScheme.buttonShadow,
        innerEndBottom = Win9xTheme.colorScheme.windowFrame,
        borderWidth = Win9xTheme.borderWidthPx
    ),
    pressed = Win9xBorder(
        outerStartTop = Win9xTheme.colorScheme.buttonShadow,
        innerStartTop = Win9xTheme.colorScheme.windowFrame,
        outerEndBottom = Win9xTheme.colorScheme.buttonHighlight,
        innerEndBottom = Win9xTheme.colorScheme.buttonFace,
        borderWidth = Win9xTheme.borderWidthPx
    )
)

@Composable
internal fun innerButtonBorders() = Win9xButtonBorders(
    normal = Win9xBorder(
        outerStartTop = Win9xTheme.colorScheme.buttonFace,
        innerStartTop = Win9xTheme.colorScheme.buttonHighlight,
        outerEndBottom = Win9xTheme.colorScheme.windowFrame,
        innerEndBottom = Win9xTheme.colorScheme.buttonShadow,
        borderWidth = Win9xTheme.borderWidthPx
    ),
    pressed = Win9xBorder(
        outerStartTop = Win9xTheme.colorScheme.windowFrame,
        innerStartTop = Win9xTheme.colorScheme.buttonShadow,
        outerEndBottom = Win9xTheme.colorScheme.buttonFace,
        innerEndBottom = Win9xTheme.colorScheme.buttonHighlight,
        borderWidth = Win9xTheme.borderWidthPx
    )
)