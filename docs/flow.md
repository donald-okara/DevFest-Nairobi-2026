# Talk flow: Adaptive UI in Jetpack Compose

A one-page run-of-show for the deck. Use it to rehearse timing, reveals, and the live demos. The full script, speaker lines, and sources are in `talk.md`. The deck is defined in `composeApp/.../presentation/ui/SkiPresentationSlides.kt`, and its speaker notes are in `SlidesNotes.kt`.

**Target:** 28:30, which leaves 1:30 of slack in a 30-minute slot. The presenter timer is set to 30 minutes (`SESSION_DURATION`).
**Shape:** about 12 minutes of argument (why), then about 14 minutes of Compose (how), then a 2-minute close.

## Arc

```
Roast Google ─► Fine, Google has a point ─► The numbers ─► Big screens are for deep work
     ─► Phones are for "I want a button" ─► Devices are contexts, not rules
     ─► "...accommodate my needs" / "Anyway." ─► Compose: window, nav, panes, grids
     ─► Compose Multiplatform is the unblocker ─► Meet them where they are
```

The argument is one sentence: **build for the context people are in, and let the window decide the layout, not the device.**

## Run of show

Slide numbers match the deck, its table of contents (press **T** in the presenter window), and `talk.md`.

**Reveals** is how many arrow presses the slide takes before the next press moves on. Each reveal shows one point, card, or number.

| # | Slide | Part | Reveals | Time | Running | Beat | Exit line |
|---|---|---|---|---|---|---|---|
| 1 | Title | 1 · Setup | 0 | 1:30 | 1:30 | Googler call-out. "No offence, Google." | "...a phone app with a lot of empty space around it." |
| 2 | Chicken and egg | 1 · Setup | 4 | 1:00 | 2:30 | The wordmark glides into the header. Cards appear around the loop; the last press closes it. | "It's a bit of a feedback loop." |
| 3 | Forget Google's bottom line | 2 · Why care | 4 | 2:30 | 5:00 | Screen-time anecdote. Real work happens on a PC. Big-screen buyers already had a need. | "...an opportunity to become part of their workflow." |
| 4 | Follow the money | 2 · Why care | 5 | 1:30 | 6:30 | 580M+, 9x, 14x, ~3x count up one by one, then the caveat. | "Is your app on the screen they're holding?" |
| 5 | Apps already doing this | 2 · Why care | 6 | 2:00 | 8:30 | Notion, Canva, Lightroom, Figma, Power BI, Notability. | "The context in which we use these devices often differs." |
| 6 | Are we abandoning mobile? | 3 · Mobile | 1 | 1:00 | 9:30 | M-PESA, 200 bob. The reveal is "I want a button." | "The interaction is relatively short." |
| 7 | Compact interactions | 3 · Mobile | 4 | 1:30 | 11:00 | Widgets, shortcuts, notification actions, sensors. | "...because of the device the user is holding." |
| 8 | Different devices, different contexts | 3 · Mobile | 3 | 1:00 | 12:00 | Desktop, tablet, phone drawings: hours, minutes, seconds. | "We're building for people who happen to use screens." |
| 9 | Accommodating the user | 4 · Transition | 1 | 0:20 | 12:20 | The setup line, pause, then reveal "Anyway. Let's talk about Compose." | Go straight to slide 10. No explanation. |
| 10 | What adaptive UI means | 5 · How | 4 | 1:00 | 13:20 | Responsive resizes, adaptive decides. The four questions. | "We're reconsidering the layout and interaction model." |
| 11 | The window is your constraint | 5 · How | 5 | 1:00 | 14:20 | Split screen, outer display, resized window, freeform. Then the question. | "How much space does my app actually have?" |
| 12 | **Demo 1** · Window size classes | 5 · How | 1 | 2:30 | 16:50 | Playground: the width class changes as you resize. | "We're asking what space we have available." |
| 13 | **Demo 2** · Adaptive navigation | 5 · How | 1 | 2:00 | 18:50 | `NavigationSuiteScaffold`: bar, rail, drawer. | "We're adapting the application we already have." |
| 14 | **Demo 3** · List-detail | 5 · How | 1 | 2:30 | 21:20 | `ListDetailPaneScaffold`. The selection survives a resize. | "A lot of places where this pattern makes sense." |
| 15 | **Demo 4** · Supporting panes | 5 · How | 1 | 1:30 | 22:50 | `SupportingPaneScaffold`. More utility, not more whitespace. | "...not just more whitespace." |
| 16 | **Demo 5** · It's not just scaffolds | 5 · How | 1 | 1:30 | 24:20 | `FlexBox` chips, `Grid` columns, a form capped at 560 dp. | "Not just a layout decision." |
| 17 | **Demo 6** · Staggered grids | 5 · How | 1 | 1:30 | 25:50 | `LazyVerticalStaggeredGrid`: uneven cards packed into columns. | "Lazy, so it suits long feeds." |
| 18 | The biggest unblocker | 5 · How | 4 | 1:00 | 26:50 | Compose Multiplatform: the adaptive work ships to desktop too. This deck is the proof. | "Mostly design decisions, not engineering." |
| 19 | Meet them where they are | 6 · Takeaway | 4 | 1:30 | 28:20 | Four takeaways, one per press. | "Meet them where they are." |
| 20 | Thank you | 6 · Takeaway | 0 | 0:10 | 28:30 | Wordmark grows back out of the header; "Thank you!" drops in, then "Asante sana". Balloons and confetti; click for a burst. | — |

