package com.azul.azulVentas.ui.presentation.container

sealed class NavGraph (val route: String) {
    object Welcome: NavGraph("welcome_screen")
    object Login: NavGraph("login_screen")
    object Registration: NavGraph("registration_screen")
    object Home: NavGraph("home_screen")
    object OTP: NavGraph("otp_screen")
    object Empresa: NavGraph("empresa_screen")
    object Empresas: NavGraph("empresas_screen")
    object RecoverPassword: NavGraph("recoverPassword_screen")

}