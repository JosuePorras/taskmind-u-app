// AppScaffold.kt
package com.moviles.taskmind.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
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
                    NavItem("Evento", Icons.Default.Add),
                    NavItem("Evaluaciones", Icons.Default.DateRange),
                    NavItem("Notas de clase", Icons.Default.Check),
                    NavItem("Cursos", Icons.Default.Info),
                )
                ButtonDialog(
                    navItems = navItemListDialog,
                    onItemSelected = { index ->
                        when(index) {
                            2 -> onNotesSelected()
                            3 -> onCoursesSelected()
                        }
                        onDismissDialog()
                    },
                    onDismiss = onDismissDialog
                )
            }
        }
    }
}

