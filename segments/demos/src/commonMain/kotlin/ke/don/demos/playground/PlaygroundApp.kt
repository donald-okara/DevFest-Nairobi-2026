package ke.don.demos.playground

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalFlexBoxApi
import androidx.compose.foundation.layout.ExperimentalGridApi
import androidx.compose.foundation.layout.FlexBox
import androidx.compose.foundation.layout.FlexWrap
import androidx.compose.foundation.layout.Grid
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Toc
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/** Which part of the talk the playground is showing. Each demo slide opens on its own stage. */
enum class PlaygroundStage { SizeClasses, Navigation, ListDetail, SupportingPane, Content }

enum class Destination(val label: String, val icon: ImageVector) {
    Inbox("Inbox", Icons.Filled.Email),
    Docs("Docs", Icons.Filled.Description),
    Gallery("Gallery", Icons.Filled.PhotoLibrary),
    Settings("Settings", Icons.Filled.Settings),
}

private fun PlaygroundStage.startDestination() = when (this) {
    PlaygroundStage.SizeClasses, PlaygroundStage.Navigation, PlaygroundStage.ListDetail -> Destination.Inbox
    PlaygroundStage.SupportingPane -> Destination.Docs
    PlaygroundStage.Content -> Destination.Gallery
}

/**
 * The single demo app that grows through the talk. Stage 1 shows only the size class;
 * from stage 2 on it is a full app with adaptive navigation and four destinations.
 */
@Composable
fun PlaygroundApp(stage: PlaygroundStage) {
    if (stage == PlaygroundStage.SizeClasses) {
        SizeClassScreen()
    } else {
        AdaptiveApp(start = stage.startDestination())
    }
}

// Demo 1: size classes

