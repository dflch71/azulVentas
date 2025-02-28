package com.azul.azulVentas.ui.presentation.registration.view

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.azul.azulVentas.ui.presentation.registration.component.RegistrationScreenTemplate
import com.azul.azulVentas.ui.presentation.registration.viewmodel.RegisterEmailViewModel
import com.azul.azulVentas.ui.presentation.registration.viewmodel.RegisterViewModel
import com.azul.azulVentas.ui.theme.PrimaryViolet
import com.azul.azulVentas.ui.theme.PrimaryVioletDark
import com.azul.azulVentas.ui.theme.PrimaryVioletLight

@Composable
fun RegistrationScreen(
    registerViewModel: RegisterViewModel,
    registerEmailViewModel: RegisterEmailViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    onLoginClicked: () -> Unit,
    idEmpresa: String = "",
    nomEmpresa: String = ""
){
    RegistrationScreenTemplate(
        registerViewModel = registerViewModel,
        registerEmailViewModel = registerEmailViewModel,
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
        loginScreenCliked = onLoginClicked,
        idEmpresaFB = idEmpresa,
        nomEmpresaFB = nomEmpresa
    )
}