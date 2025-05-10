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
import com.moviles.taskmind.utils.darkenColorHex
import com.moviles.taskmind.utils.parseColorString

@Composable
fun NoteCard(
    title: String,
    course: String,
    date: String,
    comment: String,
    colorMain: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val isCompactScreen = screenWidth < 600.dp

    val resolvedColor = darkenColorHex(colorMain, 0.6f)
    val backColor = parseColorString(colorMain)

    // Estado para controlar la visibilidad del menú
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .then(
                if(isCompactScreen){
                    Modifier.fillMaxWidth()
                }else{
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
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Fila para el título y el menú
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f).padding(16.dp)) {
                    Text(
                        text = title,
                        fontSize = if (isCompactScreen) 20.sp else 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = course,
                        fontSize = if (isCompactScreen) 14.sp else 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text= "Fecha: $date",
                        fontSize = if (isCompactScreen) 14.sp else 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                    // Añade esto después de la fecha en NoteCard.kt
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = comment,
                        fontSize = if (isCompactScreen) 14.sp else 15.sp,
                        color = Color.Black.copy(alpha = 0.8f),
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Botón para abrir el menú
                Box(
                    modifier = Modifier
                        .wrapContentSize(Alignment.TopEnd)
                ) {
                    IconButton(
                        onClick = { expanded = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Más opciones"
                        )
                    }

                    // Menú desplegable
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Editar nota") },
                            onClick = {
                                expanded = false
                                onEdit()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Eliminar nota") },
                            onClick = {
                                expanded = false
                                onDelete()
                            }
                        )
                    }
                }
            }

        }
    }
}