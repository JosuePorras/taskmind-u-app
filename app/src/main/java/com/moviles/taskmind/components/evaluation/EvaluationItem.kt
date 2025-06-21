package com.moviles.taskmind.components.evaluation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class EvaluationItem(
    val typeId: Int,
    val title: String,
    val subtitle: String,
    val date: String,
    val icon: ImageVector,
    val iconTint: Color = Color(0xFF2BD4BD),
    val iconBackground: Color = Color(0xFF9DF3AF).copy(alpha = 0.2f),
    val onEdit: (() -> Unit)? = null,
    val onDelete: (() -> Unit)? = null,
)
