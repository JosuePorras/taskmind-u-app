package com.moviles.taskmind.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moviles.taskmind.components.CourseCard

@Composable
fun CoursePage(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {

            /*falta crear un Header con el formulario (Aaron)*/


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                CourseCard(
                    title = "Diseño y programación de plataformas móviles",
                    professor = "Prof. Rachel Bolívar Morales",
                    progressBar = (0.15f * 100).toInt(),
                    event = "Examen Parcial",
                    progressColor = "#9C27B0",
                    colorMain = "#DBEAFE"
                )

                CourseCard(
                    title = "Métodos de Investigación Científica en Informática",
                    professor = "Prof. Willy Pineda Lizano",
                    progressBar = 20,
                    event = "Avance Proyecto",
                    progressColor = "#FF9800",
                    colorMain = "#FEF3C7"
                )

                CourseCard(
                    title = "Ingenieria en Sistemas III",
                    professor = "Prof. Michael Barquero",
                    progressBar = (0.10f * 100).toInt(),
                    event = "Sprint-4",
                    progressColor = "#10B981",
                    colorMain = "#DCFCE7"
                )

                CourseCard(
                    title = "Algebra Lineal",
                    professor = "Prof. Julio Marin",
                    progressBar = 75,
                    event = "Examen Parcial II",
                    progressColor = "#EF4444",
                    colorMain = "#FEE2E2"
                )
            }
        }
    }
}





