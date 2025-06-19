package com.moviles.taskmind.components.evaluation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EvaluationCardFooter(
    evaluation: String,
    borderColor: Color,
    isCompactScreen: Boolean
) {
    val footerHeight = if (isCompactScreen) 150.dp else 350.dp
    Box(modifier = Modifier
            .fillMaxWidth()
            .height(footerHeight)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
            .padding(horizontal = if (isCompactScreen) 16.dp else 24.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),

        ) {
            EvaluationInfo(
                title = "Detalle de Evaluaciones",
                items = listOf(
                    EvaluationItem(
                        title = "Evaluación",
                        subtitle = evaluation,
                        icon = Icons.Default.EventNote,
                        iconTint = Color(0XFF2BD4BD),
                        iconBackground = Color(0xFFDCFCE7)
                    ),
                    EvaluationItem(
                        title = "Evaluación",
                        subtitle = evaluation+" 2",
                        icon = Icons.Default.EventNote,
                        iconTint = Color(0XFF2BD4BD),
                        iconBackground = Color(0xFFDCFCE7)
                    ),
                    EvaluationItem(
                        title = "Evaluación",
                        subtitle = evaluation+" 3",
                        icon = Icons.Default.EventNote,
                        iconTint = Color(0XFF2BD4BD),
                        iconBackground = Color(0xFFDCFCE7)
                    ),
                ),
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}