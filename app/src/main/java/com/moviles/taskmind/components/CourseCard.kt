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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
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
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val isCompactScreen = screenWidth < 600.dp

    val resolvedColor = parseColorString(progressColor)
    val backColor = parseColorString(colorMain)

    Card(
        modifier = Modifier
            .then(
                if(isCompactScreen){
                    Modifier.fillMaxWidth()
                }else{
                    Modifier.widthIn(max = 500.dp)
                }
            )
            .padding(if (isCompactScreen) 14.dp else 16.dp)
            .border(
                width = 1.dp,
                color = resolvedColor,
                shape = RoundedCornerShape(16.dp)
            )
            .heightIn(min = if (isCompactScreen) 300.dp else 350.dp),
                colors = CardDefaults.cardColors(containerColor = backColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                       ,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        CourseCardHeader(title, resolvedColor, professor, isCompactScreen)

                        Spacer(modifier = Modifier.height(if (isCompactScreen) 8.dp else 12.dp))

                        CourseCardProgressSection(progressBar, resolvedColor, isCompactScreen)

                        Spacer(modifier = Modifier.height(if (isCompactScreen) 24.dp else 40.dp))

                        ResponsiveIconButtons(isCompactScreen)

                        Spacer(modifier = Modifier.height(if (isCompactScreen) 8.dp else 16.dp))
                    }

                    CourseCardFooter(event = event, borderColor = resolvedColor,isCompactScreen)
                }
            }
}

@Composable
private fun ResponsiveIconButtons(isCompactScreen: Boolean) {
    val iconSize = if (isCompactScreen) 30.dp else 32.dp
    val fontSize = if (isCompactScreen) 13.sp else 15.sp

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButtonWithLabel(
            icon = Icons.Default.DateRange,
            label = "Eventos",
            onClick = {},
            iconSize = iconSize,
            fontSize = fontSize
        )
        IconButtonWithLabel(
            icon = Icons.Default.AccountBox,
            label = "Evaluaciones",
            onClick = {},
            iconSize = iconSize,
            fontSize = fontSize
        )
        IconButtonWithLabel(
            icon = Icons.Default.Person,
            label = "Profesor",
            onClick = { Log.d("hola", "si llego") },
            iconSize = iconSize,
            fontSize = fontSize
        )
    }
}

@Composable
fun IconButtonWithLabel(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    iconSize: Dp,
    fontSize: TextUnit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.Black,
            modifier = Modifier.size(iconSize))

        Text(
            text = label,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CourseCardHeader(
    title: String,
    progressColor: Color,
    professor: String,
    isCompactScreen: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = if (isCompactScreen) 20.sp else 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = professor,
                    fontSize = if (isCompactScreen) 15.sp else 17.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }

            IconButton(
                onClick = { /* acción */ },
                modifier = Modifier.size(if (isCompactScreen) 36.dp else 48.dp)
            ) {
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
fun CourseCardProgressSection(
    progressBar: Int,
    progressColor: Color,
    isCompactScreen: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Progreso del Curso",
            fontSize = if (isCompactScreen) 15.sp else 18.sp,
            color = Color.Black
        )
        Text(
            text = "${progressBar}%",
            fontSize = if (isCompactScreen) 15.sp else 18.sp,
            color = Color.Black
        )
    }
    Spacer(modifier = Modifier.height(if (isCompactScreen) 12.dp else 16.dp))
    LinearProgressIndicator(
        progress = { progressBar / 100f },
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isCompactScreen) 14.dp else 18.dp)
            .padding(top = 4.dp),
        color = progressColor,
        trackColor = Color.LightGray,
    )
}

@Composable
fun CourseCardFooter(event: String, borderColor: Color, isCompactScreen: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isCompactScreen) 90.dp else 100.dp)
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
            .padding(horizontal = if (isCompactScreen) 16.dp else 24.dp),
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
                    fontSize = if (isCompactScreen) 16.sp else 18.sp,
                    color = Color.Black
                )
                Text(
                    text = event,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (isCompactScreen) 18.sp else 20.sp
                )
            }
            Text(
                text = "Hoy, 8:00 Am",
                fontSize = if (isCompactScreen) 14.sp else 16.sp,
                color = Color.Black
            )
        }
    }
}

