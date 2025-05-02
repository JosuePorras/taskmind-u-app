package com.moviles.taskmind.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.moviles.taskmind.components.CourseCard

@Composable
fun CoursePage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().background(Color(0xFFA2E0A4)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CourseCard(
            title = "Diseño y programación de plataformas móviles",
            professor = "Prof. Rachel Bolívar Morales",
            progressBar = 0.75f.times(100).toInt(),
            event = "Examen Parcial ",
             progressColor ="#9C27B0",
            colorMain = "#DBEAFE"
        )
//0xFF9C27B0
        CourseCard(
            title = "Métodos de Investigación Científica en Informática",
            professor = "Prof. Willy Pineda Lizano",
            progressBar = 75,
            event = "Avance Proyecto",
            progressColor ="#FF9800",
            colorMain = "#DBEAFE"
        )
    }//0xFFFF9800
}