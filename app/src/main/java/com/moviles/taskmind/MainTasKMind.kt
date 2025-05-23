package com.moviles.taskmind


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.moviles.taskmind.components.BottomBar
import com.moviles.taskmind.models.NavItem
import com.moviles.taskmind.components.AppScaffold
import com.moviles.taskmind.components.NoteClassForm
import com.moviles.taskmind.components.Header
import com.moviles.taskmind.pages.CalendarPage
import com.moviles.taskmind.pages.CoursePage
import com.moviles.taskmind.pages.HomePage
import com.moviles.taskmind.pages.NotesClassPage
import com.moviles.taskmind.pages.UserPage
import com.moviles.taskmind.viewmodel.CourseViewModel
import com.moviles.taskmind.viewmodel.UserSessionViewModel
import com.moviles.taskmind.viewmodel.note.NoteViewModel


@Composable
fun MainScreen(userSessionViewModel: UserSessionViewModel) {
    val navItemList = listOf(
        NavItem("Inicio", Icons.Default.Home),
        NavItem("Calendario", Icons.Default.DateRange),
        NavItem("Add", Icons.Default.Add),
        NavItem("Cursos", Icons.Default.Face),
        NavItem("Perfil", Icons.Default.Person),
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
            4 -> UserPage()
        }
    }

}
