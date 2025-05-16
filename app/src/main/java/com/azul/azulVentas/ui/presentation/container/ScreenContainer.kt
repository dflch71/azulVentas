package com.azul.azulVentas.ui.presentation.container

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.azul.azulEgresos.ui.presentation.egreso.viewmodel.EgresoPeriodoViewModel
import com.azul.azulVentas.ui.presentation.clientes.viewmodel.ClientesViewModel
import com.azul.azulVentas.ui.presentation.compra.viewmodel.CompraDiaViewModel
import com.azul.azulVentas.ui.presentation.compra.viewmodel.CompraPeriodoViewModel
import com.azul.azulVentas.ui.presentation.compra.viewmodel.CompraSemanaViewModel
import com.azul.azulVentas.ui.presentation.diaEstadistica.view.DiaEstadisticaScreen
import com.azul.azulVentas.ui.presentation.egreso.viewmodel.EgresoDiaViewModel
import com.azul.azulVentas.ui.presentation.egreso.viewmodel.EgresoSemanaViewModel
import com.azul.azulVentas.ui.presentation.empresa.view.EmpresaScreen
import com.azul.azulVentas.ui.presentation.empresa.viewmodel.EmpresaViewModel
import com.azul.azulVentas.ui.presentation.empresaFB.view.EmpresaFBScreen
import com.azul.azulVentas.ui.presentation.empresaFB.viewmodel.EmpresaFBViewModel
import com.azul.azulVentas.ui.presentation.empresaPG.viewmodel.EmpresaPGViewModel
import com.azul.azulVentas.ui.presentation.empresas.view.EmpresasScreen
import com.azul.azulVentas.ui.presentation.empresas.viewmodel.EmpresasPGViewModel
import com.azul.azulVentas.ui.presentation.home.HomeScreen
import com.azul.azulVentas.ui.presentation.login.view.LoginScreen
import com.azul.azulVentas.ui.presentation.login.viewmodel.AuthViewModel
import com.azul.azulVentas.ui.presentation.network.viewmodel.NetworkViewModel
import com.azul.azulVentas.ui.presentation.otp.view.OTPScreen
import com.azul.azulVentas.ui.presentation.recoverPassword.view.RecoverPasswordScreen
import com.azul.azulVentas.ui.presentation.recoverPassword.viewmodel.RecoverPasswordViewModel
import com.azul.azulVentas.ui.presentation.registration.view.RegistrationScreen
import com.azul.azulVentas.ui.presentation.registration.viewmodel.RegisterViewModel
import com.azul.azulVentas.ui.presentation.registrationEmail.view.RegistrationEmailScreen
import com.azul.azulVentas.ui.presentation.registrationEmail.viewmodel.RegisterEmailViewModel
import com.azul.azulVentas.ui.presentation.userPG.viewmodel.UserPGViewModel
import com.azul.azulVentas.ui.presentation.usuarioEmpresas.viewmodel.UsuarioEmpresasPGViewModel
import com.azul.azulVentas.ui.presentation.venta.viewmodel.VentaDiaViewModel
import com.azul.azulVentas.ui.presentation.venta.viewmodel.VentaPeriodoViewModel
import com.azul.azulVentas.ui.presentation.venta.viewmodel.VentaSemanaViewModel
import com.azul.azulVentas.ui.presentation.ventaPOS.viewModel.VentaPosDiaViewModel
import com.azul.azulVentas.ui.presentation.ventaPOS.viewModel.VentaPosPeriodoViewModel
import com.azul.azulVentas.ui.presentation.ventaPOS.viewModel.VentaPosSemanaViewModel
import com.azul.azulVentas.ui.presentation.welcome.view.WelcomeScreen

