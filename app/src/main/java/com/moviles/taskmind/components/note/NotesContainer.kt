package com.moviles.taskmind.components.note

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moviles.taskmind.models.NoteDto


@Composable
fun NotesContainer(
    notes: List<NoteDto>,
    onEdit: (NoteDto) -> Unit,
    onDelete: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = 2.dp,
                color = Color.Black.copy(alpha = 0.15f),
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(8.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            itemsIndexed(notes) { index, note ->
                NoteCard(
                    colorMain = "",
                    note = note,
                    noteIndex = index,
                    onEdit = onEdit,
                    onDelete = { onDelete(note.ID_STUDENT_NOTE.toString()) }
                )
            }
        }
    }
}


@Composable
fun NotesContainerColumn(
    notes: List<NoteDto>,
    onEdit: (NoteDto) -> Unit,
    onDelete: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = 2.dp,
                color = Color.Black.copy(alpha = 0.15f),
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            notes.forEachIndexed { index, note ->
                NoteCard(
                    colorMain = "",
                    note = note,
                    noteIndex = index,
                    onEdit = onEdit,
                    onDelete = { onDelete(note.ID_STUDENT_NOTE.toString()) }
                )
            }
        }
    }
}