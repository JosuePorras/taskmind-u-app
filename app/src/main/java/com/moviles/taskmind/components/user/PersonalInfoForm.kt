package com.moviles.taskmind.components.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviles.taskmind.components.RoundedBlueOutlinedTextField

@Composable
fun PersonalInfoForm(
    nombre: String,
    apellido: String,
    correo: String,
    carrera: String,
    onNombreChange: (String) -> Unit,
    onApellidoChange: (String) -> Unit,
    onCorreoChange: (String) -> Unit,
    onCarreraChange: (String) -> Unit,
    onGuardarClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDBEAFE))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Informacion de Perfil",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    RoundedBlueOutlinedTextField(
                        value = nombre,
                        onValueChange = onNombreChange,
                        labelText = "Nombre",
                        modifier = Modifier.weight(1f)
                    )
                    RoundedBlueOutlinedTextField(
                        value = apellido,
                        onValueChange = onApellidoChange,
                        labelText = "Apellido",
                        modifier = Modifier.weight(1f)
                    )
                }

                RoundedBlueOutlinedTextField(
                    value = correo,
                    onValueChange = onCorreoChange,
                    labelText = "Correo Electrónico",
                    keyboardType = KeyboardType.Email
                )

                RoundedBlueOutlinedTextField(
                    value = carrera,
                    onValueChange = onCarreraChange,
                    labelText = "Carrera"
                )

                Button(
                    onClick = onGuardarClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDCFCE7), // verde claro
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 8.dp)
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}
