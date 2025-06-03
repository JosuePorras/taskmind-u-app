package com.moviles.taskmind

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.moviles.taskmind.components.RoundedBlueOutlinedTextField
import com.moviles.taskmind.viewmodel.LoginViewModel
import com.moviles.taskmind.viewmodel.UserSessionViewModel


@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    userSessionViewModel: UserSessionViewModel
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val uiState by loginViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.userResponse) {
        uiState.userResponse?.let { user ->
            userSessionViewModel.setUserId(user.ID_USER.toString())
            navController.navigate("main") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

        Column (
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painterResource(R.drawable.tasklogo), contentDescription = "TaskMind Logo",
                modifier = Modifier.size(200.dp))

            Text(text = "Organiza tu mente, conquista tus metas.", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Por favor, inicia sesion")

            Spacer(modifier = Modifier.height(16.dp))

            RoundedBlueOutlinedTextField(
                value = email,
                onValueChange = { email = it },
                labelText = "Correo o Identificación"
            )

            Spacer(modifier = Modifier.height(16.dp))

            RoundedBlueOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                labelText = "Contraseña",
                isPassword = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    Log.i("credenciales", "correo: $email, contra: $password")
                    loginViewModel.login(email, password)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Ingresar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "No tienes cuenta?",modifier = Modifier.clickable {
                navController.navigate("userForm") {
                    launchSingleTop = true
                    //popUpTo("login") { inclusive = true }
                }
            },fontSize = 16.sp, fontWeight = FontWeight.SemiBold)


            uiState.error?.let { errorMessage ->
                Text(text = errorMessage, color = Color.Red, fontWeight = FontWeight.Bold)
            }

            uiState.userResponse?.let { user ->
                Text(text = "¡Bienvenido, ${user.DSC_FIRST_NAME}!", fontWeight = FontWeight.Bold)
            }

        }

}





