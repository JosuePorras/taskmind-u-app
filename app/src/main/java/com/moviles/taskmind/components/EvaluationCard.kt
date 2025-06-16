package com.moviles.taskmind.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviles.taskmind.components.evaluation.EvaluationCardFooter
import com.moviles.taskmind.components.evaluation.EvaluationCardHeader
import com.moviles.taskmind.components.evaluation.EvaluationCardProgressSection

import com.moviles.taskmind.utils.darkenColorHex
import com.moviles.taskmind.utils.parseColorString

@Composable
fun EvaluationCard(
    title: String,
    professor: String,
    //evaluations: List<>,
    evaluation: String,
    progressBar: Int,
    colorMain: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val isCompactScreen = screenWidth < 600.dp

    val resolvedColor = darkenColorHex(colorMain)
    val backColor = parseColorString(colorMain)

    Card (
        modifier = Modifier
            .then(
                if(isCompactScreen){
                    Modifier.fillMaxSize()
                } else {
                    Modifier.widthIn(max = 500.dp)
                }
            )
            .padding(if (isCompactScreen) 14.dp else 16.dp)
            .border(
                width = 1.dp,
                color = resolvedColor,
                shape = RoundedCornerShape(16.dp)
            )
            .heightIn(min = if (isCompactScreen) 300.dp else 350.dp),
                colors = CardDefaults.cardColors(containerColor = backColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                EvaluationCardHeader(title, resolvedColor, professor, isCompactScreen, onEdit, onDelete)

                Spacer(modifier = Modifier.height(if (isCompactScreen) 8.dp else 12.dp))

                EvaluationCardProgressSection(progressBar, resolvedColor, isCompactScreen)

                Spacer(modifier = Modifier.height(if (isCompactScreen) 8.dp else 12.dp))

            }
            //Footer
            EvaluationCardFooter(evaluation = evaluation, borderColor = resolvedColor, isCompactScreen)

        }
    }
}