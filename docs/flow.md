# Talk flow: Adaptive UI in Jetpack Compose

A one-page run-of-show for `talk.md`. Use it to rehearse timing and transitions. The full script, speaker lines, and sources are in `talk.md`.

**Target:** 28 minutes, which leaves 2 minutes of slack in a 30-minute slot.
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

| # | Slide | Part | Time | Running | Beat | Exit line (into the next slide) |
|---|---|---|---|---|---|---|
| 1 | Title | Setup | 2:30 | 2:30 | Googler call-out, "No offence, Google", chicken-and-egg loop | "It's a bit of a feedback loop." |
| 2 | Forget Google's bottom line | Why care | 2:30 | 5:00 | Screen-time anecdote. Productive work happens on a PC. Large-screen buyers already had a need. | "...an opportunity to become part of their workflow." |
| 3 | Follow the money | Why care | 1:30 | 6:30 | 580M+ devices, 9x spend, 14x foldables, ~3x time. Correlation, not causation. | "Is your app on the screen they're holding?" |
| 4 | Applications already doing this | Why care | 2:00 | 8:30 | Notion, Canva, Lightroom, Figma, Power BI. Desktop creates, mobile captures and checks. | "The context in which we use these devices often differs." |
| 5 | Are we abandoning mobile? | Mobile | 1:00 | 9:30 | M-PESA, 200 bob. "I want a button." | "The interaction is relatively short." |
| 6 | The power of compact interactions | Mobile | 1:30 | 11:00 | Widgets, shortcuts, notification actions. Phones have sensors, NFC, biometrics. | "...because of the device the user is holding." |
| 7 | Different devices, different contexts | Mobile | 1:00 | 12:00 | Deep, expanded, immediate engagement. Tendencies, not rules. | "We're building for people who happen to use screens." |
| 8 | Accommodating the user | Transition | 0:20 | 12:20 | "...accommodate my needs..." Pause. "Anyway. Let's talk about Compose." | Cut straight to slide 9. No explanation. |
| 9 | What does adaptive UI mean? | How | 1:00 | 13:20 | Responsive vs adaptive. Rethink, don't just resize. | "We're reconsidering the layout and interaction model." |
| 10 | The window is your constraint | How | 1:00 | 14:20 | Split screen, outer display, freeform windows. | "How much space does my app actually have?" |
| 11 | Window size classes + **Demo 1** | How | 2:30 | 16:50 | Compact, medium, expanded. Resize live. | "We're asking what space we have available." |
| 12 | Adaptive navigation + **Demo 2** | How | 2:30 | 19:20 | `NavigationSuiteScaffold`: bar, then rail, then drawer. | "We're adapting the application we already have." |
| 13 | List-detail + **Demo 3** | How | 3:00 | 22:20 | `ListDetailPaneScaffold`. Selection survives resize. | "A lot of places where this pattern makes sense." |
| 14 | Supporting panes + **Demo 4** | How | 2:00 | 24:20 | `SupportingPaneScaffold`. More utility, not more whitespace. | "...not just more whitespace." |
| 15 | It's not just scaffolds + **Demo 5** | How | 2:00 | 26:20 | Grids, max widths, input methods. A product decision, not just a layout one. | "Not just a layout decision." |
| 16 | What should you take home? | Takeaway | 1:30 | 27:50 | Four takeaways. "Give them both experiences." | "Meet them where they are." |
| 17 | Thank you | Takeaway | 0:10 | 28:00 | Links to the sample project. | — |

## Checkpoints

Glance at the presenter timer at these points. If you're behind, use the cut list.

| After slide | Should read about | Behind by more than 1 min? |
|---|---|---|
| 3 (Follow the money) | 6:30 | Cut slide 4 down to Notion and M-PESA. |
| 8 (Anyway) | 12:20 | Drop Demo 4 (supporting pane) and talk over the slide. |
| 13 (List-detail) | 22:20 | Merge Demo 5 into a 30-second slide talk. |

## Cut list, in order

1. Slide 4: drop Figma and Power BI, keep Notion and Canva. (Saves 45 s.)
2. Demo 4: show the slide only, no live demo. (Saves 1 min.)
3. Demo 5: name the techniques and skip the code. (Saves 1 min.)
4. Slide 6: drop the sensors paragraph. (Saves 30 s.)

Never cut: the Googler opener, slide 3's correlation caveat, the "Anyway." transition, Demos 1 to 3, and the closing line.

## Demo flow

All five demos run on one sample app that grows during the talk. Don't switch projects between demos.

| Demo | Starting state | What changes on stage | What to say it does NOT show |
|---|---|---|---|
| 1. Window size classes | App reads its size class and shows it on screen | Resize the window across the compact, medium, and expanded breakpoints | It reports window space, not the device type. |
| 2. Navigation | Bottom bar at compact width | Widen the window: rail, then drawer | Back-stack behavior is out of scope. |
| 3. List-detail | List, tap through to detail | Widen the window: two panes, and the selection is kept | Shows that state is kept across resize, not process death. |
| 4. Supporting pane | Document with a hidden side panel | Widen the window: the panel stays visible | — |
| 5. Adaptive content | Grid and form at phone width | Widen the window: more columns, form capped at max width | Input adaptation is only mentioned, not demoed (unless time allows). |

Fallback: if the live demo fails, play the pre-recorded clip of that demo (see `video.md`) and keep talking. Don't debug on stage.

## Energy map

- High: the opener (slide 1), the button line (slide 5), "Anyway." (slide 8), the close (slide 16).
- Slide 3 is a deliberate drop: say the numbers plainly, land one joke, then give the caveat in a straight voice. It's what earns credibility for the technical half.
- Demos stay at medium energy: steady, narrated, no jokes while typing.

## Pre-talk checklist

- [ ] Re-check the slide 3 numbers against the sources in `talk.md`. Google updates them, and the 9x figure is spending, not time.
- [ ] Sample app builds and resizes cleanly on the demo machine.
- [ ] Demo fallback videos are bundled locally, not remote URLs.
- [ ] Presenter timer reads `SESSION_DURATION` and is set for the slot.
- [ ] Find out whether there's a Googler in the room before you go on.
