package com.moviles.taskmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moviles.taskmind.pages.RegistrationUser
import com.moviles.taskmind.viewmodel.UserSessionViewModel


class MainActivity : ComponentActivity() {
    private val userSessionViewModel: UserSessionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    LoginScreen(
                        navController, userSessionViewModel = userSessionViewModel
                    )
                }
                composable("main") {
                    MainScreen(userSessionViewModel)
                }
                composable("userForm") {
                    RegistrationUser(
                        onSave = {navController.popBackStack()},
                        onCancel = {navController.popBackStack()}
                    )
                }

            }
        }
    }
}