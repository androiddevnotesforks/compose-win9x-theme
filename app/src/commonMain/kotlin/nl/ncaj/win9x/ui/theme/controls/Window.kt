package nl.ncaj.win9x.ui.theme.controls

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import nl.ncaj.win9x.ui.theme.Win9xTheme
import nl.ncaj.win9x.ui.theme.sunkenBorder
import nl.ncaj.win9x.ui.theme.windowBorder

@Composable
internal fun WindowPreview() {
    Window(
        title = "Title",
        menuBar = {
            entry("Item1") {
                label("Sub menu item 1") {}
            }
            entry("Item2") {
                label("Sub menu item 1") {}
                cascade("Sub menu item 2") {
                    label("Cascade menu item 1") {}
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.White)
                .sunkenBorder()
        )
    }
}

@Composable
fun TitleBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .background(Win9xTheme.colorScheme.activeCaption)
            .height(18.dp)
            .defaultMinSize(minWidth = 100.dp)
            .padding(horizontal = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

@Composable
fun TitleBar(
    title: String,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    buttons: (@Composable () -> Unit)? = null,
) {
    TitleBar(modifier) {
        icon?.invoke()
        Text(
            text = title,
            style = Win9xTheme.typography.caption,
            modifier = Modifier.padding(horizontal = 2.dp)
        )
        Spacer(Modifier.weight(1f))
        buttons?.invoke()
    }
}

@Composable
fun Window(
    title: String,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    menuBar: (MenuBarScope.() -> Unit)? = null,
    statusBar: (StatusBarScope.() -> Unit)? = null,
    buttons: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Window(
        modifier = modifier,
        menuBar = menuBar,
        statusBar = statusBar,
        content = content,
        titleBar = {
            TitleBar(
                title = title,
                icon = icon,
                buttons = buttons
            )
        }
    )
}

@Composable
fun Window(
    titleBar: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    menuBar: (MenuBarScope.() -> Unit)? = null,
    statusBar: (StatusBarScope.() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .background(Win9xTheme.colorScheme.buttonFace)
            .windowBorder()
            .padding(Win9xTheme.borderWidthDp + 2.dp)
            .defaultMinSize(minHeight = 100.dp)
    ) {
        titleBar()

        menuBar?.let { MenuBar(content = it) }

        Spacer(modifier = Modifier.height(2.dp))

        Box(Modifier.weight(1f)) {
            content()
        }

        statusBar?.let {
            Spacer(modifier = Modifier.height(2.dp))
            StatusBar(content = it)
        }
    }
}

@Composable
fun TitleButton(
    painter: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier.size(14.dp),
        enabled = enabled,
        defaultPadding = PaddingValues(),
        interactionSource = interactionSource,
        borders = innerButtonBorders(),
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
        )
    }
}