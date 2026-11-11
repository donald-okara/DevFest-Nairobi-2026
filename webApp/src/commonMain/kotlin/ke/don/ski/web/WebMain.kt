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

/**
 * The web deck.
 *
 * - `/` (and `/?slides`): the audience deck, clean output with no presenter chrome. It is the
 *   default so a shared link lands on the slides. Navigate with the arrow keys. It also follows a
 *   presenter tab, if one is open, through `localStorage`.
 * - `/?presenter`: the presenter panel (notes, timer, table of contents). It writes the current
 *   slide to `localStorage` and opens the audience deck as a popup.
 */
@Composable
fun DeckWebImpl() {
    val slides = skiPresentationSlides()
    val navigator = remember { DeckNavigator(slides) }

    val isPresenter = window.location.search.contains("presenter")

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

        LaunchedEffect(Unit) {
            openSlides()
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

/** Opens the audience deck from the presenter panel. Browsers may block it until you allow pop-ups. */
fun openSlides() {
    window.open("/?slides", "AudienceSlides", "width=1280,height=800")
}
