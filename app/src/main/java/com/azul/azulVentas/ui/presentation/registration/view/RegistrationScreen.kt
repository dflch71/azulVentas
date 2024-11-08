package com.azul.azulVentas.ui.presentation.registration.view

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.azul.azulVentas.R
import com.azul.azulVentas.ui.presentation.registration.comoponent.RegistrationScreenTemplate
import com.azul.azulVentas.ui.presentation.registration.viewmodel.RegisterViewModel
import com.azul.azulVentas.ui.theme.PrimaryViolet
import com.azul.azulVentas.ui.theme.PrimaryVioletDark
import com.azul.azulVentas.ui.theme.PrimaryVioletLight

@Composable
fun RegistrationScreen(
    registerViewModel: RegisterViewModel,
    modifier: Modifier = Modifier,
    onLoginClicked: () -> Unit
){
    RegistrationScreenTemplate(
        registerViewModel = registerViewModel,
        modifier = modifier,
        backgroundGradient = arrayOf(
            0f to PrimaryViolet,
            1f to PrimaryVioletDark
        ),
        imgRes = R.drawable.img_coder_w,
        title = "Hi there!",
        subtitle = "Let's Get Started",
        secondaryActionButtonTitle = "Register",
        secondaryActionButtonColors = ButtonDefaults.buttonColors(
            containerColor = PrimaryVioletLight,
            contentColor = Color.White
        ),
        actionButtonShadow = PrimaryVioletDark
    )
}