package com.azul.azulVentas.ui.presentation.login.view

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.azul.azulVentas.R
import com.azul.azulVentas.ui.presentation.login.component.AuthenticationScreenTemplate
import com.azul.azulVentas.ui.presentation.login.viewmodel.AuthViewModel
import com.azul.azulVentas.ui.theme.PrimaryPink
import com.azul.azulVentas.ui.theme.PrimaryPinkBlended
import com.azul.azulVentas.ui.theme.PrimaryPinkDark
import com.azul.azulVentas.ui.theme.PrimaryPinkLight

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    onRegistrationClicked: () -> Unit,
    onRecoveryClicked: () -> Unit
) {

    // Resetear mensaje cuando la pantalla se vuelve a componer
    authViewModel.clearState()

    AuthenticationScreenTemplate(
        authViewModel,
        modifier = modifier,
        backgroundGradient = arrayOf(
            0f to PrimaryPinkBlended,
            1f to PrimaryPink
        ),
        imgRes = R.drawable.img_coder_m,
        title = "Bienvenido!",
        subtitle = "Ingrese Credenciales.",
        mainActionButtonTitle = "Siguiente",
        secondaryActionButtonTitle = "Crear Cuenta",
        mainActionButtonColors = ButtonDefaults.buttonColors(
            containerColor = PrimaryPinkDark,
            contentColor = Color.White
        ),
        secondaryActionButtonColors = ButtonDefaults.buttonColors(
            containerColor = PrimaryPinkLight,
            contentColor = Color.White
        ),
        actionButtonShadow = PrimaryPinkDark,
        onMainActionButtonClicked = onLoginSuccess,
        onSecondaryActionButtonClicked = onRegistrationClicked,
        onLoginSuccess = onLoginSuccess,
        onRecoveryClicked = onRecoveryClicked
    )

}

