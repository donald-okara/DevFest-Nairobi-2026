package ke.don.ski.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import ke.don.ski.Deck
import ke.don.domain.DeckMode
import ke.don.domain.DeckSyncState
import ke.don.domain.DeckNavigator
import ke.don.ski.presentation.ui.skiPresentationSlides
import kotlinx.browser.window
import kotlinx.serialization.json.Json
import org.w3c.dom.StorageEvent
import org.w3c.dom.events.Event

/**
 * The web deck.
 *
 * - `/`: the presenter panel (notes, timer, table of contents). It writes the current slide to
 *   `localStorage` and opens the audience deck (`/?slides`) in a pop-up. If the browser blocks
 *   the pop-up on load, it opens on the first click or key press instead.
 * - `/?slides`: the audience deck, clean output. It follows the presenter tab through
 *   `localStorage`, and can also be navigated on its own with the arrow keys. Share this one.
 */
@Composable
fun DeckWebImpl() {
    val slides = skiPresentationSlides()
    val navigator = remember { DeckNavigator(slides) }

    val isPresenter = !window.location.search.contains("slides")

    if (isPresenter) {
        LaunchedEffect(navigator.currentIndex, navigator.currentStep, navigator.direction) {
            val syncState = DeckSyncState(
                slideIndex = navigator.currentIndex,
                direction = navigator.direction,
                step = navigator.currentStep
            )
            window.localStorage.setItem(
                "deckState",
                Json.encodeToString(syncState)
            )
        }

        DisposableEffect(Unit) {
            // Browsers block pop-ups that aren't triggered by the user. Try on load; if blocked,
            // retry on the first click or key press, which counts as a user gesture.
            var opened = openSlides()
            val retry: (Event) -> Unit = {
                if (!opened) opened = openSlides()
            }
            if (!opened) {
                window.addEventListener("pointerdown", retry, true)
                window.addEventListener("keydown", retry, true)
            }
            onDispose {
                window.removeEventListener("pointerdown", retry, true)
                window.removeEventListener("keydown", retry, true)
            }
        }
    } else {
        DisposableEffect(Unit) {
            val listener: (org.w3c.dom.events.Event) -> Unit = { e ->
                val event = e as? StorageEvent
                if (event?.key == "deckState") {
                    val syncState = event.newValue
                        ?.let { raw -> runCatching { Json.decodeFromString<DeckSyncState>(raw) }.getOrNull() }
                    if (syncState != null && syncState.slideIndex in slides.indices) {
                        navigator.goTo(syncState.slideIndex, syncState.step)
                    }
                }
            }
            window.addEventListener("storage", listener)
            onDispose {
                window.removeEventListener("storage", listener)
            }
        }
    }

    Deck(
        slides = slides,
        navigator = navigator,
        mode = if (isPresenter) DeckMode.Local else DeckMode.Presenter
    )
}

/** Opens (or refocuses) the audience deck. Returns false if the browser blocked the pop-up. */
fun openSlides(): Boolean =
    window.open("/?slides", "AudienceSlides", "width=1280,height=800") != null
