package com.moviles.taskmind.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun Header(
    title: String,
    subtitle: String? = null,
    buttonTitle: String? = null,
    profileData: ProfileData? = null,
    action: (() -> Unit)? = null
) {
    var searchText by remember { mutableStateOf("") }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2BD4BD))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            if (profileData != null) {

                Column {
                    Text(
                        text = "TASKMIND",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF93C5FD))
                        ) {
                            Text(
                                text = profileData.initials,
                                color = Color(0xFF367CF4),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "Bienvenido",
                                color = Color.White,
                                fontSize = 16.sp
                            )

                            Text(
                                text = profileData.fullName,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            LiveDateTimeText()
                        }
                    }
                }
            }
            else {
                Column {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color.White
                    )

                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
            }

            if (action != null) {
                IconButton(
                    onClick = action,
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color(0xFFDCFCE7), shape = CircleShape)
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = buttonTitle ?: "Agregar",
                        tint = Color(0xFF2BD4BD)
                    )
                }
            }
        }


    }
}


data class ProfileData(
    val firstName: String,
    val lastName: String
) {
    val fullName: String
        get() = "$firstName $lastName"

    val initials: String
        get() = "${firstName.firstOrNull() ?: ""}${lastName.firstOrNull() ?: ""}".uppercase()
}


fun getFormattedDate(): String {
    val locale = Locale("es", "ES")
    val date = Date()
    val formatter = SimpleDateFormat("EEEE, d 'de' MMMM, h:mm a", locale)


    formatter.timeZone = TimeZone.getTimeZone("America/Costa_Rica")

    return formatter.format(date)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
        .replace("AM", "a. m.")
        .replace("PM", "p. m.")
}


@Composable
fun LiveDateTimeText() {
    var dateTime by remember { mutableStateOf(getFormattedDate()) }


    LaunchedEffect(Unit) {
        while (true) {
            dateTime = getFormattedDate()
            delay(1000)
        }
    }

    Text(
        text = dateTime,
        fontSize = 16.sp,
        color = Color.White
    )
}