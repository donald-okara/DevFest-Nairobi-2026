# Talk flow: Adaptive UI in Jetpack Compose

A one-page run-of-show for the deck. Use it to rehearse timing, reveals, and the live demos. The full script, speaker lines, and sources are in `talk.md`. The deck is defined in `composeApp/.../presentation/ui/SkiPresentationSlides.kt`, and its speaker notes are in `SlidesNotes.kt`.

**Target:** 28 minutes, which leaves 2 minutes of slack in a 30-minute slot. The presenter timer is set to 30 minutes (`SESSION_DURATION`).
**Shape:** about 12 minutes of argument (why), then about 14 minutes of Compose (how), then a 2-minute close.

## Arc

```
Roast Google ─► Fine, Google has a point ─► The numbers ─► Big screens are for deep work
     ─► Phones are for "I want a button" ─► Devices are contexts, not rules
     ─► "...accommodate my needs" / "Anyway." ─► Compose: window, nav, panes, content
     ─► Meet them where they are
```

The argument is one sentence: **build for the context people are in, and let the window decide the layout, not the device.**

## Run of show

Slide numbers match the deck and its table of contents (press **T** in the presenter window). Slide numbers in `talk.md` differ from slide 2 on, because the deck gives the chicken-and-egg loop its own slide.

**Reveals** is how many arrow presses the slide takes before the next press moves on. Each reveal shows one point, card, or number.

| # | Slide | Part | Reveals | Time | Running | Beat | Exit line |
|---|---|---|---|---|---|---|---|
| 1 | Title | 1 · Setup | 0 | 1:30 | 1:30 | Googler call-out. "No offence, Google." | "...a phone app with a lot of empty space around it." |
| 2 | Chicken and egg | 1 · Setup | 4 | 1:00 | 2:30 | The feedback loop, one card per press. | "It's a bit of a feedback loop." |
| 3 | Forget Google's bottom line | 2 · Why care | 4 | 2:30 | 5:00 | Screen-time anecdote. Real work happens on a PC. Big-screen buyers already had a need. | "...an opportunity to become part of their workflow." |
| 4 | Follow the money | 2 · Why care | 5 | 1:30 | 6:30 | 580M+, 9x, 14x, ~3x, then the caveat. Correlation, not causation. | "Is your app on the screen they're holding?" |
| 5 | Apps already doing this | 2 · Why care | 5 | 2:00 | 8:30 | Notion, Canva, Lightroom, Figma, Power BI. | "The context in which we use these devices often differs." |
| 6 | Are we abandoning mobile? | 3 · Mobile | 1 | 1:00 | 9:30 | M-PESA, 200 bob. The reveal is "I want a button." | "The interaction is relatively short." |
| 7 | Compact interactions | 3 · Mobile | 4 | 1:30 | 11:00 | Widgets, shortcuts, notification actions, sensors. | "...because of the device the user is holding." |
| 8 | Different devices, different contexts | 3 · Mobile | 3 | 1:00 | 12:00 | Deep, expanded, immediate engagement. | "We're building for people who happen to use screens." |
| 9 | Accommodating the user | 4 · Transition | 1 | 0:20 | 12:20 | The setup line, pause, then reveal "Anyway. Let's talk about Compose." | Go straight to slide 10. No explanation. |
| 10 | What adaptive UI means | 5 · How | 4 | 1:00 | 13:20 | Responsive resizes, adaptive decides. The four questions. | "We're reconsidering the layout and interaction model." |
| 11 | The window is your constraint | 5 · How | 5 | 1:00 | 14:20 | Split screen, outer display, resized window, freeform. Then the question. | "How much space does my app actually have?" |
| 12 | **Demo 1** · Window size classes | 5 · How | 1 | 2:30 | 16:50 | Playground: the width class changes as you resize. | "We're asking what space we have available." |
| 13 | **Demo 2** · Adaptive navigation | 5 · How | 1 | 2:30 | 19:20 | `NavigationSuiteScaffold`: bar, rail, drawer. | "We're adapting the application we already have." |
| 14 | **Demo 3** · List-detail | 5 · How | 1 | 3:00 | 22:20 | `ListDetailPaneScaffold`. The selection survives a resize. | "A lot of places where this pattern makes sense." |
| 15 | **Demo 4** · Supporting panes | 5 · How | 1 | 2:00 | 24:20 | `SupportingPaneScaffold`. More utility, not more whitespace. | "...not just more whitespace." |
| 16 | **Demo 5** · It's not just scaffolds | 5 · How | 1 | 2:00 | 26:20 | `FlexBox` chips, `Grid` columns, a form capped at 560 dp. | "Not just a layout decision." |
| 17 | Meet them where they are | 6 · Takeaway | 4 | 1:30 | 27:50 | Four takeaways, one per press. | "Meet them where they are." |
| 18 | Thank you | 6 · Takeaway | 0 | 0:10 | 28:00 | — | — |

