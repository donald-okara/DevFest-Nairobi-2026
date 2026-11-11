package ke.don.demos.talk

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import io.github.donald_okara.components.reveal.RevealItem
import io.github.donald_okara.components.text.SlideTitle
import ke.don.demos.playground.CONTENT_CODE
import ke.don.demos.playground.LIST_DETAIL_CODE
import ke.don.demos.playground.NAVIGATION_CODE
import ke.don.demos.playground.PlaygroundDemoSlide
import ke.don.demos.playground.PlaygroundStage
import ke.don.demos.playground.SIZE_CLASSES_CODE
import ke.don.demos.playground.STAGGERED_GRID_CODE
import ke.don.demos.playground.SUPPORTING_PANE_CODE
import ke.don.domain.values.Values

// Slide bodies for "Adaptive UI in Jetpack Compose". The script is docs/talk.md;
// slide numbers in the comments match it. Each *_STEPS constant is the slide's
// `steps` value in the deck.

// Part 1: The setup

// Part 2: Why should you care?

private val bottomLinePoints = listOf(
    "Under two hours of screen time a day",
    "Real work happens on a PC: Notion, Claude, Canva, Docs",
    "A keyboard, more space, more information at once",
    "Big-screen buyers already had a need for the space",
)
val BOTTOM_LINE_STEPS = bottomLinePoints.size

/** Slide 2. */
@Composable
fun BottomLineSlide() = PointsSlide(
    title = "Forget Google's bottom line",
    kicker = "What's in it for you?",
    points = bottomLinePoints,
)

// Part 3: Why mobile still matters

/** Slide 5. */
@Composable
fun AbandonMobileSlide() = StatementSlide(
    statement = "So do we abandon mobile?",
    subline = "I don't want a productivity suite. I want a button.",
)
const val ABANDON_MOBILE_STEPS = 1

private val compactPoints = listOf(
    "Widgets, app shortcuts, notification actions, Quick Settings tiles",
    "Shorten the distance from intention to done",
    "Camera, location, NFC, biometrics: things only the phone has",
    "Sometimes the best UX is finishing in ten seconds",
)
val COMPACT_INTERACTIONS_STEPS = compactPoints.size

/** Slide 6. */
@Composable
fun CompactInteractionsSlide() = PointsSlide(
    title = "The power of compact interactions",
    kicker = "Intention → interaction → done",
    points = compactPoints,
)

// Part 4: The transition

/** Slide 8. The abandoned "but". Don't explain it. */
@Composable
fun AccommodateSlide() = StatementSlide(
    statement = "I know this makes me sound like I'm trying to convince you to accommodate my needs…",
    subline = "Anyway. Let's talk about Compose.",
)
const val ACCOMMODATE_STEPS = 1

// Part 5: The how

private val adaptiveMeaning = listOf(
    "Bottom bar or navigation rail?",
    "One after another, or side by side?",
    "A list, or a list with a detail pane?",
    "Full width, or a maximum width?",
)
val ADAPTIVE_MEANING_STEPS = adaptiveMeaning.size

/** Slide 9. */
@Composable
fun AdaptiveMeaningSlide() = PointsSlide(
    title = "What does adaptive UI actually mean?",
    kicker = "Responsive resizes. Adaptive decides.",
    points = adaptiveMeaning,
)

private val windowPoints = listOf(
    "A tablet in split screen",
    "A foldable on its outer display",
    "A resized desktop window",
    "Freeform windowing",
)
val WINDOW_CONSTRAINT_STEPS = windowPoints.size + 1

/** Slide 10. */
@Composable
fun WindowConstraintSlide(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        PointsSlide(
            title = "The window is your constraint",
            kicker = "Not the device",
            points = windowPoints,
            modifier = Modifier.weight(1f),
        )
        RevealItem(index = windowPoints.size + 1) {
            Text(
                text = "How much space does my app actually have?",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = Values.Dimens.largePadding),
            )
        }
    }
}

/** Slide 11, Demo 1. */
@Composable
fun WindowSizeClassesSlide() = PlaygroundDemoSlide(
    title = "Window size classes",
    stage = PlaygroundStage.SizeClasses,
    code = SIZE_CLASSES_CODE,
)

/** Slide 12, Demo 2. */
@Composable
fun AdaptiveNavigationSlide() = PlaygroundDemoSlide(
    title = "Adaptive navigation",
    stage = PlaygroundStage.Navigation,
    code = NAVIGATION_CODE,
)

/** Slide 13, Demo 3. */
@Composable
fun ListDetailSlide() = PlaygroundDemoSlide(
    title = "List-detail layouts",
    stage = PlaygroundStage.ListDetail,
    code = LIST_DETAIL_CODE,
)

/** Slide 14, Demo 4. */
@Composable
fun SupportingPaneSlide() = PlaygroundDemoSlide(
    title = "Supporting panes",
    stage = PlaygroundStage.SupportingPane,
    code = SUPPORTING_PANE_CODE,
)

/** Slide 15, Demo 5: Grid, FlexBox and a capped form width. */
@Composable
fun AdaptiveContentSlide() = PlaygroundDemoSlide(
    title = "It's not just scaffolds",
    stage = PlaygroundStage.Content,
    code = CONTENT_CODE,
)

/** Slide 17, Demo 6: staggered grids. */
@Composable
fun StaggeredGridSlide() = PlaygroundDemoSlide(
    title = "Staggered grids",
    stage = PlaygroundStage.StaggeredGrid,
    code = STAGGERED_GRID_CODE,
)

private val unblockerPoints = listOf(
    "One codebase: Android, desktop, web and iOS",
    "Size classes and the adaptive scaffolds run on desktop too",
    "The work you do for tablets is most of your desktop app",
    "This deck is a Compose Multiplatform app. So is every demo you just saw.",
)
val UNBLOCKER_STEPS = unblockerPoints.size

/** Slide 18: Compose Multiplatform as the biggest unblocker. */
@Composable
fun UnblockerSlide() = PointsSlide(
    title = "The biggest unblocker",
    kicker = "Compose Multiplatform",
    points = unblockerPoints,
)

// Part 6: The takeaway

private val takeaways = listOf(
    "It's not a phone app. Think about people and their contexts.",
    "The device doesn't decide the space. The window does.",
    "Don't stretch layouts. Change the experience.",
    "Be useful in more situations, not on more devices.",
)
val TAKEAWAY_STEPS = takeaways.size

/** Slide 16. */
@Composable
fun TakeawaySlide() = PointsSlide(
    title = "Meet them where they are",
    kicker = "What to take home",
    points = takeaways,
)
