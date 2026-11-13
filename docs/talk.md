# Adaptive UI in Jetpack Compose
## Meet your users where they are

**Event:** DevFest  
**Speaker:** Donald Isoe  
**Format:** Technical talk with live demonstrations  
**Estimated duration:** 25–30 minutes  
**Audience:** Android developers, primarily Jetpack Compose

---

# PART 1 — THE SETUP

## Slide 1: Title

**Visual:** A phone, tablet, foldable and desktop displaying the same application.

**Speaker:**

"Good morning, everyone!

Today we're going to talk about adaptive UI in Jetpack Compose.

But before we get into the technical bits, I want us to have a conversation about something.

So, do we have any Googlers in the room? 🙋🏽‍♂️

*[Wait for acknowledgment. Make eye contact.]*

Great. I wanted to have someone to look at when I say this.

No offence, Google, but..."

*[Pause.]*

"Google has been telling us for a while now that we should build applications that support tablets, foldables and larger screens.

And, well, Google has a business to run.

They're actively trying to sell foldables. They want people to buy these devices, and naturally, they want those people to get more value out of what they've paid for.

And one of the ways to do that is to have developers build applications that actually take advantage of the available screen real estate.

Because nobody wants to buy a foldable, open their favourite application and discover that they're looking at a phone application with a lot of empty space around it."

## Slide 2: Chicken and egg

**Visual:** Four cards around a loop (better apps, more useful devices, more users, more incentive), revealed one per press. Arrows draw between them; the last press closes the loop and "Which came first?" appears in the middle.

**Speaker:**

"But here's the interesting part.

Google isn't entirely wrong.

In fact, they're trying to deliver both the chicken and the egg.

They want people to buy larger-screen devices, but they also need developers to build experiences that make those devices worth buying.

And developers need people buying those devices to justify investing in those experiences.

It's a bit of a feedback loop."

*[One card per press. The last press closes the loop.]*

---

# PART 2 — WHY SHOULD YOU CARE?

## Slide 3: Forget Google's bottom line

**Visual:** Google logo, followed by a transition to a developer and an end user.

**Speaker:**

"But let's step away from Google's bottom line for a moment.

Let's talk about what's in it for you.

Do you intend on having people engage more with your application?

Do you want your application to be useful to people in more situations?

Then meet them where they are.

And I don't mean that as some motivational LinkedIn post.

I mean it quite literally.

Think about how you use your devices.

For example, my screen time rests at less than two hours a day.

And most of that is probably TikTok, Twitter, and quick responses on WhatsApp.

Maybe I'll open a payment application to move some money around.

But most of my actual work happens on my PC.

If an application has a PC version, that's probably where I'll use it.

Notion, Claude, Canva, Google Docs.

If I'm doing something productive, I generally don't want to open my phone to do it.

I want a keyboard. I want more screen real estate. I want to see more information at once.

And I'm probably not alone in that."

*[Slide: Phone screen vs desktop workspace.]*

"Now, here's something I find interesting.

People who buy tablets and foldables often have use cases that benefit from having more screen real estate.

Maybe they read a lot. Maybe they draw. Maybe they work with multiple documents. Maybe they want to multitask.

It's not necessarily that having a bigger screen makes them want to spend more time in applications.

Perhaps they already had a need for more screen real estate, and that's why they bought more screen.

And if your application supports that need, you have an opportunity to become part of their workflow."

## Slide 4: Follow the money

**Visual:** One big number per beat, revealed in sequence (state-driven reveal, not duplicate slides):

1. **580M+** large-screen Android devices in use
2. **9x** more spent by people who own a phone *and* a large screen
3. **14x** more spent by foldable users
4. **~3x** more time in-app for phone + tablet users (entertainment apps, US)

Small footer on each number: *"Source: Google."* Let it sit there.

**Speaker:**

"Okay. Fine. Let's talk numbers.

According to Google, there are more than 580 million large-screen Android devices out there. Tablets, foldables, Chromebooks.

That's not a niche. That's a market."

*[Reveal 9x.]*

"People who own a phone and a large-screen device, a tablet or a Chromebook, spend nine times more on apps than people who only have a phone.

Nine times."

*[Reveal 14x.]*

