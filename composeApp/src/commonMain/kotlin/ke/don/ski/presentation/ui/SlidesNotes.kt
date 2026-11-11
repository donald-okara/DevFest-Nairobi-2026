package ke.don.ski.presentation.ui

import androidx.compose.ui.text.AnnotatedString

// One named constant per slide, referenced from skiPresentationSlides().
// The full script is docs/talk.md. See docs/speaker-notes.md for how to write these.

private fun notes(vararg points: String) = points.map(::AnnotatedString)

// Part 1: The setup

val titleNotes = notes(
    "Good morning! Today: adaptive UI in Jetpack Compose. But first, a conversation.",
    "Ask: any Googlers in the room? Wait. Make eye contact. \"Great. I wanted someone to look at when I say this.\"",
    "\"No offence, Google, but...\" Pause. Google is selling foldables and wants people to get value from them. Nobody wants to open their favourite app on a foldable and see a phone app with empty space around it.",
)

val feedbackLoopNotes = notes(
    "Google isn't entirely wrong. They're trying to deliver both the chicken and the egg.",
    "Better apps make the devices useful, which brings more users, which gives developers more reason to build better apps.",
    "\"It's a bit of a feedback loop.\" Move on.",
)

// Part 2: Why should you care?

val bottomLineNotes = notes(
    "Step away from Google's bottom line. Want more engagement? Want to be useful in more situations? Meet people where they are. Literally, not as a LinkedIn post.",
    "Anecdote: under two hours of screen time a day, mostly TikTok, Twitter, WhatsApp. Real work happens on the PC: Notion, Claude, Canva, Google Docs.",
    "People who buy tablets and foldables already had a need for the space. Support that need and you become part of their workflow.",
)

val followTheMoneyNotes = notes(
    "580M+ large-screen Android devices (Google). 9x: people with a phone AND a large screen spend 9x more than phone-only users (Google internal data, 2026).",
    "14x: foldable users. Pause, look at the Googler: \"Exactly the number you'd want to show developers if you were trying to sell foldables.\" Don't explain it.",
    "~3x time: phone + tablet users of entertainment apps spend almost 200% more time in-app (US, I/O 2025). The 9x figures are money, not time. Don't say \"9x more time\".",
    "What the numbers don't measure: cause. Foldable owners already have money to spend. Correlation, not causation. The question is whether your app is on the screen they're holding.",
)

val appsDoingThisNotes = notes(
    "Notion: databases and columns on desktop; checking, capturing, quick edits on mobile. Canva: fine on a phone, better on a big screen.",
    "Lightroom: shoot and edit on the phone, finish on desktop. Figma mobile is for inspecting and reviewing, not authoring. Power BI: build on desktop, check the metric on the phone.",
    "Pattern: desktop is creating, organising, analysing. Mobile is capturing, reviewing, monitoring, responding. Not strict rules.",
)

// Part 3: Why mobile still matters

val abandonMobileNotes = notes(
    "\"So do we abandon mobile and build desktop apps?\" Pause. \"Absolutely not.\"",
    "Send money, check a package, scan a QR code, reply to a message. Reveal: \"I don't want a productivity suite. I want a button.\" Pause for laughter.",
    "M-PESA: how often do you sit at a computer to send someone 200 shillings? The phone is already with you.",
)

val compactInteractionsNotes = notes(
    "Sometimes you just want to glance: balance, next meeting, package, rain.",
    "Widgets, app shortcuts, notification actions, Quick Settings tiles reduce the distance between intention and action. The best UX may be finishing in ten seconds, not twenty minutes in your app.",
    "Phones have cameras, location, NFC, biometrics, sensors. QR payments, nearby services, fingerprint auth: interactions that exist because of the device in hand.",
)

val contextsNotes = notes(
    "Desktop: deep engagement. Tablet and foldable: expanded engagement. Phone: immediate engagement.",
    "Tendencies, not rules. A phone can be someone's main productivity device; a tablet can be someone's computer.",
    "Land it: \"We're not building for screens. We're building for people who happen to use screens.\"",
)

// Part 4: The transition