## Checkpoints

Glance at the presenter timer at these points. If you're behind, use the cut list.

| After slide | Timer should read about | Behind by more than 1 min? |
|---|---|---|
| 4 (Follow the money) | 6:30 | Cut slide 5 down to Notion and Canva. |
| 9 (Anyway) | 12:20 | Skip Demo 4 and talk over the slide. |
| 14 (Demo 3) | 22:20 | Do Demo 5 as code only (one press, then talk). |

## Cut list, in order

1. Slide 5: drop Figma and Power BI, keep Notion and Canva. You still have to press through all five cards. (Saves 45 s.)
2. Demo 4: don't touch the playground. Press once for the code and summarise it. (Saves 1 min.)
3. Demo 5: same as Demo 4, code only. (Saves 1 min.)
4. Slide 7: drop the sensors point. (Saves 30 s.)

Never cut: the Googler opener, slide 4's correlation caveat, the "Anyway." transition, Demos 1 to 3, and the closing line.

## Demo flow: the playground

All five demos run in one playground: a resizable, scaled-down window holding one demo app. The app has four destinations: Inbox, Docs, Gallery, and Settings. Each demo slide opens the app on the destination it needs. The code is in `segments/demos/.../playground/`.

**Controls (same on every demo slide)**

- **Preset chips** above the window: Phone (360 dp), Foldable (673), Tablet (900), Laptop (1280), Desktop (1600). Tapping a chip animates the window to that width.
- **Drag handle** on the window's right edge, for a continuous resize.
- **Readout** next to the chips, for example "673 dp · Medium".
- **Next arrow:** slides in the code card. The next press after that moves to the next slide.

| Demo | Opens on | Do this on stage | What it does NOT show |
|---|---|---|---|
| 1. Window size classes | Size class screen, Phone | Tap Phone, Foldable, Tablet, Laptop, Desktop. Point at the highlighted class and "Navigation for this width". | Device detection. It reads window width only. |
| 2. Navigation | Inbox, Phone | Drag from Phone to Desktop. Bottom bar becomes a rail at 600 dp and a drawer at 1200 dp. Tap between destinations. | Back stack and deep links. |
| 3. List-detail | Inbox, Phone | On Phone, open a message (it fills the window and shows a back arrow). Tap Tablet: the same message sits beside the list. Back to Phone: still open. | State across process death. |
| 4. Supporting pane | Docs, Phone | On Phone, tap **Contents**. Tap Laptop: contents and properties stay beside the document. | — |
| 5. Content | Gallery, Phone | Drag wider slowly: the chips rewrap (`FlexBox`) and the grid goes 2 → 3 → 4 → 5 columns (`Grid`). Then open **Settings** at Desktop: the form stops at 560 dp. | Input adaptation (keyboard, mouse, stylus). Mention it only. |

**Things to say once, during Demo 1:**

- The playground simulates the window. Inside the deck, `currentWindowAdaptiveInfo()` would report the fullscreen projector window, so the demo app reads the playground's size instead. A real app calls `currentWindowAdaptiveInfo()`, and that's what the code card shows.
- `Grid` and `FlexBox` are experimental APIs (Compose 1.11+).

**Which window to drive:** the presenter window and the audience window each hold their own playground state. Drive the demo on the audience window, the one being projected, so the room sees every click. Use the presenter window only for arrow presses and notes.

**Fallback:** if the playground misbehaves, press the next arrow to show the code and talk through it. Don't debug on stage. (A pre-recorded clip per demo is still a to-do. See `video.md` for how to bundle one.)

## Energy map

- High: the opener (slide 1), the button line (slide 6), "Anyway." (slide 9), the close (slide 17).
- Slide 4 is a deliberate drop: say the numbers plainly, land one joke, then give the caveat in a straight voice. It's what earns credibility for the technical half.
- Demos stay at medium energy: steady, narrated, no jokes while dragging.

## Pre-talk checklist

- [ ] Re-check the slide 4 numbers against the sources in `talk.md`. Google updates them, and the 9x figure is spending, not time.
- [ ] Run the deck on the demo machine (the **Desktop** run configuration) and click through all five demos on the audience window.
- [ ] Check the playground is readable from the back of the room at the projector's resolution. Phone width is small at the playground's scale.
- [ ] Rehearse arrow presses: every reveal slide needs its full count before it moves on (see the Reveals column).
- [ ] Record a fallback clip for each demo.
- [ ] Find out whether there's a Googler in the room before you go on.
