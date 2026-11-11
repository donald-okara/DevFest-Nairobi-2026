package ke.don.ski.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ke.don.design.brand.DevFestWordmark
import io.github.donald_okara.components.DotBullet
import io.github.donald_okara.components.timer.TimerComponent
import ke.don.domain.DeckMode
import ke.don.domain.WORDMARK_SHARED_KEY
import ke.don.domain.deckSharedBounds
import ke.don.domain.timer.TimerIntentHandler
import ke.don.domain.timer.TimerState


/**
 * Provides a footer composable for the current slide when the slide requests a footer.
 *
 */

@Composable
fun MainFooter(
    modifier: Modifier = Modifier,
    showTimer: Boolean,
    label: String,
    timerState: TimerState,
    onIntent: (TimerIntentHandler) -> Unit,
    transitionSpec: ContentTransform? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.animateContentSize()
    ) {
        if (showTimer) {
            TimerComponent(
                timerState = timerState, onIntent = onIntent
            )
        }

        Row(
            modifier = Modifier
                .animateContentSize()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DotBullet()

            Spacer(Modifier.width(10.dp))

            val labelText: @Composable (String) -> Unit = { text ->
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            transitionSpec?.let {
                AnimatedContent(
                    targetState = label, transitionSpec = {
                        transitionSpec
                    }) { text ->
                    labelText(text)
                }
            } ?: labelText(label)
        }
    }
}


/**
 * Deck header: the animated DEVFEST Nairobi 2026 wordmark, top-left as in the template.
 *
 * In the presenter panel ([DeckMode.Local]) a warning sits beside it so the panel is never
 * mistaken for the audience window. The audience window shows the wordmark alone.
 *
 * @param mode The deck mode used to decide whether to show the presenter warning.
 */
@Composable
fun MainHeader(
    mode: DeckMode,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        DevFestWordmark(letterSize = 48.sp, modifier = Modifier.deckSharedBounds(WORDMARK_SHARED_KEY))

        if (mode == DeckMode.Local) {
            Text(
                "Presenter's panel (do not present)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
