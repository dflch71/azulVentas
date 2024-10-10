package com.azul.azulVentas.ui.presentation.Welcome.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.azul.azulVentas.R
import com.azul.azulVentas.ui.components.ActionButton
import com.azul.azulVentas.ui.presentation.Welcome.viewmodel.WelcomeViewModel
import com.azul.azulVentas.ui.presentation.login.viewmodel.AuthViewModel
import com.azul.azulVentas.ui.theme.DarkTextColor
import com.azul.azulVentas.ui.theme.PrimaryPinkBlended
import com.azul.azulVentas.ui.theme.PrimaryYellow
import com.azul.azulVentas.ui.theme.PrimaryYellowDark
import com.azul.azulVentas.ui.theme.PrimaryYellowLight

@Composable
fun WelcomeScreen(
    welcomeViewModel: WelcomeViewModel,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    onOpenLoginClicked: () -> Unit
) {
    val isNetworkAvailable by welcomeViewModel.isNetworkAvailable.observeAsState(true)

    LaunchedEffect(Unit) {
       welcomeViewModel.checkNetwork()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0f to PrimaryPinkBlended,
                    0.6f to PrimaryYellowLight,
                    1f to PrimaryYellow
                )
            )
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (!isNetworkAvailable!!) {
            AlertDialog(
                onDismissRequest = {},
                title = { Text("Conexión perdida") },
                text = { Text("No hay conexión a internet, por favor revisa tu red.") },
                confirmButton = {
                    Button(onClick = { /* Intenta reconectar o cerrar */ }) {
                        Text("OK")
                    }
                }
            )
        }

        Image(
            painter = painterResource(R.drawable.img_welcome),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .padding(top = 32.dp)
        )
        Spacer(
            modifier = Modifier.height(24.dp)
        )
        Text(
            text = "Let's start now!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(horizontal = 24.dp),
            color = DarkTextColor
        )

        Spacer( modifier = Modifier.height(16.dp))
        Text(
            text = "Create a beautiful Login App using\nKotlin, Jetpack Compose, and Material3",
            modifier = Modifier.padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = DarkTextColor
        )

        Spacer( modifier = Modifier.height(16.dp))


        Text(
            text = authViewModel.getUserEmail() ?: "",
            modifier = Modifier.padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = DarkTextColor
        )



        Spacer( modifier = Modifier.height(16.dp))
        Text(
            text = authViewModel.getUserLastDay() ?: "",
            modifier = Modifier.padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = DarkTextColor
        )

        Spacer(
            modifier = Modifier.weight(weight = 1f)
        )
        ActionButton(
            text = "Next",
            isNavigationArrowVisible = true,
            onClicked = onOpenLoginClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryYellowDark,
                contentColor = DarkTextColor
            ),
            shadowColor = PrimaryYellowDark,
            modifier = Modifier.padding(24.dp)
        )
    }
}