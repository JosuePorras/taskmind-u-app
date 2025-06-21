// AppScaffold.kt
package com.moviles.taskmind.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.BookOnline
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moviles.taskmind.models.NavItem

@Composable
fun AppScaffold(
    bottomBar: @Composable () -> Unit,
    showDialog: Boolean,
    onDismissDialog: () -> Unit,
    onNotesSelected: () -> Unit,
    onCoursesSelected: () -> Unit,
    onEvaluationSelected: () -> Unit,
    content: @Composable () -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = bottomBar
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()

            if (showDialog) {
                val navItemListDialog = listOf(
                    NavItem("Evaluaciones", Icons.Filled.BookOnline),
                    NavItem("Notas de clase", Icons.Filled.NoteAlt),
                    NavItem("Cursos", Icons.Filled.School)
                )
                ButtonDialog(
                    navItems = navItemListDialog,
                    onItemSelected = { index ->
                        when(index) {
                            0 -> onEvaluationSelected()
                            1 -> onNotesSelected()
                            2 -> onCoursesSelected()
                        }
                        onDismissDialog()
                    },
                    onDismiss = onDismissDialog
                )
            }
        }
    }
}

