package com.moviles.taskmind.components.evaluation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.moviles.taskmind.components.homepage.TaskCard
import com.moviles.taskmind.utils.darkenColorHex
import com.moviles.taskmind.utils.parseColorString

@Composable
fun EvaluationCard(
    courseName: String,
    professor: String,
    progressBar: Int,
    colorMain: String,
    evaluations: List<EvaluationItem>,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val isCompactScreen = screenWidth < 300.dp
    var expandedState by remember { mutableStateOf(false) }

    val resolvedColor = darkenColorHex(colorMain)
    val backColor = parseColorString(colorMain)

    val minHeight = if (isCompactScreen) 100.dp else 120.dp
    val maxHeight = if (isCompactScreen) 450.dp else 480.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .heightIn(min = minHeight, max = if (expandedState) maxHeight else minHeight)
            .border(1.dp, resolvedColor, RoundedCornerShape(16.dp))
            .animateContentSize(tween(300, easing = LinearOutSlowInEasing)),
        colors = CardDefaults.cardColors(containerColor = backColor),
        shape = RoundedCornerShape(16.dp),
        onClick = { expandedState = !expandedState }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                EvaluationCardHeader(courseName, resolvedColor, professor, isCompactScreen, onEdit, onDelete, expandedState)
                Spacer(modifier = Modifier.height(8.dp))
                EvaluationCardProgressSection(progressBar, resolvedColor, isCompactScreen)
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (expandedState) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                    evaluations.forEach { item ->
                        TaskCard(
                            title = item.title,
                            subtitle = item.subtitle,
                            date = item.date,
                            backgroundColor = item.iconBackground,
                            iconColor = item.iconTint,
                            icon = item.icon
                        )
                    }
                }
            }
        }
    }
}