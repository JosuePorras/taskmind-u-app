package com.moviles.taskmind.components.course

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.moviles.taskmind.components.RoundedBlueOutlinedTextField
import com.moviles.taskmind.models.Professor

@Composable
fun ProfessorInfoTab(
    useExisting: Boolean?,
    onUseExistingChange: (Boolean) -> Unit,
    professors: List<Professor>,
    selectedProfessorId: Int?,
    onProfessorSelected: (Int) -> Unit,
    professorName: String,
    onProfessorNameChange: (String) -> Unit,
    professorFirstName: String,
    onProfessorFirstNameChange: (String) -> Unit,
    professorLastName: String,
    onProfessorLastNameChange: (String) -> Unit,
    professorEmail: String,
    onProfessorEmailChange: (String) -> Unit,
    professorPhone: String,
    onProfessorPhoneChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Opciones mejoradas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            OptionCard(
                title = "Seleccionar",
                icon = Icons.Default.Person,
                isSelected = useExisting == true,
                onClick = { onUseExistingChange(true) },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )

            OptionCard(
                title = "Crear nuevo",
                icon = Icons.Default.Person,
                isSelected = useExisting == false,
                onClick = { onUseExistingChange(false) },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }

        // Contenido según la opción seleccionada
        AnimatedVisibility(
            visible = useExisting == true,
            enter = fadeIn(animationSpec = tween(300)) + expandVertically(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(animationSpec = tween(300))
        ) {
            DropdownMenuWrapper(
                items = professors,
                selectedId = selectedProfessorId,
                onItemSelected = onProfessorSelected
            )
        }

        AnimatedVisibility(
            visible = useExisting == false,
            enter = fadeIn(animationSpec = tween(300)) + expandVertically(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(animationSpec = tween(300))
        ) {
            Column {
                val fields = listOf(
                    Triple("Nombre del Profesor*", professorName to onProfessorNameChange, "Ej: Juan"),
                    Triple("Primer Apellido*", professorFirstName to onProfessorFirstNameChange, "Ej: Pérez"),
                    Triple("Segundo Apellido*", professorLastName to onProfessorLastNameChange, "Ej: López"),
                    Triple("Correo Electrónico*", professorEmail to onProfessorEmailChange, "Ej: juan.perez@una.ac.cr"),
                    Triple("Teléfono*", professorPhone to onProfessorPhoneChange, "Ej: 00000000")
                )

                fields.forEachIndexed { index, (label, pair, hint) ->
                    if (index > 0) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Text(
                        label,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF000000),
                        modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
                    )

                    RoundedBlueOutlinedTextField(
                        value = pair.first,
                        onValueChange = pair.second,
                        labelText = hint,
                    )
                }
            }
        }
    }
}

@Composable
fun OptionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) Color(0xFF2BD4BD) else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFE0F7FA) else Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Color(0xFF2BD4BD) else Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color(0xFF2BD4BD) else Color.Gray
            )
        }
    }
}

@Composable
fun DropdownMenuWrapper(
    items: List<Professor>,
    selectedId: Int?,
    onItemSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedProfessor = items.find { it.id == selectedId }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            "Selecciona un profesor existente",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF000000),
            modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(50.dp),
                )
                .clip(RoundedCornerShape(12.dp))
                .clickable { expanded = true }
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedProfessor?.let { "${it.firstName} ${it.lastNameOne}" } ?: "Seleccionar profesor",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .background(Color.White)
            ) {
                items.forEach { professor ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                "${professor.firstName} ${professor.lastNameOne}",
                                fontWeight = if (professor.id == selectedId) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        onClick = {
                            onItemSelected(professor.id)
                            expanded = false
                        },
                        modifier = Modifier.background(
                            if (professor.id == selectedId) Color(0xFFE0F7FA) else Color.White
                        )
                    )
                }
            }
        }
    }
}