## Checkpoints

Glance at the presenter timer at these points. If you're behind, use the cut list.

| After slide | Timer should read about | Behind by more than 1 min? |
|---|---|---|
| 4 (Follow the money) | 6:30 | Cut slide 5 down to Notion, Canva and Notability. |
| 9 (Anyway) | 12:20 | Skip Demos 4 and 6 and talk over their slides. |
| 14 (Demo 3) | 21:20 | Do Demos 5 and 6 as code only (one press, then talk). |

## Cut list, in order

1. Slide 5: talk to three of the six cards. You still have to press through all six. (Saves 45 s.)
2. Demo 6: don't touch the playground. Press once for the code and give the one-line pitch. (Saves 1 min.)
3. Demo 4: same, code only. (Saves 1 min.)
4. Demo 5: same, code only. (Saves 1 min.)
5. Slide 7: drop the sensors point. (Saves 30 s.)

Never cut: the Googler opener, slide 4's correlation caveat, the "Anyway." transition, Demos 1 to 3, the Compose Multiplatform slide, and the closing line.

## Demo flow: the playground

All six demos run in one playground: a resizable, scaled-down window holding one demo app. The app has five destinations: Inbox, Docs, Gallery, Feed, and Settings. Each demo slide opens the app on the destination it needs. The code is in `segments/demos/.../playground/`.

**Controls (same on every demo slide)**

- **Preset chips** above the window: Phone (360 dp), Foldable (673), Tablet (900), Laptop (1280), Desktop (1600). Tapping a chip animates the window to that width.
- **Drag handle** on the window's right edge, for a continuous resize.
- **Readout** next to the chips, for example "673 dp · Medium".
- **Next arrow:** slides in the code card, with lambdas folded so the shape reads first. The card's buttons unfold the lambdas, switch light/dark, and open the code fullscreen (`FocusKotlinViewer`, with zoom). The next arrow press after the card moves to the next slide.

| Demo | Opens on | Do this on stage | What it does NOT show |
|---|---|---|---|
| 1. Window size classes | Size class screen, Phone | Tap Phone, Foldable, Tablet, Laptop, Desktop. Point at the highlighted class and "Navigation for this width". | Device detection. It reads window width only. |
| 2. Navigation | Inbox, Phone | Drag from Phone to Desktop. Bottom bar becomes a rail at 600 dp and a drawer at 1200 dp. Tap between destinations. | Back stack and deep links. |
| 3. List-detail | Inbox, Phone | On Phone, open a message (it fills the window and shows a back arrow). Tap Tablet: the same message sits beside the list. Back to Phone: still open. | State across process death. |
| 4. Supporting pane | Docs, Phone | On Phone, tap **Contents**. Tap Laptop: contents and properties stay beside the document. | — |
| 5. Content | Gallery, Phone | Drag wider slowly: the chips rewrap (`FlexBox`) and the grid goes 2 → 3 → 4 → 5 columns (`Grid`). Then open **Settings** at Desktop: the form stops at 560 dp. | Input adaptation (keyboard, mouse, stylus). Mention it only. |
| 6. Staggered grid | Feed, Phone | Drag from Phone to Desktop: two columns become as many as fit at 160 dp each. The "Feed" header spans the full line. | Performance with real images. The cards are coloured boxes. |

**Things to say once, during Demo 1:**

- The playground simulates the window. Inside the deck, `currentWindowAdaptiveInfo()` would report the fullscreen projector window, so the demo app reads the playground's size instead. A real app calls `currentWindowAdaptiveInfo()`, and that's what the code card shows.
- `Grid` and `FlexBox` are experimental APIs (Compose 1.11+).

**Which window to drive:** the presenter window and the audience window each hold their own playground state. Drive the demo on the audience window, the one being projected, so the room sees every click. Use the presenter window only for arrow presses and notes.

**Fallback:** if the playground misbehaves, press the next arrow to show the code and talk through it. Don't debug on stage. (A pre-recorded clip per demo is still a to-do. See `video.md` for how to bundle one.)

## Energy map

- High: the opener (slide 1), the button line (slide 6), "Anyway." (slide 9), the Compose Multiplatform reveal (slide 18), the close (slides 19 and 20).
- Slide 4 is a deliberate drop: say the numbers plainly, land one joke, then give the caveat in a straight voice. It's what earns credibility for the technical half.
- Demos stay at medium energy: steady, narrated, no jokes while dragging.

## Pre-talk checklist

- [ ] Re-check the slide 4 numbers against the sources in `talk.md`. Google updates them, and the 9x figure is spending, not time.
- [ ] Run the deck on the demo machine (the **Desktop** run configuration) and click through all six demos on the audience window.
- [ ] Check the playground is readable from the back of the room at the projector's resolution. Phone width is small at the playground's scale.
- [ ] Rehearse arrow presses: every reveal slide needs its full count before it moves on (see the Reveals column).
- [ ] Record a fallback clip for each demo.
- [ ] Find out whether there's a Googler in the room before you go on.
