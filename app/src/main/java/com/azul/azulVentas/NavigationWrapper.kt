package com.azul.azulVentas

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.azul.azulVentas.ui.presentation.initial.InitialScreen
import com.azul.azulVentas.ui.presentation.login.LoginScreen
import com.azul.azulVentas.ui.presentation.singup.SingUpScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    auth: FirebaseAuth
) {

    NavHost(
        navController = navHostController,
        startDestination = "initial"
    ) {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navHostController.navigate("login") },
                navigateToSingUp = { navHostController.navigate("singUp") }
            )
        }

        composable("login") {
            LoginScreen(auth) {
                navHostController.navigate("home")
            }
        }

        composable("singup") {
            SingUpScreen(auth)
        }
    }
}