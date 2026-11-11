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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.donald_okara.components.guides.code_viewer.KotlinCodeViewer
import io.github.donald_okara.components.text.SlideTitle
import ke.don.domain.LocalRevealStep
import ke.don.domain.values.Values

/** Steps for every playground slide: step 1 slides the code card in. */
const val PLAYGROUND_STEPS = 1

/**
 * A demo slide: the playground window on the left and, from reveal step 1, the code that
 * matters on the right. The window is interactive; drag its right edge or tap a preset.
 */
@Composable
fun PlaygroundDemoSlide(
    title: String,
    stage: PlaygroundStage,
    code: String,
    modifier: Modifier = Modifier,
    initialWidthDp: Float = 360f,
) {
    val showCode = LocalRevealStep.current >= 1
    Column(
        modifier = modifier.fillMaxSize().padding(Values.Dimens.mediumPadding),
        verticalArrangement = Arrangement.spacedBy(Values.Dimens.mediumPadding),
    ) {
        SlideTitle(title = title, kicker = "Live demo")
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
                KotlinCodeViewer(
                    code = code,
                    shouldFoldLambdas = false,
                    modifier = Modifier.width(460.dp).fillMaxHeight(),
                )
            }
        }
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

val LIST_DETAIL_CODE = """
val navigator = rememberListDetailPaneScaffoldNavigator<Int>()
val scope = rememberCoroutineScope()

ListDetailPaneScaffold(
    directive = navigator.scaffoldDirective,
    value = navigator.scaffoldValue,
    listPane = {
        AnimatedPane {
            Inbox(onOpen = { id ->
                scope.launch {
                    navigator.navigateTo(Detail, id)
                }
            })
        }
    },
    detailPane = {
        AnimatedPane {
            Conversation(navigator.currentDestination?.contentKey)
        }
    },
)
""".trimIndent()

val SUPPORTING_PANE_CODE = """
val navigator = rememberSupportingPaneScaffoldNavigator()

SupportingPaneScaffold(
    directive = navigator.scaffoldDirective,
    value = navigator.scaffoldValue,
    mainPane = {
        AnimatedPane { Document() }
    },
    supportingPane = {
        AnimatedPane { ContentsAndProperties() }
    },
)

// Hidden on compact? Offer a button:
navigator.scaffoldValue[Supporting] == PaneAdaptedValue.Hidden
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
