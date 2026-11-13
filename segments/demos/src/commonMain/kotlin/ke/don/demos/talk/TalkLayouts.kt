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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import io.github.donald_okara.components.reveal.RevealBulletList
import io.github.donald_okara.components.reveal.RevealItem
import io.github.donald_okara.components.text.CalloutCard
import io.github.donald_okara.components.text.SlideTitle
import ke.don.domain.values.Values

/**
 * Title and points, one point per reveal step. Give the slide `steps = points.size`.
 */
@Composable
fun PointsSlide(
    title: String,
    points: List<String>,
    modifier: Modifier = Modifier,
    kicker: String? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Values.Dimens.largePadding),
        verticalArrangement = Arrangement.spacedBy(Values.Dimens.largePadding),
    ) {
        SlideTitle(title = title, kicker = kicker)
        RevealBulletList(items = points)
    }
}

/** One column of a [ColumnsSlide]: a heading and a short line under it. */
data class TalkColumn(val heading: String, val body: String)

/**
 * Title and side-by-side cards, one card per reveal step. Give the slide `steps = columns.size`.
 */
@Composable
fun ColumnsSlide(
    title: String,
    columns: List<TalkColumn>,
    modifier: Modifier = Modifier,
    kicker: String? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Values.Dimens.largePadding),
        verticalArrangement = Arrangement.spacedBy(Values.Dimens.largePadding),
    ) {
        SlideTitle(title = title, kicker = kicker)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Values.Dimens.mediumPadding),
        ) {
            columns.forEachIndexed { i, column ->
                RevealItem(index = i + 1, modifier = Modifier.weight(1f)) {
                    CalloutCard(title = column.heading, modifier = Modifier.fillMaxWidth()) {
                        Text(column.body, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}

/** A single line, centred, for punchlines and transitions. */
@Composable
fun StatementSlide(
    statement: String,
    modifier: Modifier = Modifier,
    subline: String? = null,
    statementStyle: TextStyle = MaterialTheme.typography.displayMedium,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Values.Dimens.largePadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = statement,
            style = statementStyle,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        subline?.let {
            RevealItem(index = 1) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = Values.Dimens.mediumPadding),
                )
            }
        }
    }
}
