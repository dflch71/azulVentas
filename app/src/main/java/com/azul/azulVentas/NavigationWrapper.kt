package com.azul.azulVentas

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.azul.azulVentas.ui.presentation.clientes.viewmodel.ClientesViewModel
import com.azul.azulVentas.ui.presentation.home.HomeScreen
import com.azul.azulVentas.ui.presentation.initial.InitialScreen
import com.azul.azulVentas.ui.presentation.login.view.LoginScreen
import com.azul.azulVentas.ui.presentation.login.viewmodel.AuthViewModel
import com.azul.azulVentas.ui.presentation.registration.RegistrationScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    auth: FirebaseAuth,
    clientesViewModel: ClientesViewModel,
    authViewModel: AuthViewModel
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
            //LoginScreen(auth) {
            LoginScreen(
                onLoginClicked = { navHostController.navigate("home") },
                onRegistrationClicked = { navHostController.navigate("singUp") },
                viewModel = authViewModel,
                onLoginSuccess = {}
            )
               navHostController.navigate("home")

        }

        composable("singup") {
            RegistrationScreen(
                authViewModel = authViewModel,
                onRegisterClicked = { /*TODO*/ },
                onLoginClicked = { /*TODO*/ }) {
            }
        }

        composable("home") {
            HomeScreen(
                clientesViewModel,
                authViewModel,
                onLogoutSuccess = { navHostController.navigate("initial") }
            )
        }

    }
}