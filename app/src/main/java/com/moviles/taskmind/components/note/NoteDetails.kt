package com.moviles.taskmind.components.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.moviles.taskmind.models.NoteDto
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun NoteDetails(
    note: NoteDto,
    isVisible: Boolean,
    onDismiss: () -> Unit
) {
    if (isVisible) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Header del modal con botón cerrar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "Detalles de la Nota",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = onDismiss
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar",
                                tint = Color.Black.copy(alpha = 0.6f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Título de la nota
                    Text(
                        text = "Título:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black.copy(alpha = 0.8f)
                    )
                    Text(
                        text = note.DSC_TITLE,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Curso
                    Text(
                        text = "Curso:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black.copy(alpha = 0.8f)
                    )
                    Text(
                        text = note.Course?.DSC_NAME ?: "Sin curso",
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Fecha
                    Text(
                        text = "Fecha:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black.copy(alpha = 0.8f)
                    )
                    Text(
                        text = parseDateStringForModal(note.DATE_NOTE),
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Contenido completo
                    Text(
                        text = "Contenido:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black.copy(alpha = 0.8f)
                    )
                    Text(
                        text = note.DSC_COMMENT,
                        fontSize = 16.sp,
                        color = Color.Black,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

private fun parseDateStringForModal(dateString: String): String {
    try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d 'de' MMMM, yyyy", Locale("es", "ES"))
        val date = inputFormat.parse(dateString)
        return if (date != null) outputFormat.format(date) else dateString
    } catch (e: Exception) {
        return dateString
    }
}