package com.moviles.taskmind.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviles.taskmind.models.Note


@Composable
fun NoteCard(note: Note) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = note.DSC_TITLE,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Estudiante: ${note.User.DSC_FIRST_NAME} ${note.User.DSC_LAST_NAME_ONE}")
            Text(text = "Carrera: ${note.User.DSC_CAREER}")
            Text(text = "Curso: ${note.Course.DSC_NAME}")
            Text(text = "Fecha: ${note.DATE_NOTE.take(10)}")

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Comentario: ${note.DSC_COMMENT}")
        }
    }
}

