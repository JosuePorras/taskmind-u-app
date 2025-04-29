package com.moviles.taskmind


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import com.moviles.taskmind.models.NavItem
import com.moviles.taskmind.pages.CalendarPage
import com.moviles.taskmind.pages.CoursePage
import com.moviles.taskmind.pages.HomePage
import com.moviles.taskmind.pages.UserPage


@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val navItemList = listOf(
        NavItem("Inicio",Icons.Default.Home),
        NavItem("Calendario",Icons.Default.DateRange),
        NavItem("Add",Icons.Default.Add),
        NavItem("Cursos",Icons.Default.Face),
        NavItem("Perfil",Icons.Default.Person),
    )
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold (modifier= Modifier.fillMaxSize().size(30.dp),
    bottomBar = {

           BottomBar(
               navItems = navItemList,
               selectedIndex = selectedIndex,
               onItemSelected = { index -> selectedIndex = index }
           )

    }

    ) {  innerPadding ->
    ContentScreen(modifier=Modifier.padding(innerPadding),selectedIndex)
        
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier,selectedIndex: Int) {
    when(selectedIndex){
        0-> HomePage()
        1-> CalendarPage()
        3-> CoursePage()
        4-> UserPage()
    }
}



@Composable
fun BottomBar(navItems: List<NavItem>, onItemSelected: (Int) -> Unit, selectedIndex: Int) {
    NavigationBar  {
        navItems.forEachIndexed { index, item ->
            if (item.label == "Add") {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2BD4BD))
                        .clickable { onItemSelected(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint =  Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            } else {
                NavigationBarItem(
                    selected = selectedIndex == index,
                    onClick = { onItemSelected(index) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (selectedIndex == index) Color(0xFF2BD4BD) else Color.Gray
                        )
                    },
                    label = { Text(item.label) }
                )
            }
        }
    }
}
