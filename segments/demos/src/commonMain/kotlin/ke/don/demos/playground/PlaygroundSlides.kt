package ke.don.demos.playground

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.donald_okara.components.guides.code_viewer.FocusKotlinViewer
import io.github.donald_okara.components.guides.code_viewer.KotlinCodeViewerCard
import io.github.donald_okara.components.text.SlideTitle
import ke.don.domain.LocalRevealStep
import ke.don.domain.values.Values

/** Steps for every playground slide: step 1 slides the code card in. */
const val PLAYGROUND_STEPS = 1

/**
 * A demo slide: the playground window on the left and, from reveal step 1, the code card on
 * the right. The window is interactive; drag its right edge or tap a preset.
 */
@Composable
fun PlaygroundDemoSlide(
    title: String,
    stage: PlaygroundStage,
    code: String,
    modifier: Modifier = Modifier,
    kicker: String = "Live demo",
    initialWidthDp: Float = 360f,
    initiallyFolded: Boolean = true,
) {
    val showCode = LocalRevealStep.current >= 1
    Column(
        modifier = modifier.fillMaxSize().padding(Values.Dimens.mediumPadding),
        verticalArrangement = Arrangement.spacedBy(Values.Dimens.mediumPadding),
    ) {
        SlideTitle(title = title, kicker = kicker)
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(Values.Dimens.mediumPadding),
        ) {
            PlaygroundWindowFrame(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                initialWidthDp = initialWidthDp,
            ) {
                PlaygroundApp(stage)
            }
            AnimatedVisibility(
                visible = showCode,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally(),
            ) {
                CodePanel(title = title, code = code, initiallyFolded = initiallyFolded)
            }
        }
    }
}

/**
 * The demo's code in a [KotlinCodeViewerCard], lambdas folded to start with (unless
 * [initiallyFolded] is false) so the shape reads first. The fullscreen button opens [FocusKotlinViewer] for the full, zoomable code.
 */
@Composable
internal fun CodePanel(title: String, code: String, initiallyFolded: Boolean) {
    // Code reads best dark, whatever the deck theme. The card's button still toggles it.
    var darkCode by remember { mutableStateOf(true) }
    var focused by remember { mutableStateOf(false) }

    KotlinCodeViewerCard(
        modifier = Modifier.width(480.dp),
        darkTheme = darkCode,
        toggleTheme = { darkCode = !darkCode },
        toggleFocus = { focused = true },
        initiallyFolded = initiallyFolded,
        code = { code },
    )

    if (focused) {
        FocusKotlinViewer(
            title = title,
            onDismiss = { focused = false },
            darkTheme = darkCode,
            toggleTheme = { darkCode = !darkCode },
            code = { code },
        )
    }
}

// The snippets show the real-app version: they read currentWindowAdaptiveInfo(),
// where the playground reads its simulated window.

val SIZE_CLASSES_CODE = """
val sizeClass = currentWindowAdaptiveInfo().windowSizeClass

val layoutType = when {
    sizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_LARGE_LOWER_BOUND) ->
        NavigationSuiteType.NavigationDrawer
    sizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND) ->
        NavigationSuiteType.NavigationRail
    else -> NavigationSuiteType.NavigationBar
}
""".trimIndent()

val NAVIGATION_CODE = """
NavigationSuiteScaffold(
    layoutType = layoutType, // or leave it to the default
    navigationSuiteItems = {
        Destination.entries.forEach { dest ->
            item(
                selected = dest == current,
                onClick = { current = dest },
                icon = { Icon(dest.icon, dest.label) },
                label = { Text(dest.label) },
            )
        }
    },
) {
    CurrentScreen(current)
}
""".trimIndent()

// The Navigation 3 intro slide: how NavDisplay picks a Scene, and the shape of a custom strategy.
val NAV3_INTRO_CODE = """
NavDisplay(
    backStack = backStack, // a list you own
    onBack = { backStack.removeLastOrNull() },
    // Tried in order: the first Scene wins.
    // None match? You get a single pane.
    sceneStrategies = listOf(
        DialogSceneStrategy<NavKey>(),
        rememberListDetailSceneStrategy<NavKey>(),
    ),
    entryProvider = entryProvider {
        // each entry<Key>(metadata = role)
    },
)

// Need your own layout? Write a strategy.
class TwoPaneStrategy<T : Any>(
    private val wide: Boolean,
) : SceneStrategy<T> {
    override fun SceneStrategyScope<T>.calculateScene(
        entries: List<NavEntry<T>>,
    ): Scene<T>? {
        if (!wide || entries.size < 2) return null // pass
        return TwoPaneScene(entries.takeLast(2))
    }
}
""".trimIndent()

// Demos 3 and 4 show Navigation 3: one NavDisplay over a back stack you own, and a scene
// strategy that decides how many entries share the screen. No navigator, no coroutine scope.

val LIST_DETAIL_CODE = """
@Serializable data object Inbox : NavKey
@Serializable data class Conversation(val id: Int) : NavKey

// The back stack is just a list. You own it.
val backStack = rememberNavBackStack(Inbox)

NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    // One pane or two? The strategy decides.
    sceneStrategies = listOf(
        rememberListDetailSceneStrategy<NavKey>()
    ),
    entryProvider = entryProvider {
        entry<Inbox>(
            metadata = ListDetailSceneStrategy.listPane(
                detailPlaceholder = { PickAConversation() }
            )
        ) {
            InboxScreen(onOpen = { id ->
                backStack.add(Conversation(id))
            })
        }
        entry<Conversation>(
            metadata = ListDetailSceneStrategy.detailPane()
        ) { key ->
            ConversationScreen(key.id)
        }
    },
)
""".trimIndent()

val SUPPORTING_PANE_CODE = """
@Serializable data object Document : NavKey
@Serializable data object Contents : NavKey

val backStack = rememberNavBackStack(Document)

NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    sceneStrategies = listOf(
        rememberSupportingPaneSceneStrategy<NavKey>(
            // Back dismisses the supporting pane
            backNavigationBehavior = BackNavigationBehavior
                .PopUntilCurrentDestinationChange,
        )
    ),
    entryProvider = entryProvider {
        entry<Document>(
            metadata = SupportingPaneSceneStrategy.mainPane()
        ) {
            DocumentScreen(onContents = {
                backStack.add(Contents)
            })
        }
        entry<Contents>(
            metadata = SupportingPaneSceneStrategy.supportingPane()
        ) {
            ContentsAndProperties()
        }
    },
)
""".trimIndent()

val CONTENT_CODE = """
FlexBox(config = {
    wrap(FlexWrap.Wrap)
    gap(8.dp)
}) {
    filters.forEach { FilterChip(...) }
}

Grid(config = {
    val width = constraints.maxWidth.toDp()
    val columns = if (width < 600.dp) 2 else 4
    val cell = (width - 12.dp * (columns - 1)) / columns
    repeat(columns) { column(cell) }
    repeat(rows) { row(cell * 0.75f) }
    gap(12.dp)
}) { photos.forEach { Photo(it) } }

Card(Modifier.fillMaxWidth().widthIn(max = 560.dp)) { Form() }
""".trimIndent()

val STAGGERED_GRID_CODE = """
LazyVerticalStaggeredGrid(
    columns = StaggeredGridCells.Adaptive(minSize = 160.dp),
    verticalItemSpacing = 12.dp,
    horizontalArrangement = Arrangement.spacedBy(12.dp),
) {
    item(span = StaggeredGridItemSpan.FullLine) {
        Header("Feed")
    }
    items(posts) { post ->
        PostCard(post) // each card picks its own height
    }
}
""".trimIndent()
