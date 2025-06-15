package com.moviles.taskmind.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviles.taskmind.models.Note
import com.moviles.taskmind.models.NoteDto
import com.moviles.taskmind.utils.darkenColorHex
import com.moviles.taskmind.utils.parseColorString
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Colores exactos del prototipo
private val cardColors = listOf(
    Color(0xFFE1C4FF), // Morado pastel
    Color(0xFFFFF2CC), // Amarillo pastel
    Color(0xFFB8E6FF), // Azul pastel
    Color(0xFFB8FFB8), // Verde pastel
    Color(0xFFFFB8B8)  // Rosa pastel
)

// Función para obtener el color basado en el índice
fun getCardColor(index: Int): Color {
    return cardColors[index % cardColors.size]
}

// Función para obtener el color del borde (más oscuro)
fun getCardBorderColor(index: Int): Color {
    val baseColor = getCardColor(index)
    return when (index % cardColors.size) {
        0 -> Color(0xFFB794E6) // Morado más oscuro
        1 -> Color(0xFFE6D499) // Amarillo más oscuro
        2 -> Color(0xFF85CCFF) // Azul más oscuro
        3 -> Color(0xFF85E685) // Verde más oscuro
        4 -> Color(0xFFE68585)  // Rosa más oscuro
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
    // Obtener el color basado en el índice
    val backColor = getCardColor(noteIndex)
    val borderColor = getCardBorderColor(noteIndex)

    // Estado para controlar la visibilidad del menú
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp) // Menos padding horizontal ya que el contenedor padre tendrá el suyo
            .border(
                width = 2.dp, // Borde más grueso como en el prototipo
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
            // Header con título y menú
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Título - más prominente
                Text(
                    text = note.DSC_TITLE,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1, // Solo una línea como en el prototipo
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

            // Información del curso - sin spacer extra
            Text(
                text = note.Course?.DSC_NAME ?: "Sin curso",
                fontSize = 16.sp, // Más grande
                color = Color.Black,
                fontWeight = FontWeight.SemiBold, // Más peso
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp)) // Espaciado mínimo

            // Fecha
            Text(
                text = parseDateString(note.DATE_NOTE),
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = 0.8f),
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Contenido/Descripción - más compacto
            Text(
                text = note.DSC_COMMENT,
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = 0.7f),
                maxLines = 2, // Solo 2 líneas como en el prototipo
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )
        }
    }
}

// Función para parsear fecha en formato español
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

@Composable
fun NotesContainer(
    notes: List<NoteDto>,
    onEdit: (NoteDto) -> Unit,
    onDelete: (String) -> Unit
) {
    // Contenedor grande con borde que envuelve todas las notas
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) // Margen exterior
            .border(
                width = 2.dp,
                color = Color.Black.copy(alpha = 0.15f), // Borde gris oscuro
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(8.dp) // Padding interno del contenedor
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(0.dp) // Sin espacio extra entre cards
        ) {
            itemsIndexed(notes) { index, note ->
                NoteCard(
                    colorMain = "", // Ya no se usa
                    note = note,
                    noteIndex = index,
                    onEdit = onEdit,
                    onDelete = { onDelete(note.ID_STUDENT_NOTE.toString()) }
                )
            }
        }
    }
}

// Si no usas LazyColumn, aquí una versión con Column:
@Composable
fun NotesContainerColumn(
    notes: List<NoteDto>,
    onEdit: (NoteDto) -> Unit,
    onDelete: (String) -> Unit
) {
    // Contenedor grande con borde que envuelve todas las notas
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) // Margen exterior
            .border(
                width = 2.dp,
                color = Color.Black.copy(alpha = 0.15f), // Borde gris oscuro
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(8.dp) // Padding interno del contenedor
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            notes.forEachIndexed { index, note ->
                NoteCard(
                    colorMain = "", // Ya no se usa
                    note = note,
                    noteIndex = index,
                    onEdit = onEdit,
                    onDelete = { onDelete(note.ID_STUDENT_NOTE.toString()) }
                )
            }
        }
    }
}