@Composable
fun ScreenContainer(
    navHost: NavHostController,
    networkViewModel: NetworkViewModel,
    authViewModel: AuthViewModel,
    registerViewModel: RegisterViewModel,
    clientesViewModel: ClientesViewModel,
    empresaViewModel: EmpresaViewModel,
    empresaFBViewModel: EmpresaFBViewModel,
    empresaPGViewModel: EmpresaPGViewModel,
    empresasPGViewModel: EmpresasPGViewModel,
    usuarioEmpresasPGViewModel: UsuarioEmpresasPGViewModel,
    userPGViewModel: UserPGViewModel,
    recoverPasswordViewModel: RecoverPasswordViewModel,
    registerEmailViewModel: RegisterEmailViewModel,
    ventaDiaViewModel: VentaDiaViewModel,
    ventaSemanaViewModel: VentaSemanaViewModel,
    ventaPeriodoViewModel: VentaPeriodoViewModel,
    ventaPosDiaViewModel: VentaPosDiaViewModel,
    ventaPosSemanaViewModel: VentaPosSemanaViewModel,
    ventaPosPeriodoViewModel: VentaPosPeriodoViewModel,
    egresoDiaViewModel: EgresoDiaViewModel,
    egresoSemanaViewModel: EgresoSemanaViewModel,
    egresoPeriodoViewModel: EgresoPeriodoViewModel,
    compraDiaViewModel: CompraDiaViewModel,
    compraSemanaViewModel: CompraSemanaViewModel,
    compraPeriodoViewModel: CompraPeriodoViewModel,
    ) {

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
                        navHost.navigate(NavGraph.Empresas.route)
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
                authViewModel = authViewModel, // ViewModel inyectado

                onEmpresaFBClicked = {
                    navHost.navigate(NavGraph.RegistrationEmail.route) // Navegar a la pantalla de Registro
                },

                onLoginSuccess = {
                    //navHost.navigate(NavGraph.Home.route) // Si el login es exitoso, navegar al Home
                    navHost.navigate(NavGraph.Empresas.route) // Si el login es exitoso, navegar a Empresas
                },

                onRecoveryClicked = {
                    navHost.navigate(NavGraph.RecoverPassword.route)
                }
            )
        }

        composable(
            NavGraph.Registration.route,
            arguments = listOf(
                navArgument("idEmpresa") { type = NavType.StringType },
                navArgument("nomEmpresa") { type = NavType.StringType },
                navArgument("idPG") { type = NavType.StringType },
            )
        ) {
            backStackEntry ->
            val idEmpresa = backStackEntry.arguments?.getString("idEmpresa")
            val nomEmpresa = backStackEntry.arguments?.getString("nomEmpresa")
            val idPG = backStackEntry.arguments?.getString("idPG")

            RegistrationScreen(
                authViewModel,
                userPGViewModel,
                navHost,
                onEmpresasClicked = { navHost.navigate(NavGraph.Empresas.route) {} },
                idEmpresa = idEmpresa ?: "",
                nomEmpresa = nomEmpresa ?: "",
                idPG = idPG ?: ""
            )
        }

        composable(NavGraph.OTP.route) {
            OTPScreen(
                navController = navHost,
                onEmpresaClicked = { navHost.navigate( NavGraph.Empresa.route) }
            )
        }

        composable(NavGraph.Empresa.route) {
            EmpresaScreen(
                empresaViewModel,
                navController = navHost,
                onButtonClicked = { navHost.navigate(NavGraph.Welcome.route) {
                    popUpTo(NavGraph.Welcome.route) { inclusive = true }
                } }
            )
        }

        composable(NavGraph.Empresas.route) {
            EmpresasScreen(
                navController = navHost,
                authViewModel = authViewModel,
                empresasPGViewModel = empresasPGViewModel,
                HomeScreenClicked = { idEmpresa, nomEmpresa -> navHost.navigate(NavGraph.Home.createRoute(idEmpresa, nomEmpresa)) },
                RegisterScreenClicked = { navHost.navigate(NavGraph.EmpresaFB.route) }
            )
        }

        composable(NavGraph.EmpresaFB.route) {
            //EmpresaFBScreen Debe pasar parametros a la pantalla de Registro
            EmpresaFBScreen(
                navController = navHost,
                empresaFBViewModel,
                empresaPGViewModel,
                usuarioEmpresasPGViewModel,
                authViewModel,
                empresasScreenClicked = {
                    navHost.navigate(NavGraph.Empresas.route) {
                        popUpTo(NavGraph.Welcome.route) { inclusive = true } }
                },
                onRegistrationClicked = { id, nom, idPG ->
                    navHost.navigate(NavGraph.Registration.createRoute(id, nom, idPG))
                }
            )
        }

        composable(NavGraph.RegistrationEmail.route) {
            RegistrationEmailScreen(
                navController = navHost,
                registerEmailViewModel
            )
        }

        composable(NavGraph.RecoverPassword.route) {
            RecoverPasswordScreen(
                navController = navHost,
                viewModel = recoverPasswordViewModel
            )
        }



        composable(
            route = NavGraph.Home.route,
            arguments = listOf(
                navArgument(NavGraph.Home.idEmpresaArg) { type = NavType.StringType },
                navArgument(NavGraph.Home.nomEmpresaArg) { type = NavType.StringType }
            )

        ) {
                backStackEntry ->
            val idEmpresa = backStackEntry.arguments?.getString(NavGraph.Home.idEmpresaArg)
            val nomEmpresa = backStackEntry.arguments?.getString(NavGraph.Home.nomEmpresaArg)

            if (idEmpresa != null) {
                HomeScreen(
                    idEmpresa = idEmpresa ?: "",
                    nombreEmpresa = nomEmpresa ?: "",
                    navController = navHost,
                    clientesViewModel,
                    authViewModel,
                    onLogoutSuccess = {
                        //authViewModel.signout()
                        navHost.navigate(NavGraph.Login.route) {
                            popUpTo(NavGraph.Home.route) { inclusive = true }
                        }
                    },
                    ventaDiaViewModel,
                    ventaSemanaViewModel,
                    ventaPeriodoViewModel,
                    ventaPosDiaViewModel,
                    ventaPosSemanaViewModel,
                    ventaPosPeriodoViewModel,
                    egresoDiaViewModel,
                    egresoSemanaViewModel,
                    egresoPeriodoViewModel,
                    compraDiaViewModel,
                    compraSemanaViewModel,
                    compraPeriodoViewModel,
                    networkViewModel
                )
            }
        }

        composable(
            //route = "dia_estadistica_screen/{title}/{fecha}",
            route = NavGraph.DiaEstadistica.route,
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("fecha") { type = NavType.StringType }
            )

        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val fecha = backStackEntry.arguments?.getString("fecha") ?: ""
            DiaEstadisticaScreen(title = title, fecha = fecha, navController = navHost)
        }
    }
}