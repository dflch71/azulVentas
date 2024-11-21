package com.azul.azulVentas.ui.presentation.container

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.azul.azulVentas.ui.presentation.welcome.view.WelcomeScreen
import com.azul.azulVentas.ui.presentation.clientes.viewmodel.ClientesViewModel
import com.azul.azulVentas.ui.presentation.empresa.view.EmpresaScreen
import com.azul.azulVentas.ui.presentation.empresa.viewmodel.EmpresaViewModel
import com.azul.azulVentas.ui.presentation.home.HomeScreen
import com.azul.azulVentas.ui.presentation.login.view.LoginScreen
import com.azul.azulVentas.ui.presentation.login.viewmodel.AuthViewModel
import com.azul.azulVentas.ui.presentation.network.viewmodel.NetworkViewModel
import com.azul.azulVentas.ui.presentation.otp.view.OTPScreen
import com.azul.azulVentas.ui.presentation.registration.view.RegistrationScreen
import com.azul.azulVentas.ui.presentation.registration.viewmodel.RegisterViewModel

@Composable
fun ScreenContainer(
    navHost: NavHostController,
    networkViewModel: NetworkViewModel,
    authViewModel: AuthViewModel,
    registerViewModel: RegisterViewModel,
    clientesViewModel: ClientesViewModel,
    empresaViewModel: EmpresaViewModel
) {
    //val navHost = rememberNavController()

    NavHost(
        navController = navHost,
        startDestination = NavGraph.Welcome.route
    ) {
        composable(NavGraph.Welcome.route) {

            WelcomeScreen(
                networkViewModel,
                authViewModel = authViewModel,

                onOpenLoginClicked = {
                    if (authViewModel.isLoggedIn()) {
                        navHost.navigate(NavGraph.Home.route)
                    } else {
                        navHost.navigate(NavGraph.Login.route)
                    }
                },

                onOpenOTPClicked = {
                    navHost.navigate(NavGraph.OTP.route)
                }
            )
        }

        composable(NavGraph.Login.route) {
            LoginScreen(
                onRegistrationClicked = {
                    navHost.navigate(NavGraph.Registration.route) // Navegar a la pantalla de Registro
                },

                authViewModel = authViewModel, // ViewModel inyectado
                onLoginSuccess = {
                    navHost.navigate(NavGraph.Home.route) // Si el login es exitoso, navegar al Home
                }
            )
        }

        composable(NavGraph.Registration.route) {
            RegistrationScreen(
                registerViewModel,
                onLoginClicked = {
                    navHost.navigate(NavGraph.Login.route)
                }
            )
        }

        composable(NavGraph.OTP.route) {
            OTPScreen(
                navController = navHost,
                onEmpresaClicked = {
                    navHost.navigate(

                        NavGraph.Empresa.route
                    )
                }
            )
        }

        composable(NavGraph.Empresa.route) {
            EmpresaScreen(
                empresaViewModel,
                navController = navHost
            )
        }

        composable(NavGraph.Home.route) {
            HomeScreen(
                clientesViewModel,
                authViewModel,
                onLogoutSuccess  = {
                    //authViewModel.signout()
                        navHost.navigate(NavGraph.Login.route) {
                        popUpTo(NavGraph.Home.route) { inclusive = true }
                    }
                }
            )
        }

    }
}