package com.moviles.taskmind

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.moviles.taskmind.components.toast.CustomToast
import com.moviles.taskmind.viewmodel.LoginViewModel
import com.moviles.taskmind.viewmodel.UserSessionViewModel
import com.moviles.taskmind.viewmodel.toast.ToastViewModel



@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    tviewModel: ToastViewModel = viewModel(),
    userSessionViewModel: UserSessionViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val uiState by loginViewModel.uiState.collectAsState()
    val toastState by tviewModel.toastState.collectAsState()

    // Efecto para mostrar Toast de error
    LaunchedEffect(uiState.error) {
        uiState.error?.let { errorMessage ->
            tviewModel.showToast(errorMessage, ToastViewModel.ToastType.ERROR)
            loginViewModel.clearError() // Asegúrate de limpiar el error después de mostrarlo
        }
    }

    // Efecto para navegar después de login exitoso
    LaunchedEffect(uiState.userResponse) {
        uiState.userResponse?.let { user ->
            tviewModel.showToast("¡Bienvenido, ${user.DSC_FIRST_NAME}!", ToastViewModel.ToastType.SUCCESS)
            userSessionViewModel.setUserId(user.ID_USER.toString())
            navController.navigate("main") {
                popUpTo("login") { inclusive = true }
            }
            loginViewModel.clearUserResponse() // Limpiar después de usar
        }
    }

    // Toast que se muestra sobre toda la pantalla
    if (toastState.show) {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomToast(
                message = toastState.message,
                toastType = toastState.type,
                onDismiss = { tviewModel.dismissToast() }
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(R.drawable.tasklogo),
            contentDescription = "TaskMind Logo",
            modifier = Modifier.size(200.dp)
        )

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

        Text(
            text = "No tienes cuenta?",
            modifier = Modifier.clickable {
                navController.navigate("userForm") {
                    launchSingleTop = true
                }
            },
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}





