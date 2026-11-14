package ke.don.ski.navigation

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.abs

/** The direction the finger travelled. Swiping left moves the deck forward, like turning a page. */
enum class SwipeDirection { Left, Right, Up, Down }

/** Minimum travel along the dominant axis before a gesture counts as a swipe. */
private val SwipeThreshold = 64.dp

/**
 * Detects single-finger touch (or stylus) swipes and reports their direction.
 *
 * Mouse drags are ignored, so selecting text or dragging on desktop never changes slides. The
 * detector runs after the slide's own content, so a gesture is dropped as soon as a child consumes
 * it: scrolling lists, pagers and text fields keep working. Multi-touch gestures (pinch) are
 * dropped too.
 */
fun Modifier.deckSwipeGestures(
    enabled: Boolean = true,
    onSwipe: (SwipeDirection) -> Unit,
): Modifier = if (!enabled) this else composed {
    val currentOnSwipe by rememberUpdatedState(onSwipe)
    pointerInput(Unit) {
        val threshold = SwipeThreshold.toPx()
        awaitEachGesture {
            val down = awaitFirstDown(requireUnconsumed = false)
            if (down.type != PointerType.Touch && down.type != PointerType.Stylus) return@awaitEachGesture

            var end = down.position
            var cancelled = false
            while (true) {
                val event = awaitPointerEvent()
                if (event.changes.count { it.pressed } > 1) cancelled = true
                val change = event.changes.firstOrNull { it.id == down.id } ?: break
                if (change.isConsumed) cancelled = true
                end = change.position
                if (!change.pressed) break
            }
            if (cancelled) return@awaitEachGesture

            val delta: Offset = end - down.position
            val direction = when {
                abs(delta.x) >= abs(delta.y) && abs(delta.x) > threshold ->
                    if (delta.x < 0) SwipeDirection.Left else SwipeDirection.Right
                abs(delta.y) > abs(delta.x) && abs(delta.y) > threshold ->
                    if (delta.y < 0) SwipeDirection.Up else SwipeDirection.Down
                else -> null
            }
            direction?.let(currentOnSwipe)
        }
    }
}
