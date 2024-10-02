package com.azul.azulVentas.ui.presentation.container

sealed class NavGraph (val route: String) {
    object Welcome: NavGraph("welcome_screen")
    object Login: NavGraph("login_screen")
    object Registration: NavGraph("registration_screen")
    object Home: NavGraph("home_screen")
}