package com.moviles.taskmind


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.moviles.taskmind.components.BottomBar
import com.moviles.taskmind.models.NavItem
import com.moviles.taskmind.components.AppScaffold
import com.moviles.taskmind.pages.CalendarPage
import com.moviles.taskmind.pages.CoursePage
import com.moviles.taskmind.pages.HomePage
import com.moviles.taskmind.pages.NotesClassPage
import com.moviles.taskmind.pages.UserPage
import com.moviles.taskmind.viewmodel.UserSessionViewModel


@Composable
fun MainScreen(userSessionViewModel: UserSessionViewModel) {
    val navItemList = listOf(
        NavItem("Inicio", Icons.Filled.Home),
        NavItem("Agenda", Icons.Filled.CalendarToday),
        NavItem("Add", Icons.Filled.AddCircle),
        NavItem("Cursos", Icons.Filled.School),
        NavItem("Perfil", Icons.Filled.AccountCircle)
    )

    var selectedIndex by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    var showNoteClassForm by remember { mutableStateOf(false) }




    AppScaffold(
        bottomBar = {
            BottomBar(
                navItems = navItemList,
                selectedIndex = selectedIndex,
                onItemSelected = { index -> selectedIndex = index },
                onAddClick = { showDialog = true }
            )
        },
        showDialog = showDialog,
        onDismissDialog = { showDialog = false },
        onNotesSelected = { selectedIndex = 2
                // When "Notas de clase" is selected from the dialog
            showNoteClassForm = true
            },
        onCoursesSelected = { selectedIndex = 3 }
    ) {
        when (selectedIndex) {
            0 -> HomePage()
            1 -> CalendarPage()
            2 -> NotesClassPage(userSessionViewModel = userSessionViewModel)
            3 -> CoursePage(userSessionViewModel = userSessionViewModel)
            4 -> UserPage(userSessionViewModel = userSessionViewModel)
        }
    }

}
