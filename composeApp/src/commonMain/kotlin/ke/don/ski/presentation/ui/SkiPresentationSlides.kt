package ke.don.ski.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import ke.don.demos.talk.ABANDON_MOBILE_STEPS
import ke.don.demos.talk.ACCOMMODATE_STEPS
import ke.don.demos.talk.ADAPTIVE_MEANING_STEPS
import ke.don.demos.talk.APPS_DOING_THIS_STEPS
import ke.don.demos.talk.AbandonMobileSlide
import ke.don.demos.talk.AccommodateSlide
import ke.don.demos.talk.AdaptiveContentSlide
import ke.don.demos.talk.AdaptiveMeaningSlide
import ke.don.demos.talk.AdaptiveNavigationSlide
import ke.don.demos.talk.AppsDoingThisSlide
import ke.don.demos.talk.BOTTOM_LINE_STEPS
import ke.don.demos.talk.BottomLineSlide
import ke.don.demos.talk.COMPACT_INTERACTIONS_STEPS
import ke.don.demos.talk.CONTEXTS_STEPS
import ke.don.demos.talk.CompactInteractionsSlide
import ke.don.demos.talk.ContextsSlide
import ke.don.demos.talk.FEEDBACK_LOOP_STEPS
import ke.don.demos.talk.FOLLOW_THE_MONEY_STEPS
import ke.don.demos.talk.FeedbackLoopSlide
import ke.don.demos.talk.FollowTheMoneySlide
import ke.don.demos.talk.ListDetailSlide
import ke.don.demos.talk.SupportingPaneSlide
import ke.don.demos.talk.TAKEAWAY_STEPS
import ke.don.demos.talk.TakeawaySlide
import ke.don.demos.talk.ThankYouSlide
import ke.don.demos.talk.WINDOW_CONSTRAINT_STEPS
import ke.don.demos.talk.WindowConstraintSlide
import ke.don.demos.talk.WindowSizeClassesSlide
import ke.don.domain.ScreenTransition
import ke.don.domain.SlideConfig
import ke.don.domain.SlidesConstants.SESSION_DURATION
import ke.don.domain.generateDeck
import ke.don.domain.timer.TimerController
import ke.don.introduction.IntroductionScreen
import kotlin.time.Duration

/** The deck for "Adaptive UI in Jetpack Compose". Script: docs/talk.md. Timing: docs/flow.md. */
@Composable
fun skiPresentationSlides(sessionDuration: Duration = SESSION_DURATION): List<SlideConfig> {
    val timerController = rememberTimerController(sessionDuration)

    val slides = remember(timerController) {
        generateDeck(timerController = timerController) {
            section("Part 1 · The setup") {
                slide(
                    "Title",
                    transition = ScreenTransition.Fade,
                    notes = titleNotes,
                    showHeader = false,
                    showFooter = false
                ) { IntroductionScreen() }
                slide("Chicken and egg", notes = feedbackLoopNotes, steps = FEEDBACK_LOOP_STEPS) {
                    FeedbackLoopSlide()
                }
            }

            section("Part 2 · Why should you care?") {
                slide("Forget Google's bottom line", notes = bottomLineNotes, steps = BOTTOM_LINE_STEPS) {
                    BottomLineSlide()
                }
                slide("Follow the money", notes = followTheMoneyNotes, steps = FOLLOW_THE_MONEY_STEPS) {
                    FollowTheMoneySlide()
                }
                slide("Apps already doing this", notes = appsDoingThisNotes, steps = APPS_DOING_THIS_STEPS) {
                    AppsDoingThisSlide()
                }
            }

            section("Part 3 · Why mobile still matters") {
                slide("Are we abandoning mobile?", notes = abandonMobileNotes, steps = ABANDON_MOBILE_STEPS) {
                    AbandonMobileSlide()
                }
                slide("Compact interactions", notes = compactInteractionsNotes, steps = COMPACT_INTERACTIONS_STEPS) {
                    CompactInteractionsSlide()
                }
                slide("Different devices, different contexts", notes = contextsNotes, steps = CONTEXTS_STEPS) {
                    ContextsSlide()
                }
            }

            section("Part 4 · The transition") {
                slide("Accommodating the user", notes = accommodateNotes, steps = ACCOMMODATE_STEPS) {
                    AccommodateSlide()
                }
            }

            section("Part 5 · The how") {
                slide("What adaptive UI means", notes = adaptiveMeaningNotes, steps = ADAPTIVE_MEANING_STEPS) {
                    AdaptiveMeaningSlide()
                }
                slide("The window is your constraint", notes = windowConstraintNotes, steps = WINDOW_CONSTRAINT_STEPS) {
                    WindowConstraintSlide()
                }
                slide("Demo 1 · Window size classes", notes = windowSizeClassesNotes) {
                    WindowSizeClassesSlide()
                }
                slide("Demo 2 · Adaptive navigation", notes = adaptiveNavigationNotes) {
                    AdaptiveNavigationSlide()
                }
                slide("Demo 3 · List-detail", notes = listDetailNotes) {
                    ListDetailSlide()
                }
                slide("Demo 4 · Supporting panes", notes = supportingPaneNotes) {
                    SupportingPaneSlide()
                }
                slide("Demo 5 · It's not just scaffolds", notes = adaptiveContentNotes) {
                    AdaptiveContentSlide()
                }
            }

            section("Part 6 · The takeaway") {
                slide("Meet them where they are", notes = takeawayNotes, steps = TAKEAWAY_STEPS) {
                    TakeawaySlide()
                }
                slide("Thank you", notes = thankYouNotes, footer = null) {
                    ThankYouSlide()
                }
            }
        }
    }
    return slides
}

@Composable
fun rememberTimerController(
    sessionDuration: Duration
): TimerController {
    val scope = rememberCoroutineScope()
    return remember(scope, sessionDuration) {
        TimerController(scope, sessionDuration)
    }
}
