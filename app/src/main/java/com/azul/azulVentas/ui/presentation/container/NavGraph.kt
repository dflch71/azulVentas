package com.azul.azulVentas.ui.presentation.container



sealed class NavGraph (val route: String) {
    object Welcome: NavGraph("welcome_screen")
    object Login: NavGraph("login_screen")
    object Registration: NavGraph("registration_screen/{idEmpresa}/{nomEmpresa}/{idPG}"){
        fun createRoute(idEmpresa: String, nomEmpresa: String, idPG: String) = "registration_screen/$idEmpresa/$nomEmpresa/$idPG"
    }
    object Home: NavGraph("home_screen/{idEmpresa}/{nomEmpresa}") {
        const val idEmpresaArg = "idEmpresa"
        const val nomEmpresaArg = "nomEmpresa"
        fun createRoute(idEmpresa: String, nomEmpresa: String) = "home_screen/$idEmpresa/$nomEmpresa"
    }
    object OTP: NavGraph("otp_screen")
    object Empresa: NavGraph("empresa_screen")
    object EmpresaFB: NavGraph("empresaFB_screen")
    object Empresas: NavGraph("empresas_screen")
    object RegistrationEmail: NavGraph("registrationEmail_screen")
    object RecoverPassword: NavGraph("recoverPassword_screen")

    object DiaEstadistica :
        NavGraph("dia_estadistica_screen/{tipoOperacion}/{empresaID}/{title}/{fecha}/{facturas}/{efectivo}/{credito}/{total}") {
        fun createRoute(
            tipoOperacion: String,
            empresaID: String,
            title: String,
            fecha: String,
            facturas: String,
            efectivo: String,
            credito: String,
            total: String) =
            "dia_estadistica_screen/$tipoOperacion/$empresaID/$title/$fecha/$facturas/$efectivo/$credito/$total"
    }


    object VentaSemana: NavGraph("ventaSemana_screen/{idEmpresa}/{nomEmpresa}") {
        const val idEmpresaArg = "idEmpresa"
        const val nomEmpresaArg = "nomEmpresa"
        fun createRoute(idEmpresa: String, nomEmpresa: String) = "ventaSemana_screen/$idEmpresa/$nomEmpresa"
    }
}