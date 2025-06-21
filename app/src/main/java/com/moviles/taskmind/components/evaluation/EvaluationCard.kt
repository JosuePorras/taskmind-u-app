package com.moviles.taskmind.components.evaluation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
    val isCompactScreen = screenWidth < 600.dp
    var expandedState by remember { mutableStateOf(false) }

    val resolvedColor = darkenColorHex(colorMain)
    val backColor = parseColorString(colorMain)

    val minHeight = if (isCompactScreen) 100.dp else 120.dp

    Box(
        modifier = Modifier
            .then(if (isCompactScreen) Modifier.fillMaxWidth() else Modifier.widthIn(max = 500.dp))
            .padding(if (isCompactScreen) 14.dp else 16.dp)
            .clickable { expandedState = !expandedState }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = minHeight)
                .border(1.dp, resolvedColor, RoundedCornerShape(16.dp))
                .animateContentSize(tween(300, easing = LinearOutSlowInEasing)),
            colors = CardDefaults.cardColors(containerColor = backColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    EvaluationCardHeader(
                        title = courseName,
                        progressColor = resolvedColor,
                        professor = professor,
                        isCompactScreen = isCompactScreen,
                        expandedState = expandedState,
                        onExpandToggle = { expandedState = !expandedState }
                    )
                    Spacer(modifier = Modifier.height(if (isCompactScreen) 8.dp else 12.dp))
                    EvaluationCardProgressSection(progressBar, resolvedColor, isCompactScreen)
                    Spacer(modifier = Modifier.height(if (isCompactScreen) 8.dp else 16.dp))
                }

                if (expandedState) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        HorizontalDivider(thickness = 1.dp, color = resolvedColor)

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 260.dp)
                                .background(Color.White)
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(6.dp),
                                contentPadding = PaddingValues(vertical = 4.dp)
                            ) {
                                items(evaluations) { item ->
                                    TaskCard(
                                        title = item.title,
                                        subtitle = item.subtitle,
                                        date = item.date,
                                        backgroundColor = backColor,
                                        iconColor = resolvedColor,
                                        icon = item.icon,
                                        buttonAction = true,
                                        modifier = Modifier.fillMaxWidth(),
                                        onEdit = { item.onEdit?.invoke() },
                                        onDelete = { item.onDelete?.invoke() },
                                        evaluationId = item.typeId
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}