"And foldable users?

Fourteen times more."

*[Pause. Look at the Googler.]*

"Which, incidentally, is exactly the number you'd want to show developers if you were trying to sell foldables."

*[Let it land. Don't explain it.]*

"And it isn't just money.

Google's numbers from last year say people using entertainment apps on both a phone and a tablet spend almost three times as long in those apps as phone-only users.

Now, let's be honest about what these numbers say.

They don't say that a bigger screen makes people spend more.

Someone who owns a foldable, a tablet and a phone already has money to spend. That's correlation, not causation.

Which is exactly the point I made a minute ago. These people already had a need, and they already have a wallet.

The question is whether your app is on the screen they're holding when they open it."

*[Slide: "Correlation, not causation. Still your users."]*

**Stats reference (verify before the talk; Google updates these):**

| Claim on slide | What Google actually says | Source |
|---|---|---|
| 580M+ large-screen devices | "more than 580 million large screen Android devices" | [Android Developers: Adaptive apps](https://developer.android.com/adaptive-apps), [Android Developers Blog, May 2026](https://android-developers.googleblog.com/2026/05/android-adaptive-development-ecosystem.html) |
| 9x, phone + large screen | "Multi-device users spend 9x more on average than phone only users" (Google Internal Data, 2026) | [Android Developers Blog, May 2026](https://android-developers.googleblog.com/2026/05/android-adaptive-development-ecosystem.html) |
| 9x, large-screen users | Users on "foldables, tablets, and Googlebooks ... spend 9x more in dollar value than users who have only a phone" | [Android Developers: Adaptive apps](https://developer.android.com/adaptive-apps) |
| 14x, foldables | "On foldables, that engagement multiplier can reach 14x" (Google Internal Data, 2026). Android Authority reports it as in-app purchase spending vs non-folding phones. | [Android Developers Blog, May 2026](https://android-developers.googleblog.com/2026/05/android-adaptive-development-ecosystem.html), [Android Authority](https://www.androidauthority.com/foldable-phone-users-in-app-purchases-3702784/) |
| ~3x time, phone + tablet | Entertainment apps: users on phone and tablet "spend almost 200% more time in-app" than phone-only users, US (Google internal data) | [Android Developers Blog, I/O 2025](https://android-developers.googleblog.com/2025/05/adaptiveapps-io25.html) |

Notes on the numbers:

- **The 9x figures measure money, not time.** No Google source found gives a 9x time-in-app figure. The time figure Google publishes is ~3x (phone + tablet, entertainment apps, US, 2025). Don't say "9x more time" on stage.
- **The sources disagree.** The same May 2026 blog post also says large-screen users "typically spend roughly 5x more than phone-only users", while the Adaptive apps page says 9x. Use the multi-device 9x (it has a dated source) and avoid putting both on screen.
- **The 14x is worded loosely.** Google calls it an "engagement multiplier" right after a spending sentence. Present it as spending, as Android Authority does, but don't claim more precision than Google gives. Google shares no dollar figures.
- **All of these are Google's internal data.** No methodology is published. That's what the "Source: Google." footer is for, and it supports the joke.
- **Older figure, if needed:** tablets delivered ~50% higher revenue per user than phones ([Android Developers Blog, March 2023](https://android-developers.googleblog.com/2023/03/concepts-users-spend-more-time-using-app-on-tablets-than-phones.html)). Dated; use it only as background.

## Slide 5: Applications already doing this

**Visual:** Six app cards in a grid: Notion, Canva, Lightroom, Figma, Power BI, Notability. Each shows what the app does on a big screen and on a phone.

**Speaker:**

"Let's look at some applications we probably already use.

Take Notion.

On desktop, you have room for databases, multiple columns, navigation and a lot of information.

On mobile, you're probably checking something, capturing a note, searching for a page or making a quick edit.

Same product. Different interaction patterns.

Take Canva.

You can create and edit designs on your phone, absolutely.

But if I'm doing something more involved, I'd much rather have a larger screen and more precise controls.

Lightroom is another interesting example.

You can capture and edit photographs on your phone, and then continue working with those photographs on a desktop.

Figma takes a slightly different approach.

Its mobile application is largely about inspecting designs, reviewing work and testing prototypes. The actual design authoring experience lives elsewhere.

Notability lives on the tablet. Handwritten notes with a stylus, on a screen big enough to write on. On the phone, you're reviewing those notes or recording a lecture.

And then there's Power BI.

You can spend your time building reports and models on desktop, but when you want to check whether a business metric is behaving as expected, you can pull out your phone.

These applications aren't necessarily trying to reproduce the exact same experience everywhere.

They're considering what makes sense on each platform."

*[Slide: Desktop and mobile side-by-side, with the different tasks highlighted.]*

"Notice the pattern?

On desktop, we're often creating, organizing, configuring and analyzing.

On mobile, we're often capturing, retrieving, reviewing, monitoring and responding.

These aren't strict rules, of course.

You can absolutely create things on a phone.

But the context in which we use these devices often differs."

---

# PART 3 — WHY MOBILE STILL MATTERS

## Slide 6: Are we abandoning mobile?

**Visual:** A dramatic phone image. Perhaps an oversized question mark.

**Speaker:**

"So does this mean we should abandon mobile and start building desktop applications?"

*[Pause.]*

"Absolutely not.

Because sometimes I don't want to sit down and open an application.

I just want to get something done.

I want to send someone money.

I want to check whether my package has arrived.

I want to scan a QR code.

I want to reply to a message.

I don't want a productivity suite.

I want a button."

*[Pause for laughter.]*

"Think about M-PESA.

How often do you sit down at your computer to send someone 200 shillings?

You probably don't.

You pull out your phone, open the application or dial the relevant USSD code, and complete the transaction.

The phone is already with you.

It's convenient, it's accessible, and the interaction is relatively short."

## Slide 7: The power of compact interactions

**Visual:** Examples of Google Wallet, M-PESA, weather widgets, calendar widgets and notifications.

**Speaker:**

"And payments are just one example.

Sometimes you just want to glance at something.

What's my account balance?

When is my next meeting?

Has my package arrived?

Is it raining?

What's happening with this transaction?

You don't necessarily want to navigate through five screens to find that information.

This is where widgets, notifications and shortcuts become particularly useful.

Android actually gives us a number of ways to expose application functionality without requiring users to launch the entire application.

App shortcuts.

Home screen widgets.

Notification actions.

Quick settings tiles, where appropriate.

These are all ways of reducing the distance between a user's intention and the action they want to perform.

Sometimes the best user experience is not getting someone to spend twenty minutes in your application.

It's helping them finish something in ten seconds."

*[Slide: Intention → Interaction → Completion, with minimal steps.]*

"And phones have another advantage.

They're almost always with us.

They have access to cameras, location, NFC, biometric authentication, motion sensors and notifications.

These capabilities allow us to build experiences that are deeply connected to the user's immediate context.

Think about scanning a QR code to make a payment.

Or using location to find a nearby service.

Or authenticating a transaction with your fingerprint.

These aren't just smaller versions of desktop interactions.

They're interactions that make sense because of the device the user is holding."

## Slide 8: Different devices, different contexts

**Visual:** Three columns.

- Desktop: Deep engagement
- Tablet / Foldable: Expanded engagement
- Phone: Immediate engagement

**Speaker:**

"So, here's where I want us to land.

Desktop is often where people go for deep engagement.

Mobile is often where people go for immediate engagement.

And tablets and foldables give us opportunities to expand the experience.

But these are tendencies, not rules.

A phone can be someone's primary productivity device.

A tablet can be someone's primary computer.

A foldable can be used for a quick transaction.

The point isn't to assign rigid responsibilities to devices.

The point is to understand the context in which people use them.

We're not building for screens.

We're building for people who happen to use screens."

---

# PART 4 — THE TRANSITION

## Slide 9: Accommodating the user

**Visual:** Return to the speaker's original phone/desktop comparison.

**Speaker:**

"I know this makes me sound like I'm trying to convince you to accommodate my needs..."

*[Pause. Let the audience anticipate the 'but'.]*

"Anyway.

Let's talk about Compose."

*[Move immediately to the technical section. Do not explain the joke.]*

---

# PART 5 — THE HOW

## Slide 10: What does adaptive UI actually mean?

**Visual:** The same application shown at compact, medium and expanded window sizes.

**Speaker:**

"Now, before we touch any code, let's establish what we're actually trying to achieve.

When we talk about adaptive UI, we're talking about applications that respond to their available space and adapt their layouts and interactions accordingly.

And there's an important distinction here.

Responsive UI and adaptive UI are related, but they aren't necessarily the same thing.

Responsive design is concerned with how content responds to changes in available space.

Adaptive design goes a step further.

It allows us to make deliberate decisions about how the experience should change.

For example:

- Should this navigation be a bottom navigation bar or a navigation rail?
- Should these two pieces of content appear one after another, or side by side?
- Should I show a list, or a list alongside a detail pane?
- Should a component occupy the entire available width, or should it have a maximum width?

We're not just resizing things.

We're reconsidering the layout and interaction model."

## Slide 11: The window is your constraint

**Visual:** A window changing size continuously, with the layout responding.

**Speaker:**

"One of the first things we need to change in how we think about Android development is this:

Stop thinking exclusively in terms of devices.

A device does not necessarily tell you how much space your application has.

You can have a tablet with your application running in split-screen mode.

You can have a foldable with the application open on the outer display.

You can have a desktop window that's been resized.

You can have an application running in freeform windowing.

So instead of asking whether we're on a tablet or a phone, we should ask:

How much space does my application actually have?

That is a much more useful constraint to design around."

## Slide 12: Window size classes

**Visual:** Three windows illustrating compact, medium and expanded width classes.

**Speaker:**

"Compose and the Android ecosystem give us tools to reason about the available window size.

One of those concepts is window size classes.

We generally work with three width categories:

- Compact.
- Medium.
- Expanded.

The important thing to understand is that these are not device categories.

They're descriptions of the available window space.

A compact window might be a phone in portrait.

A medium window could be a phone in landscape or a smaller tablet.

An expanded window could be a larger tablet or a desktop window.

And these boundaries are not arbitrary device labels. They help us make consistent layout decisions."

**DEMO 1: Window size classes**

Show:
- Reading the current window size class.
- Reacting to a change in window width.
- Switching between compact and expanded navigation.

**Speaker:**

"Notice that we're not asking what device we're running on.

We're asking what space we have available.

That's the distinction."

## Slide 13: Adaptive navigation

**Visual:** Bottom navigation transforming into a navigation rail and potentially a navigation drawer.

**Speaker:**

"Let's start with something familiar: navigation.

On a phone, bottom navigation is a pretty natural interaction pattern.

Your thumb can reach it, it's familiar, and it doesn't consume too much space.

But what happens when we have more horizontal space?

Do we still want to keep that bottom navigation?

Maybe not.

We could move to a navigation rail.

And on even larger screens, we might decide that a permanent navigation drawer makes more sense.

Compose provides NavigationSuiteScaffold to help us implement these kinds of adaptive navigation experiences.

Instead of manually maintaining completely separate navigation implementations, we can allow the navigation suite to respond to the available window configuration."

**DEMO 2: NavigationSuiteScaffold**

Show:
- Compact navigation.
- Expanded navigation.
- How the navigation configuration changes.

**Speaker:**

"And that's one of the first things I want you to notice.

We're not building a second application.

We're adapting the application we already have.

Now hold that thought about the back stack. Next slide, it becomes the whole story."

## Slide 14: Navigation 3

**Visual:** "Navigation 3", kicker "Navigation is state. Layout is a strategy." Four points revealed one at a time, then a code card: `NavDisplay` with a list of scene strategies, and the shape of a custom `SceneStrategy`.

**Speaker:**

"Before we put two panes on the screen, we need to talk about how navigation decides what's on the screen. That's Navigation 3.

First: the back stack is just a list. You own it. Going forward is adding to it. Going back is removing from it. Nothing is hiding your navigation state from you.

Second: every entry can describe itself. This screen is a list. This one is a detail. This one is a dialog. The screen doesn't know how it will be laid out, and it doesn't need to.

Third, and this is the bit I want you to remember: scene strategies. NavDisplay asks each strategy, in order: given this back stack, do you want to show something? A strategy looks at the entries on top and their roles, and either returns a Scene or passes. The first Scene wins. If nobody claims it, you get a single pane.

Which means the same back stack can be one pane on a phone and two or three panes on a tablet. Resize the window and the Scene changes. The back stack doesn't.

And if the built-in strategies don't fit, you write your own: return a Scene, or return null to pass. Bottom sheets, a three-column desktop layout, whatever your product needs.

Navigation is state. Layout is a strategy.

Let's look at two strategies Material gives us."

## Slide 15: List-detail layouts

**Visual:** A mobile list transitioning into a two-pane list/detail layout.

**Speaker:**

"Now let's talk about something a little more interesting.

Imagine a messaging application.

On a phone, you open the application, see your conversations, tap one, and navigate into the conversation.

That's a perfectly reasonable interaction.

But what if you have more space?

Do we really need to navigate away from the conversation list just to see a conversation?

Not necessarily.

We could have the conversation list on one side and the selected conversation on the other.

Now the user can switch between conversations without constantly navigating back and forth.

This is where list-detail layouts become particularly useful.

Navigation 3 gives us this with a scene strategy: ListDetailSceneStrategy."

**DEMO 3: NavDisplay + ListDetailSceneStrategy (Navigation 3)**

Show:
- Compact: list and detail as separate navigation destinations.
- Expanded: list and detail side by side.
- Selection handling and state preservation.

**Speaker:**

"Think about email clients, messaging applications, file browsers and even banking applications.

There are a lot of places where this pattern makes sense.

And look at the code. It's the same NavDisplay you'd write for a phone app. Opening a conversation is adding a key to the back stack. The entries say list pane and detail pane, and the strategy from the last slide does the rest."

## Slide 16: Supporting panes

**Visual:** Main content with an additional contextual panel.

**Speaker:**

"And sometimes two panes aren't even enough.

Imagine a document editor.

You have your document in the middle, but you also want access to contextual information.

Maybe a table of contents.

Maybe formatting controls.

Maybe document properties.

On a compact screen, that supporting information might need to be hidden behind another interaction.

On a larger screen, we can afford to keep it visible.

That's another scene strategy in Navigation 3: SupportingPaneSceneStrategy.

The important thing is that the additional space should give us an opportunity to provide more utility, not just more whitespace."

**DEMO 4: NavDisplay + SupportingPaneSceneStrategy (Navigation 3)**

Show:
- Compact presentation.
- Expanded presentation.
- How supporting content changes visibility.
- The code: same NavDisplay and back stack as Demo 3; only the strategy and the entry roles changed.

## Slide 17: It's not just scaffolds

**Visual:** A collection of UI elements showing adaptive spacing, grids, content widths and typography.

**Speaker:**

"Now, I don't want you leaving here thinking adaptive UI is just about choosing the correct scaffold.

There's a lot more to it.

Think about the components inside your application.

A grid that displays two columns on a phone might display four or five on a tablet.

A form that takes the entire width of a phone might become unnecessarily wide on a desktop.

A piece of content might benefit from a maximum width.

You might want to change the arrangement of buttons, images, cards and other components.

You also need to think about input methods.

On a phone, the primary interaction might be touch.

On desktop, you might have a keyboard and mouse.

On tablets, you might have stylus input.

Adaptive UI is a product and interaction design decision, not just a layout decision."

**DEMO 5: Adaptive content**

Show:
- Adaptive grids.
- Maximum content widths.
- Different arrangements.
- Appropriate spacing and sizing.

## Slide 18: Staggered grids

**Visual:** The playground's Feed: cards of different heights packed into columns.

**Speaker:**

"One more layout before we wrap up.

When your items have different heights, a regular grid leaves gaps. Photos, posts, notes, products.

A staggered grid packs each item into the shortest column."

**DEMO 6: LazyVerticalStaggeredGrid**

Show:
- `StaggeredGridCells.Adaptive(160.dp)` going from two columns to as many as fit.
- A header that spans the full line.
- That it's lazy, so it suits long feeds, unlike `Grid`.

## Slide 19: The biggest unblocker

**Visual:** "The biggest unblocker", kicker "Compose Multiplatform", four points revealed one at a time.

**Speaker:**

"Now, here's the thing that actually changed my mind about all of this.

The biggest unblocker isn't a scaffold.

It's Compose Multiplatform.

The same composables ship to Android, desktop, web and iOS. The window size classes, Navigation 3 and the scene strategies you just saw run on desktop and web too. The same back stack, the same strategy, everywhere.

So the work you do for tablets turns out to be most of your desktop app.

We can now ship to desktop with confidence. Not as a side project. Not as a second codebase.

And if you want proof: this deck is a Compose Multiplatform app. Every demo you just saw ran in it, on a laptop."

*[Pause.]*

"That doesn't mean every app should ship a desktop build. It means the cost of doing it is now mostly design decisions, not engineering."

---

# PART 6 — THE TAKEAWAY

## Slide 20: What should you take home?

**Visual:** One application displayed across multiple form factors.

**Speaker:**

"So, what should you take away from all this?

First, stop thinking of your application as a phone application.

Think about the people using it and the different contexts in which they might need it.

Second, stop assuming that the device determines the available space.

Your application should respond to the actual window constraints.

Third, don't just stretch your layouts.

Think about how the experience should change. With Navigation 3, that's a scene strategy, not a rewrite.

And finally, remember that adaptive UI isn't an exercise in supporting as many devices as possible.

It's about making your application useful in more situations.

Sometimes your user wants to sit down and work.

Sometimes they want to pick up their phone and finish something in ten seconds.

Give them both experiences.

Meet them where they are."

## Slide 21: Thank you

**Visual:** Everything centred. The DEVFEST wordmark grows back out of the header. "Thank you!" drops in letter by letter in Google colours and keeps a gentle wave, followed by "Asante sana", the tagline, the speaker's name and the repo link. Balloons drift up and confetti falls throughout; a click fires a confetti burst.

**Speaker:**

"And that's adaptive UI in Jetpack Compose.

Thank you!"

---

# PRESENTATION NOTES FOR SKI

## Overall narrative

The presentation should follow this progression:

1. Challenge Google's motivation.
2. Establish why developers should care independently of Google's commercial interests, then back it with Google's own numbers (and their limits).
3. Explain why desktop and larger screens have genuine utility.
4. Defend the continued importance of mobile.
5. Establish that different form factors serve different contexts.
6. Transition into Jetpack Compose.
7. Demonstrate the implementation patterns.
8. Name Compose Multiplatform as the biggest unblocker: the adaptive work ships to desktop too.
9. Close with the central thesis.

## Tone and delivery

- Conversational, not academic.
- Technical but accessible.
- Humour should feel spontaneous.
- Avoid corporate marketing language.
- Allow pauses after punchlines.
- Use familiar applications and relatable scenarios.
- Do not turn every slide into a paragraph of text.
- Prefer product screenshots, simple diagrams and visual comparisons.

## Important comedic beats

1. Ask whether there are Googlers in the room.
2. Recruit one as the target for the "No offence, Google" joke.
3. Introduce the chicken-and-egg analogy.
4. Use the personal screen-time anecdote.
5. "Exactly the number you'd want to show developers if you were trying to sell foldables." (Slide 4, eyes on the Googler.)
6. "I don't want a productivity suite. I want a button."
7. "I know this makes me sound like I'm trying to convince you to accommodate my needs..."
8. "Anyway. Let's talk about Compose."

Do not explain the abandoned "but" joke.

## Technical accuracy and implementation notes

- Use current AndroidX Compose Material3 Adaptive APIs and verify package names and signatures against the versions selected for the sample project.
- Explain that window size classes describe available window space, not physical device identity.
- Demonstrate window resizing and configuration changes, not just static phone/tablet previews.
- Keep navigation state and selected-item state coherent across layout changes.
- Distinguish layout adaptation from input adaptation.
- Avoid implying that supporting scaffolds automatically makes an application fully adaptive.
- Navigation 3 on Compose Multiplatform (1.10+): on iOS and web, back-stack keys must be registered for polymorphic serialization so `rememberNavBackStack` can save them. Browser back/forward history isn't integrated with multiplatform Nav 3 yet. Keep both for Q&A.
- The Demo 3 and 4 code cards show Nav 3 scene strategies; the playground itself is built on the Material pane scaffolds. The behaviour on screen is the same, with one difference: with `SupportingPaneSceneStrategy`, the supporting pane shows only while its entry is on the back stack.
- Include accessibility, keyboard navigation and large-screen testing where time permits.

## Potential live-demo repository

Prepare a single sample application with:
- Compact and expanded navigation.
- A list-detail experience.
- A supporting pane.
- An adaptive grid.
- A staggered feed.
- A form with constrained content width.

Prefer one coherent application that evolves throughout the talk rather than five unrelated code snippets.

## Speaker personality and comedic direction — NON-NEGOTIABLE

The speaker is intentionally cheeky, irreverent, occasionally provocative and unapologetically opinionated.

Do NOT sanitize the humour.

Do NOT turn the speaker into a corporate keynote presenter.

Do NOT replace profanity, sarcasm or deliberate awkwardness with polite alternatives.

The presentation should feel like an experienced Android engineer having a conversation with fellow developers, occasionally roasting Google, challenging conventional thinking and making the audience laugh.

### Specific comedic direction

- Google is fair game. Criticize its commercial incentives, product decisions and developer messaging.
- If a Googler is in the room, the speaker intends to make eye contact during the "No offence, Google" jokes.
- Use deliberate pauses to create comedic anticipation.
- Sometimes complete the anticipated punchline. Sometimes deliberately abandon it.
- Preserve the "I know this makes me sound like I'm trying to convince you to accommodate my needs... Anyway, let's talk about Compose" transition.
- Do not explain jokes after delivering them.
- Do not make every slide funny. Technical credibility matters.
- Allow occasional profanity where it improves delivery.
- Avoid forced internet slang, excessive memes and jokes that sound written by an AI trying to be funny.

### Audience relationship

The audience consists of fellow developers, not customers being sold a product.

Treat them as intelligent adults. Challenge their assumptions. Occasionally call out bad engineering habits.

The speaker should be comfortable saying that an approach is unnecessary, misguided or just plain stupid when technically justified.

### Google-specific instruction

Do not make Google the villain of the entire presentation. The speaker acknowledges that Google's adaptive UI direction is technically valuable while remaining perfectly comfortable questioning the commercial motivations behind it.

The humour should punch at the company and its decisions, not at an individual Googler.

### Most important

The speaker's personality is part of the presentation.

Do not optimize it away in pursuit of professionalism.

## Sources to reference

- Android Developers: [Adaptive apps](https://developer.android.com/adaptive-apps)
- Android Developers Blog: [Adaptive development for the expanding Android ecosystem (May 2026)](https://android-developers.googleblog.com/2026/05/android-adaptive-development-ecosystem.html)
- Android Developers Blog: [Adaptive apps at I/O 2025](https://android-developers.googleblog.com/2025/05/adaptiveapps-io25.html)
- Android Authority: [Foldable users spend 14x more on in-app purchases](https://www.androidauthority.com/foldable-phone-users-in-app-purchases-3702784/)
- Android Developers: [Build adaptive apps](https://developer.android.com/develop/ui/compose/layouts/adaptive)
- Android Developers: [Material 3 adaptive navigation](https://developer.android.com/develop/ui/compose/layouts/adaptive/build-adaptive-navigation)
- Android Developers: [Window size classes](https://developer.android.com/develop/ui/compose/layouts/adaptive/use-window-size-classes)
- Android Developers: [Adaptive layouts](https://developer.android.com/develop/ui/compose/layouts/adaptive/support-different-screen-sizes)
- Notion: [Mobile documentation](https://www.notion.com/help/notion-for-mobile)
- Adobe: [Lightroom mobile](https://helpx.adobe.com/lightroom-cc/using/lightroom-mobile.html)
- Figma: [Mobile app](https://help.figma.com/hc/en-us/articles/360040328273-Guide-to-the-Figma-mobile-app)
- Microsoft: [Power BI mobile](https://learn.microsoft.com/en-us/power-bi/consumer/mobile/mobile-apps-for-mobile-devices)
- Android Developers: [Widgets](https://developer.android.com/develop/ui/views/appwidgets/overview)
- Android Developers: [App shortcuts](https://developer.android.com/develop/ui/views/launch/shortcuts)