@Composable
private fun SizeClassScreen() {
    val window = LocalPlaygroundWindow.current
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("windowSizeClass", style = MaterialTheme.typography.titleMedium)
        Text(
            window.widthClass.label,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            WidthClass.entries.forEach { cls ->
                val active = cls == window.widthClass
                Box(
                    Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        "${cls.label} ≥${cls.minDp.toInt()}",
                        color = if (active) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
        Text(
            "Navigation for this width: " + navigationTypeLabel(window.widthClass),
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

private fun navigationTypeFor(widthClass: WidthClass): NavigationSuiteType = when (widthClass) {
    WidthClass.Compact -> NavigationSuiteType.NavigationBar
    WidthClass.Medium, WidthClass.Expanded -> NavigationSuiteType.NavigationRail
    WidthClass.Large, WidthClass.ExtraLarge -> NavigationSuiteType.NavigationDrawer
}

private fun navigationTypeLabel(widthClass: WidthClass) = when (navigationTypeFor(widthClass)) {
    NavigationSuiteType.NavigationBar -> "bottom bar"
    NavigationSuiteType.NavigationRail -> "navigation rail"
    else -> "navigation drawer"
}

// Demo 2: adaptive navigation

@Composable
private fun AdaptiveApp(start: Destination) {
    val window = LocalPlaygroundWindow.current
    var current by rememberSaveable { mutableStateOf(start) }

    NavigationSuiteScaffold(
        layoutType = navigationTypeFor(window.widthClass),
        navigationSuiteItems = {
            Destination.entries.forEach { dest ->
                item(
                    selected = dest == current,
                    onClick = { current = dest },
                    icon = { Icon(dest.icon, contentDescription = dest.label) },
                    label = { Text(dest.label) },
                )
            }
        },
    ) {
        when (current) {
            Destination.Inbox -> InboxScreen()
            Destination.Docs -> DocsScreen()
            Destination.Gallery -> GalleryScreen()
            Destination.Settings -> SettingsScreen()
        }
    }
}

// Demo 3: list-detail

private data class Message(val id: Int, val from: String, val subject: String, val body: String)

private val messages = listOf(
    Message(0, "Wanjiru", "Slides for Saturday", "Can you send the deck before Friday? We're printing the schedule."),
    Message(1, "M-PESA", "Confirmed. Ksh200 sent", "Ksh200.00 sent to Kamau. New balance is Ksh1,340.00."),
    Message(2, "Otieno", "Foldable test device", "The Pixel Fold is on my desk. Bring it back by Monday."),
    Message(3, "GDG Nairobi", "Speaker check-in", "Check in at the speaker desk by 8:30. Coffee is on us."),
    Message(4, "Achieng", "Code review", "Left two comments on the list-detail PR. Mostly naming."),
    Message(5, "Calendar", "DevFest Nairobi 2026", "Saturday, 9:00 at Sarit Expo Centre."),
)

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun InboxScreen() {
    val window = LocalPlaygroundWindow.current
    val directive = calculatePaneScaffoldDirective(window.adaptiveInfo)
    val navigator = rememberListDetailPaneScaffoldNavigator<Int>(scaffoldDirective = directive)
    val scope = rememberCoroutineScope()
    val selectedId = navigator.currentDestination?.contentKey
    val singlePane = directive.maxHorizontalPartitions == 1

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                LazyColumn(Modifier.fillMaxSize()) {
                    item { PaneTitle("Inbox") }
                    items(messages, key = { it.id }) { message ->
                        ListItem(
                            headlineContent = { Text(message.from, fontWeight = FontWeight.Bold) },
                            supportingContent = { Text(message.subject) },
                            leadingContent = { Avatar(message.from) },
                            colors = ListItemDefaults.colors(
                                containerColor = if (message.id == selectedId && !singlePane) {
                                    MaterialTheme.colorScheme.secondaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surface
                                }
                            ),
                            modifier = Modifier.clickable {
                                scope.launch { navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, message.id) }
                            },
                        )
                    }
                }
            }
        },
        detailPane = {
            AnimatedPane {
                val message = messages.firstOrNull { it.id == selectedId }
                if (message == null) {
                    EmptyState("Pick a conversation")
                } else {
                    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        if (singlePane) {
                            IconButton(onClick = { scope.launch { navigator.navigateBack() } }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        }
                        Text(message.subject, style = MaterialTheme.typography.headlineMedium)
                        Text("From ${message.from}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        HorizontalDivider()
                        Text(message.body, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        },
    )
}

// Demo 4: supporting pane

private val sections = listOf("Introduction", "Window size classes", "Navigation", "Panes", "Grids and FlexBox", "Wrap-up")

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun DocsScreen() {
    val window = LocalPlaygroundWindow.current
    val navigator = rememberSupportingPaneScaffoldNavigator(
        scaffoldDirective = calculatePaneScaffoldDirective(window.adaptiveInfo)
    )
    val scope = rememberCoroutineScope()
    val supportingHidden = navigator.scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Hidden

    SupportingPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        mainPane = {
            AnimatedPane {
                Column(
                    Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Talk notes.md", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.weight(1f))
                        if (supportingHidden) {
                            TextButton(onClick = {
                                scope.launch { navigator.navigateTo(SupportingPaneScaffoldRole.Supporting) }
                            }) {
                                Icon(Icons.Filled.Toc, contentDescription = null)
                                Text("Contents")
                            }
                        }
                    }
                    sections.forEach { section ->
                        Text(section, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text(
                            "Adaptive layouts respond to the space the window actually has. " +
                                "This section covers $section and how it changes between a phone and a desktop.",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }
        },
        supportingPane = {
            AnimatedPane {
                Column(
                    Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceContainer).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (window.widthClass == WidthClass.Compact) {
                        IconButton(onClick = { scope.launch { navigator.navigateBack() } }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                    Text("Contents", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    sections.forEachIndexed { i, s -> Text("${i + 1}. $s") }
                    HorizontalDivider(Modifier.padding(vertical = 8.dp))
                    Text("Properties", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Words: 1,240")
                    Text("Edited: today")
                }
            }
        },
    )
}

// Demo 5: Grid and FlexBox

private val filters = listOf("All", "Nairobi", "Speakers", "Workshops", "Food", "After-party", "Swag", "Codelabs")
private val tileColors = listOf(
    Color(0xFF4285F4), Color(0xFFEA4335), Color(0xFFFBBC04), Color(0xFF34A853),
    Color(0xFF7BAAF7), Color(0xFFF28B82), Color(0xFFFDD663), Color(0xFF81C995),
)

@OptIn(ExperimentalFlexBoxApi::class, ExperimentalGridApi::class)
@Composable
private fun GalleryScreen() {
    var selected by rememberSaveable { mutableStateOf("All") }
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        PaneTitle("Gallery")

        // FlexBox: chips wrap onto as many rows as the width needs.
        FlexBox(config = {
            wrap(FlexWrap.Wrap)
            gap(8.dp)
        }) {
            filters.forEach { filter ->
                FilterChip(
                    selected = filter == selected,
                    onClick = { selected = filter },
                    label = { Text(filter) },
                )
            }
        }

        // Grid: the column count comes from the width the grid is given.
        Grid(config = {
            val width = constraints.maxWidth.toDp()
            val columns = when {
                width < 600.dp -> 2
                width < 900.dp -> 3
                width < 1200.dp -> 4
                else -> 5
            }
            val gap = 12.dp
            val cell = ((width - gap * (columns - 1)) / columns).coerceAtLeast(0.dp)
            repeat(columns) { column(cell) }
            repeat((tileColors.size + columns - 1) / columns) { row(cell * 0.75f) }
            gap(gap)
        }) {
            tileColors.forEachIndexed { i, color ->
                Box(
                    Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)).background(color),
                    contentAlignment = Alignment.BottomStart,
                ) {
                    Text("Photo ${i + 1}", color = Color.White, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

// Demo 5: constrained content width

@Composable
private fun SettingsScreen() {
    var name by rememberSaveable { mutableStateOf("Donald Isoe") }
    var notifications by rememberSaveable { mutableStateOf(true) }
    Box(Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.TopCenter) {
        // The form stops growing at 560dp instead of stretching across a desktop window.
        Card(Modifier.fillMaxWidth().widthIn(max = 560.dp)) {
            Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Profile", style = MaterialTheme.typography.headlineSmall)
                OutlinedTextField(name, { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Notifications", modifier = Modifier.weight(1f))
                    Switch(notifications, { notifications = it })
                }
                Button(onClick = {}, modifier = Modifier.align(Alignment.End)) { Text("Save") }
            }
        }
    }
}

// Shared bits

@Composable
private fun PaneTitle(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
    )
}

@Composable
private fun Avatar(name: String) {
    Box(
        Modifier.size(40.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center,
    ) {
        Text(name.first().toString(), color = MaterialTheme.colorScheme.onPrimaryContainer, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun EmptyState(text: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.titleMedium)
    }
}
