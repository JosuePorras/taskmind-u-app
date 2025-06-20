package com.moviles.taskmind.components.evaluation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviles.taskmind.components.homepage.TaskCard
import com.moviles.taskmind.pages.dateFormat

@Composable
fun EvaluationInfo(
    title: String? = null,
    items: List<EvaluationItem>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column {


            items.forEachIndexed { index, item ->
                EvaluationItemRow(
                    item = item,
                    showDivider = index < items.lastIndex
                )
            }

        }
    }
}

@Composable
private fun EvaluationItemRow(
    item: EvaluationItem,
    showDivider: Boolean
) {
    Column{
        TaskCard(
            title = item.title,
            subtitle = item.subtitle,
            date = dateFormat("2025-06-25"),
            backgroundColor = Color(0xFFE3F2FD), // Azul claro
            iconColor = Color(0xFF1565C0),       // Azul oscuro
            icon = Icons.Default.Book
        )

    }

}