package com.moviles.taskmind.components


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviles.taskmind.utils.parseColorString


@Composable
fun CourseCard(
    title: String,
    professor: String,
    progressBar: Int,
    event: String,
    progressColor: String,
    colorMain: String
) {
    val resolvedColor = parseColorString(progressColor)
    val backColor = parseColorString(colorMain)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .border(
                width = 1.dp,
                color = resolvedColor,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                CourseCardHeader(title, resolvedColor, professor)

                Spacer(modifier = Modifier.height(12.dp))

                CourseCardProgressSection(progressBar, resolvedColor)

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButtonWithLabel(Icons.Default.DateRange, "Eventos", onClick = {})
                    IconButtonWithLabel(Icons.Default.AccountBox, "Evaluaciones", onClick = {})
                    IconButtonWithLabel(Icons.Default.Person, "Profesor", onClick = { Log.d("hola", "si llego") })
                }

                Spacer(modifier = Modifier.height(16.dp))
            }


            CourseCardFooter(event = event, borderColor = resolvedColor)
        }
    }


}


@Composable
fun IconButtonWithLabel(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        verticalAlignment  = Alignment.CenterVertically,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.Black,
            modifier = Modifier.size(30.dp))

                    Text(
                    text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun CourseCardHeader(title: String, progressColor: Color, professor: String) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = professor,
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }

            IconButton(onClick = { /* acción */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Opciones",
                    tint = progressColor
                )
            }
        }
    }
}



@Composable
fun CourseCardProgressSection(progressBar: Int, progressColor: Color) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Progreso del Curso", fontSize = 15.sp, color = Color.Black)
        Text(
            text = "${progressBar}%",
            fontSize = 15.sp,
            color = Color.Black
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
    LinearProgressIndicator(
        progress = { progressBar / 100f },
        modifier = Modifier
            .fillMaxWidth()
            .height(14.dp)
            .padding(top = 4.dp),
        color = progressColor,
        trackColor = Color.LightGray,
    )
}


@Composable
fun CourseCardFooter(event: String, borderColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column  {
                Text(
                    text = "Próximo Evento",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = event,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Text(
                text = "Hoy, 8:00 Am",
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}

