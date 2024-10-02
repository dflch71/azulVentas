package com.azul.azulVentas.ui.presentation.login.view

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.azul.azulVentas.R
import com.azul.azulVentas.ui.components.AuthenticationScreenTemplate
import com.azul.azulVentas.ui.presentation.login.viewmodel.AuthViewModel
import com.azul.azulVentas.ui.theme.PrimaryPink
import com.azul.azulVentas.ui.theme.PrimaryPinkBlended
import com.azul.azulVentas.ui.theme.PrimaryPinkDark
import com.azul.azulVentas.ui.theme.PrimaryPinkLight

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginClicked: () -> Unit,
    onRegistrationClicked: () -> Unit,
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit
) {

    //val (email, setEmail) = remember { mutableStateOf("") }
    //val (password, setPassword) = remember { mutableStateOf("") }
    val email = viewModel.email
    val password = viewModel.password
    var errorMessage by remember { mutableStateOf<String?>(null) }

    /*
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = setEmail,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = password,
            onValueChange = setPassword,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        // Botón para iniciar sesión (Login)
        Button(
            onClick = {
                viewModel.login(email, password) { user ->
                    if (user != null) {
                        // Login exitoso, llama a onLoginSuccess
                        onLoginSuccess()
                    } else {
                        errorMessage = "Login failed. Try again."
                    }
                }
            },

            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Login")
        }

        // Botón para registrarse
        TextButton(
            onClick = onRegistrationClicked, // Navegar al registro
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Don't have an account? Sign up")
        }
    }*/


    AuthenticationScreenTemplate(
        viewModel,
        modifier = modifier,
        backgroundGradient = arrayOf(
            0f to PrimaryPinkBlended,
            1f to PrimaryPink
        ),
        imgRes = R.drawable.img_coder_m,
        title = "Welcome back!",
        subtitle = "Please, Log In.",
        mainActionButtonTitle = "Continue",
        secondaryActionButtonTitle = "Create an Account",
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
        onLoginSuccess = onLoginSuccess
    )

}