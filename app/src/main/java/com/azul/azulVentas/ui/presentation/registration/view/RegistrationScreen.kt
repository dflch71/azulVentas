package com.azul.azulVentas.ui.presentation.registration.view

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.azul.azulVentas.data.repository.auth.AuthRepository
import com.azul.azulVentas.ui.presentation.login.viewmodel.AuthViewModel
import com.azul.azulVentas.ui.presentation.registration.component.RegistrationScreenTemplate
import com.azul.azulVentas.ui.presentation.registrationEmail.viewmodel.RegisterEmailViewModel
import com.azul.azulVentas.ui.presentation.registration.viewmodel.RegisterViewModel
import com.azul.azulVentas.ui.presentation.userPG.viewmodel.UserPGViewModel
import com.azul.azulVentas.ui.theme.PrimaryViolet
import com.azul.azulVentas.ui.theme.PrimaryVioletDark
import com.azul.azulVentas.ui.theme.PrimaryVioletLight

@Composable
fun RegistrationScreen(
    authViewModel: AuthViewModel,
    userPGViewModel: UserPGViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    onEmpresasClicked: () -> Unit,
    idEmpresa: String = "",
    nomEmpresa: String = "",
    idPG: String = ""
){
    RegistrationScreenTemplate(
        authViewModel = authViewModel,
        userPGViewModel = userPGViewModel,
        navController = navController,
        modifier = modifier,
        backgroundGradient = arrayOf(
            0f to PrimaryViolet,
            1f to PrimaryVioletDark
        ),
        secondaryActionButtonTitle = "Registrar",
        secondaryActionButtonColors = ButtonDefaults.buttonColors(
            containerColor = PrimaryVioletLight,
            contentColor = Color.White
        ),
        actionButtonShadow = PrimaryVioletDark,
        empresaScreenClicked = onEmpresasClicked,
        idEmpresaFB = idEmpresa,
        nomEmpresaFB = nomEmpresa,
        idPG = idPG
    )
}