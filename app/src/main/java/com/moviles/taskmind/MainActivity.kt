package com.moviles.taskmind

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moviles.taskmind.pages.RegistrationUser
import com.moviles.taskmind.viewmodel.UserSessionViewModel

class MainActivity : ComponentActivity() {
    private val userSessionViewModel: UserSessionViewModel by viewModels()

    // Launcher para solicitar el permiso de notificaciones
    private lateinit var notificationPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Registrar el launcher de permiso
        notificationPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                Log.d("PERMISOS", "Permiso de notificaciones CONCEDIDO")
            } else {
                Log.w("PERMISOS", "Permiso de notificaciones DENEGADO")
            }
        }

        // Pedir permiso solo si es Android 13 o superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permiso = Manifest.permission.POST_NOTIFICATIONS
            if (ContextCompat.checkSelfPermission(this, permiso) != PackageManager.PERMISSION_GRANTED) {
                notificationPermissionLauncher.launch(permiso)
            } else {
                Log.d("PERMISOS", "Permiso ya concedido")
            }
        }

        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    LoginScreen(
                        navController,
                        userSessionViewModel = userSessionViewModel
                    )
                }
                composable("main") {
                    MainScreen(userSessionViewModel)
                }
                composable("userForm") {
                    RegistrationUser(
                        onSave = { navController.popBackStack() },
                        onCancel = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
