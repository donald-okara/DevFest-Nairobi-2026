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
import ke.don.domain.values.Values

// Slide bodies for "Adaptive UI in Jetpack Compose". The script is docs/talk.md;
// slide numbers in the comments match it. Each *_STEPS constant is the slide's
// `steps` value in the deck.

// Part 1: The setup

private val feedbackLoop = listOf(
    TalkColumn("Better apps", "Apps that use the space"),
    TalkColumn("More useful devices", "The foldable is worth opening"),
    TalkColumn("More users", "People buy the hardware"),
    TalkColumn("More incentive", "Developers build better apps"),
)
val FEEDBACK_LOOP_STEPS = feedbackLoop.size

/** Slide 1, second half: the chicken-and-egg loop. */
@Composable
fun FeedbackLoopSlide() = ColumnsSlide(
    title = "Chicken, meet egg",
    kicker = "No offence, Google",
    columns = feedbackLoop,
)

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

private data class Stat(val figure: String, val label: String)

private val stats = listOf(
    Stat("580M+", "large-screen Android devices in use"),
    Stat("9x", "more spent by phone + large-screen owners"),
    Stat("14x", "more spent by foldable users"),
    Stat("~3x", "more time in-app, phone + tablet (entertainment, US)"),
)
val FOLLOW_THE_MONEY_STEPS = stats.size + 1

/** Slide 3: one number per step, then the correlation caveat. */
@Composable
fun FollowTheMoneySlide(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Values.Dimens.largePadding),
        verticalArrangement = Arrangement.spacedBy(Values.Dimens.largePadding),
    ) {
        SlideTitle(title = "Follow the money", kicker = "Source: Google")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Values.Dimens.mediumPadding),
        ) {
            stats.forEachIndexed { i, stat ->
                RevealItem(index = i + 1, modifier = Modifier.weight(1f)) {
                    Text(
                        text = stat.figure,
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(stat.label, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
        RevealItem(index = stats.size + 1) {
            Text(
                text = "Correlation, not causation. Still your users.",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Start,
            )
        }
    }
}

private val appsDoingThis = listOf(
    TalkColumn("Notion", "Databases on desktop, quick capture on mobile"),
    TalkColumn("Canva", "Edit anywhere, design properly on a big screen"),
    TalkColumn("Lightroom", "Shoot on the phone, finish on desktop"),
    TalkColumn("Figma", "Mobile is for reviewing, not authoring"),
    TalkColumn("Power BI", "Build on desktop, check the metric on the phone"),
)
val APPS_DOING_THIS_STEPS = appsDoingThis.size

/** Slide 4. */
@Composable
fun AppsDoingThisSlide() = ColumnsSlide(
    title = "Apps already doing this",
    kicker = "Same product, different jobs",
    columns = appsDoingThis,
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

private val contexts = listOf(
    TalkColumn("Desktop", "Deep engagement"),
    TalkColumn("Tablet / foldable", "Expanded engagement"),
    TalkColumn("Phone", "Immediate engagement"),
)
val CONTEXTS_STEPS = contexts.size

/** Slide 7. */
@Composable
fun ContextsSlide() = ColumnsSlide(
    title = "Different devices, different contexts",
    kicker = "Tendencies, not rules",
    columns = contexts,
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
fun WindowSizeClassesSlide() = DemoPlaceholderSlide(
    title = "Window size classes",
    api = "currentWindowAdaptiveInfo().windowSizeClass",
    show = listOf(
        "Read the current size class",
        "Resize the window across compact, medium, expanded",
        "Switch navigation on the width class",
    ),
)

/** Slide 12, Demo 2. */
@Composable
fun AdaptiveNavigationSlide() = DemoPlaceholderSlide(
    title = "Adaptive navigation",
    api = "NavigationSuiteScaffold",
    show = listOf(
        "Bottom bar at compact width",
        "Rail, then drawer as the window widens",
        "Same app, not a second one",
    ),
)

/** Slide 13, Demo 3. */
@Composable
fun ListDetailSlide() = DemoPlaceholderSlide(
    title = "List-detail layouts",
    api = "ListDetailPaneScaffold",
    show = listOf(
        "Compact: list and detail are separate destinations",
        "Expanded: side by side",
        "Selection survives the resize",
    ),
)

/** Slide 14, Demo 4. */
@Composable
fun SupportingPaneSlide() = DemoPlaceholderSlide(
    title = "Supporting panes",
    api = "SupportingPaneScaffold",
    show = listOf(
        "Compact: supporting content behind an interaction",
        "Expanded: stays visible",
        "More utility, not more whitespace",
    ),
)

/** Slide 15, Demo 5. */
@Composable
fun AdaptiveContentSlide() = DemoPlaceholderSlide(
    title = "It's not just scaffolds",
    api = "Grids, max widths, input",
    show = listOf(
        "Adaptive grid column count",
        "Form capped at a maximum width",
        "Touch, keyboard, mouse, stylus",
    ),
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

/** Slide 17. */
@Composable
fun ThankYouSlide(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Thank you!", style = MaterialTheme.typography.displayLarge, fontWeight = FontWeight.Bold)
        Text(
            "Donald Isoe · Adaptive UI in Jetpack Compose",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
