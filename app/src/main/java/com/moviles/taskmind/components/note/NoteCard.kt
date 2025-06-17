package com.moviles.taskmind.components.note

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviles.taskmind.models.NoteDto
import java.text.SimpleDateFormat
import java.util.Locale


private val cardColors = listOf(
    Color(0xFFE1C4FF),
    Color(0xFFFFF2CC),
    Color(0xFFB8E6FF),
    Color(0xFFB8FFB8),
    Color(0xFFFFB8B8)
)


fun getCardColor(index: Int): Color {
    return cardColors[index % cardColors.size]
}


fun getCardBorderColor(index: Int): Color {
    val baseColor = getCardColor(index)
    return when (index % cardColors.size) {
        0 -> Color(0xFFB794E6)
        1 -> Color(0xFFE6D499)
        2 -> Color(0xFF85CCFF)
        3 -> Color(0xFF85E685)
        4 -> Color(0xFFE68585)
        else -> baseColor.copy(alpha = 0.8f)
    }
}

@Composable
fun NoteCard(
    colorMain: String,
    note: NoteDto,
    noteIndex: Int = 0,
    onEdit: (NoteDto) -> Unit,
    onDelete: () -> Unit
) {

    val backColor = getCardColor(noteIndex)
    val borderColor = getCardBorderColor(noteIndex)


    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 10.dp)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = backColor),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Text(
                    text = note.DSC_TITLE,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                // Botón menú
                Box(
                    modifier = Modifier.wrapContentSize(Alignment.TopEnd)
                ) {
                    IconButton(
                        onClick = { expanded = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Más opciones",
                            tint = Color.Black.copy(alpha = 0.6f)
                        )
                    }

                    // Menú desplegable
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.White, RoundedCornerShape(8.dp))
                    ) {
                        DropdownMenuItem(
                            text = { Text("Editar nota", color = Color.Black) },
                            onClick = {
                                expanded = false
                                onEdit(note)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Eliminar nota", color = Color.Black) },
                            onClick = {
                                expanded = false
                                onDelete()
                            }
                        )
                    }
                }
            }


            Text(
                text = note.Course?.DSC_NAME ?: "Sin curso",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))


            Text(
                text = parseDateString(note.DATE_NOTE),
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = 0.8f),
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = note.DSC_COMMENT,
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = 0.7f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )
        }
    }
}


private fun parseDateString(dateString: String): String {
    try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d 'de' MMMM, yyyy", Locale("es", "ES"))
        val date = inputFormat.parse(dateString)
        return if (date != null) outputFormat.format(date) else dateString
    } catch (e: Exception) {
        return dateString
    }
}
