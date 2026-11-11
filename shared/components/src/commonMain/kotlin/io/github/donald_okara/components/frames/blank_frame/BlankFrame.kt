package io.github.donald_okara.components.frames.blank_frame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ke.don.domain.frames.SkiFrame
import ke.don.domain.values.Values

/**
 * A frame that draws nothing: no border, no surface, no curve. The header and
 * footer stack above and below the content, and the slide sits on the bare
 * deck background.
 */
object BlankFrame : SkiFrame {
    @Composable
    override fun Render(
        modifier: Modifier,
        header: @Composable (() -> Unit)?,
        footer: @Composable (() -> Unit)?,
        content: @Composable (BoxScope.() -> Unit)
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Values.Dimens.mediumPadding),
        ) {
            header?.let { Box(Modifier.fillMaxWidth()) { it() } }
            Box(Modifier.weight(1f).fillMaxWidth(), content = content)
            footer?.let { Box(Modifier.fillMaxWidth()) { it() } }
        }
    }
}
