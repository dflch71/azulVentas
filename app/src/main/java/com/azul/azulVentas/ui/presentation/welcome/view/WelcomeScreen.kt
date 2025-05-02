package com.azul.azulVentas.ui.presentation.welcome.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.azul.azulVentas.R
import com.azul.azulVentas.core.utils.Utility.Companion.calculateDaysToTargetDate
import com.azul.azulVentas.ui.components.ActionButton
import com.azul.azulVentas.ui.presentation.login.viewmodel.AuthViewModel
import com.azul.azulVentas.ui.presentation.network.viewmodel.NetworkViewModel
import com.azul.azulVentas.ui.theme.DarkTextColor
import com.azul.azulVentas.ui.theme.PrimaryPinkBlended
import com.azul.azulVentas.ui.theme.PrimaryVioletDark
import com.azul.azulVentas.ui.theme.PrimaryYellow
import com.azul.azulVentas.ui.theme.PrimaryYellowDark
import com.azul.azulVentas.ui.theme.PrimaryYellowLight
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun WelcomeScreen(
    networkViewModel: NetworkViewModel,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    onOpenLoginClicked: () -> Unit,
    onOpenOTPClicked: () -> Unit
) {
    val isNetworkAvailable by networkViewModel.networkStatus.collectAsState()
    val isConnect = remember { mutableStateOf(true) }

    val snackbarHostState = remember { SnackbarHostState() }  // Para el Snackbar
    val context = LocalContext.current

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(align = Alignment.TopCenter)
                    .statusBarsPadding()

            )
        }
    ) {
        Box (modifier = Modifier.padding(it)) {
            if (!isNetworkAvailable) {
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar(
                        message = "No hay Conexión a Internet",
                        withDismissAction = true
                    )
                }
            }

            isConnect.value = isNetworkAvailable

            WelcomeContent(
                isConnect = isNetworkAvailable,
                modifier = modifier,
                authViewModel = authViewModel,
                onOpenLoginClicked = onOpenLoginClicked,
                onOpenOTPClicked = onOpenOTPClicked
            )
        }
    }
}

@Composable
fun ShowToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
private fun WelcomeContent(
    isConnect: Boolean,
    modifier: Modifier,
    authViewModel: AuthViewModel,
    onOpenLoginClicked: () -> Unit,
    onOpenOTPClicked: () -> Unit
) {
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

        Image(
            painter = painterResource(R.drawable.img_welcome),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .padding(top = 32.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Azul Soluciones",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(horizontal = 24.dp),
            color = DarkTextColor
        )

        //Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Registre su Empresa y tenga La información\n de los movimientos financieros de su Negocio",
            modifier = Modifier.padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = DarkTextColor
        )

        val user = "${authViewModel.getUserEmail() ?: ""}\n${authViewModel.getUserLastDay() ?: ""}"
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = user,
            modifier = Modifier.padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = DarkTextColor
        )

        /*
        Text(
            text = authViewModel.getUserEmail() ?: "",
            modifier = Modifier.padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = DarkTextColor
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = authViewModel.getUserLastDay() ?: "",
            modifier = Modifier.padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = DarkTextColor
        )
        */

        var daysLogged = 0L
        if (!authViewModel.getUserLastDay().isNullOrEmpty()) {
            daysLogged = calculateDaysToTargetDate(
                LocalDateTime.parse(
                    authViewModel.getUserLastDay(),
                    DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy HH:mm:ss a")
                )
            )

            //Si el usuario completa 5 dias, debe iniciar sesion
            if (daysLogged > 5) { authViewModel.signout() }
        }

        if (daysLogged > 0L) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Days logged: $daysLogged",
                modifier = Modifier.padding(horizontal = 24.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = DarkTextColor
            )
        }

        if (!isConnect) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_wifi_off), // Replace with your image resource
                    contentDescription = "No Internet",
                    modifier = Modifier
                        .size(24.dp),
                    tint = PrimaryVioletDark
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "Sin Conexión a Internet",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = PrimaryVioletDark)

            }
        }

        Spacer(modifier = Modifier.weight(weight = 1f))

        ActionButton(
            text = "Siguiente",
            isNavigationArrowVisible = true,
            onClicked = onOpenLoginClicked,
            onLongClicked = onOpenOTPClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryYellowDark,
                contentColor = DarkTextColor
            ),
            shadowColor = PrimaryYellowDark,
            modifier = Modifier.padding(24.dp)
        )
    }

}
