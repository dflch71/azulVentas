package com.azul.azulVentas.ui.presentation.container

sealed class NavGraph (val route: String) {
    object Welcome: NavGraph("welcome_screen")
    object Login: NavGraph("login_screen")
    object Registration: NavGraph("registration_screen/{idEmpresa}/{nomEmpresa}/{idPG}"){
        fun createRoute(idEmpresa: String, nomEmpresa: String, idPG: String) = "registration_screen/$idEmpresa/$nomEmpresa/$idPG"
    }
    object Home: NavGraph("home_screen")
    object OTP: NavGraph("otp_screen")
    object Empresa: NavGraph("empresa_screen")
    object EmpresaFB: NavGraph("empresaFB_screen")
    object Empresas: NavGraph("empresas_screen")
    object RegistrationEmail: NavGraph("registrationEmail_screen")
    object RecoverPassword: NavGraph("recoverPassword_screen")
}