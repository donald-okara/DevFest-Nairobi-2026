package ke.don.domain

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SharedTransitionScope.ResizeMode.Companion.scaleToBounds
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier

/** The deck's [SharedTransitionScope], set around the frame so the header and slides share it. Null outside a deck. */
val LocalDeckSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }

/**
 * The [AnimatedVisibilityScope] of whatever is animating this content in or out: the slide's
 * `AnimatedContent` inside the deck, or the header's `AnimatedVisibility`. Null outside a deck.
 */
val LocalDeckAnimatedScope = compositionLocalOf<AnimatedVisibilityScope?> { null }

/** Shared-element key for the DevFest wordmark, which moves from the title slide into the header. */
const val WORDMARK_SHARED_KEY = "devfest-wordmark"

/**
 * Marks this content as a shared element across slides and the deck header. Content with the same
 * [key] morphs between its old and new bounds, scaled to fit, as one leaves and the other enters.
 * Does nothing outside a deck.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Modifier.deckSharedBounds(key: Any): Modifier {
    val transitionScope = LocalDeckSharedTransitionScope.current ?: return this
    val animatedScope = LocalDeckAnimatedScope.current ?: return this
    return with(transitionScope) {
        this@deckSharedBounds.sharedBounds(
            sharedContentState = rememberSharedContentState(key),
            animatedVisibilityScope = animatedScope,
            resizeMode = scaleToBounds(),
        )
    }
}
