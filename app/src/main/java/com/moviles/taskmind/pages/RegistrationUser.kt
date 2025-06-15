package com.moviles.taskmind.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviles.taskmind.R
import com.moviles.taskmind.components.RoundedBlueOutlinedTextField
import com.moviles.taskmind.components.toast.CustomToast
import com.moviles.taskmind.models.UserResponse
import com.moviles.taskmind.viewmodel.toast.ToastViewModel
import com.moviles.taskmind.viewmodel.user.UserViewModel


@Composable
fun RegistrationUser(
    onSave: () -> Unit,
    onCancel: () -> Unit,
    userViewModel: UserViewModel = viewModel(),
    tviewModel: ToastViewModel = viewModel()
) {
    val uiState by userViewModel.uiState.collectAsState()
    val toastState by tviewModel.toastState.collectAsState()
    // init states
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var identification by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var career by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var emailErrorMessage by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordMatchError by remember { mutableStateOf(false) }
    val blueColor = Color(0xFF0069BB)

    LaunchedEffect(uiState.error) {
        uiState.error?.let { errorMessage ->
            tviewModel.showToast(errorMessage, ToastViewModel.ToastType.ERROR)
            userViewModel.clearError()
        }
    }

    LaunchedEffect(uiState.userResponse) {
        uiState.userResponse?.let { user ->
            user.message.let {
                tviewModel.showToast(it, ToastViewModel.ToastType.SUCCESS)
            }
            userViewModel.clearUserResponse()
            onSave()
        }
    }

    LaunchedEffect(toastState.show) {
        if (toastState.show) {
            kotlinx.coroutines.delay(ToastViewModel.ToastDuration.SHORT.timeMillis)
            tviewModel.dismissToast()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 560.dp)
                .padding(24.dp)
                .align(Alignment.Center),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.tasklogo),
                        contentDescription = "TaskMind Logo",
                        modifier = Modifier
                            .size(120.dp)
                            .padding(bottom = 8.dp),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = "Crear cuenta",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0069BB))
                    Text(
                        text = "Complete todos los campos para registrarse",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                // name
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    RoundedBlueOutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        labelText = "Nombre",
                        leadingIcon = Icons.Default.Person,
                        modifier = Modifier.weight(1f),
                        cornerRadius = 12
                    )

                    RoundedBlueOutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        labelText = "Apellido",
                        leadingIcon = Icons.Default.Person,
                        modifier = Modifier.weight(1f),
                        cornerRadius = 12
                    )
                }

                // inputs
                RoundedBlueOutlinedTextField(
                    value = identification,
                    onValueChange = { identification = it },
                    labelText = "Identificación",
                    leadingIcon = Icons.Default.AccountBox,
                    cornerRadius = 12
                )

                RoundedBlueOutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = if (it.isNotEmpty()) {
                            if (!isValidEmail(it)) {
                                emailErrorMessage = "Ingrese un correo válido (ej: usuario@dominio.com)"
                                true
                            } else {
                                false
                            }
                        } else {
                            false
                        }
                    },
                    labelText = "Correo Electrónico",
                    isError = emailError,
                    leadingIcon = Icons.Default.Email,
                    keyboardType = KeyboardType.Email,
                    cornerRadius = 12
                )

                RoundedBlueOutlinedTextField(
                    value = career,
                    onValueChange = { career = it },
                    labelText = "Carrera",
                    leadingIcon = Icons.Default.Home,
                    cornerRadius = 12
                )

                // password
                RoundedBlueOutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = it.length < 6 && it.isNotEmpty()
                        passwordMatchError = it != confirmPassword && confirmPassword.isNotEmpty()
                    },
                    labelText = "Contraseña",
                    isPassword = true,
                    leadingIcon = Icons.Default.Lock,
                    cornerRadius = 12
                )

                RoundedBlueOutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        passwordMatchError = it != password && it.isNotEmpty()
                    },
                    labelText = "Confirmar Contraseña",
                    isPassword = true,
                    leadingIcon = Icons.Default.Lock,
                    cornerRadius = 12
                )

                // error message
                Column(
                    modifier = Modifier.padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (emailError) {
                        ErrorText(text = emailErrorMessage)
                    }
                    if (passwordError) {
                        ErrorText(text = "La contraseña debe tener al menos 6 caracteres")
                    }
                    if (passwordMatchError) {
                        ErrorText(text = "Las contraseñas no coinciden")
                    }
                }

                // Bottom
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = onCancel,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, blueColor)
                    ) {
                        Text("Cancelar", color = blueColor)
                    }

                    Button(
                        onClick = {
                            if (!emailError && !passwordError && !passwordMatchError) {
                                val newUser=UserResponse(null, firstName,lastName,
                                    identification,email,password,confirmPassword,"",
                                    career,"")

                                userViewModel.addUser(newUser)
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = firstName.isNotEmpty() && lastName.isNotEmpty()
                                && identification.isNotEmpty() && email.isNotEmpty()
                                && password.isNotEmpty() && career.isNotEmpty()
                                && confirmPassword.isNotEmpty()
                                && !passwordError && !emailError && !passwordMatchError,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = blueColor,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Registrarse")
                    }
                }
            }
        }
    }
    if (toastState.show) {
        CustomToast(
            message = toastState.message,
            toastType = toastState.type,
            onDismiss = { tviewModel.dismissToast() }
        )
    }
}

@Composable
private fun ErrorText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier.padding(start = 8.dp)
    )
}


fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
    return emailRegex.matches(email)
}