val accommodateNotes = notes(
    "\"I know this makes me sound like I'm trying to convince you to accommodate my needs...\" Pause. Let them expect the \"but\".",
    "Reveal and say: \"Anyway. Let's talk about Compose.\" Go straight to the next slide. Do not explain the joke.",
)

// Part 5: The how

val adaptiveMeaningNotes = notes(
    "Adaptive UI: apps that respond to the space they have and change layout and interaction to match.",
    "Responsive is how content responds to space. Adaptive is deliberate decisions about how the experience changes.",
    "Walk the four questions. \"We're not just resizing things. We're reconsidering the layout and interaction model.\"",
)

val windowConstraintNotes = notes(
    "Stop thinking in devices. A device doesn't tell you how much space your app has.",
    "Split screen, the outer display of a foldable, a resized desktop window, freeform windowing.",
    "Reveal the question: how much space does my app actually have? That's the constraint to design around.",
)

val windowSizeClassesNotes = notes(
    "Window size classes: compact, medium, expanded width. They describe window space, not device type.",
    "Demo 1: read the current size class, resize the window across the breakpoints, switch navigation on the width class.",
    "What the demo doesn't show: device detection. \"We're not asking what device we're on. We're asking what space we have.\"",
    "Playground: tap Phone, Foldable, Tablet, Laptop, Desktop, or drag the right edge. It simulates the window, because inside the deck currentWindowAdaptiveInfo() would report the fullscreen projector window. Say so: the real app reads currentWindowAdaptiveInfo(). Next arrow shows the code.",
)

val adaptiveNavigationNotes = notes(
    "Bottom navigation is natural on a phone: thumb reach, familiar, cheap on space. With more width, a rail; even more, a permanent drawer.",
    "Demo 2: NavigationSuiteScaffold. Compact, then expanded, watch the navigation type change.",
    "\"We're not building a second application. We're adapting the one we already have.\" Back-stack behaviour is out of scope.",
    "Drag from Phone to Desktop: bottom bar, then rail at 600dp, then drawer at 1200dp. Next arrow shows the NavigationSuiteScaffold code.",
)

val listDetailNotes = notes(
    "Messaging app: on a phone, tap a conversation and navigate in. With more space, keep the list and the conversation side by side.",
    "Demo 3: ListDetailPaneScaffold. Compact: separate destinations. Expanded: side by side. Selection survives the resize.",
    "What it doesn't show: state across process death. Email, files, banking: lots of places this fits.",
    "On Phone, tap a message, then switch to Tablet: the selection is still open, now beside the list. Back on Phone, the detail shows a back arrow. Next arrow shows the code.",
)

val supportingPaneNotes = notes(
    "Sometimes two panes aren't enough. A document editor wants a table of contents, formatting, properties.",
    "Demo 4: SupportingPaneScaffold. Compact hides the supporting content behind an interaction; expanded keeps it visible.",
    "The extra space should add utility, not whitespace.",
    "On Phone, tap Contents to open the supporting pane. Widen to Laptop: it stays beside the document. Next arrow shows the code.",
)

val adaptiveContentNotes = notes(
    "Adaptive UI isn't just picking the right scaffold. Grids go from 2 columns to 4 or 5. Forms shouldn't stretch across a desktop. Content wants a max width.",
    "Input matters too: touch on phones, keyboard and mouse on desktop, stylus on tablets.",
    "Demo 5: adaptive grid, max content width, different arrangements. It's a product and interaction decision, not just a layout one.",
    "Gallery: drag the width and watch the FlexBox chips rewrap and the Grid go from 2 to 5 columns. Then open Settings: the form stops at 560dp. Grid and FlexBox are experimental APIs. Next arrow shows the code.",
)

// Part 6: The takeaway

val takeawayNotes = notes(
    "One: it's not a phone app. Think about people and their contexts. Two: the window decides the space, not the device.",
    "Three: don't stretch layouts, change the experience. Four: it's about being useful in more situations, not supporting every device.",
    "\"Sometimes your user wants to sit down and work. Sometimes they want to finish something in ten seconds. Give them both. Meet them where they are.\"",
)

val thankYouNotes = notes(
    "\"And that's adaptive UI in Jetpack Compose. Thank you!\"",
    "Point at the sample project link if it's on screen.",
)
