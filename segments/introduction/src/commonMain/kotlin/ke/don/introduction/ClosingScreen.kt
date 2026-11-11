package ke.don.introduction

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.donald_okara.components.charts.Confetti
import ke.don.design.brand.DevFestWordmark
import ke.don.design.theme.DevFestColors
import ke.don.domain.WORDMARK_SHARED_KEY
import ke.don.domain.deckSharedBounds
import ke.don.resources.Resources
import kotlinx.coroutines.delay

/**
 * Closing slide: the wordmark grows back out of the header, a "Thank you!" in moving Google
 * colours, the speaker photo, and confetti bursts. Click anywhere for an extra burst.
 */
@Composable
fun ClosingScreen(
    modifier: Modifier = Modifier,
    repoUrl: String = "github.com/donald-okara/DevFest-Nairobi-2026",
) {
    // Confetti fires on a loop; each burst needs its trigger to go false, then true.
    var burst by remember { mutableStateOf(false) }
    var extraBursts by remember { mutableIntStateOf(0) }
    LaunchedEffect(extraBursts) {
        while (true) {
            burst = true
            delay(1300)
            burst = false
            delay(2200)
        }
    }

    val shimmer by rememberInfiniteTransition(label = "thanks").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing)),
        label = "thanks-shimmer",
    )
    val width = 1600f
    val gradient = Brush.linearGradient(
        colors = DevFestColors.cycle + DevFestColors.cycle.first(),
        start = Offset(-width * shimmer, 0f),
        end = Offset(width * (1 - shimmer), 0f),
        tileMode = TileMode.Repeated,
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { extraBursts++ },
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                DevFestWordmark(letterSize = 72.sp, modifier = Modifier.deckSharedBounds(WORDMARK_SHARED_KEY))
                Spacer(Modifier.height(40.dp))
                Text(
                    "Thank you!",
                    style = MaterialTheme.typography.displayLarge.merge(TextStyle(brush = gradient)),
                    fontWeight = FontWeight.Black,
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    "Meet your users where they are.",
                    style = MaterialTheme.typography.headlineMedium,
                )
                Spacer(Modifier.height(32.dp))
                Text(
                    "Donald Isoe",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    "Slides and demos: $repoUrl",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            SpeakerPhoto(image = Resources.Images.DON, size = 400.dp)
        }
        Confetti(trigger = burst, modifier = Modifier.fillMaxSize())
    }
}
