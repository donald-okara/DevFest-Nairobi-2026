package ke.don.ski

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import io.github.donald_okara.components.frames.blank_frame.BlankFrame
import io.github.donald_okara.components.frames.defaultSkiFrames
import ke.don.domain.frames.LocalSkiFrames
import ke.don.domain.DeckMode
import ke.don.domain.DeckNavigator
import ke.don.domain.LocalDeckMode
import ke.don.domain.SlideConfig
import ke.don.ski.presentation.PresentationDeck
import ke.don.ski.presentation.ui.skiPresentationSlides

/**
 * Composable entry point that renders the slide deck UI with theme support and navigation.
 *
 * The DevFest Nairobi 2026 deck is frameless: slides and presenter panels sit on the
 * plain theme background with no border, pattern or decorator image.
 *
 * @param navigator Navigator responsible for slide/screen navigation within the deck.
 * @param mode Mode to use for the deck (for example local or remote presentation behavior).
 * @param slides List of slide configurations to display in the deck.
 */
@Composable
fun Deck(
    mode: DeckMode = DeckMode.Local,
    slides: List<SlideConfig> = skiPresentationSlides(),
    navigator: DeckNavigator = remember { DeckNavigator(slides) },
) {
    CompositionLocalProvider(
        // Only for slides that build a frame themselves (the Ski frame demos); the deck chrome uses none.
        LocalSkiFrames provides defaultSkiFrames(),
        LocalDeckMode provides mode
    ) {
        PresentationDeck(
            mainFrame = BlankFrame,
            guidesFrame = BlankFrame,
            background = null,
            navigator = navigator,
            slides = slides,
            shareFrame = true
        )
    }
}
