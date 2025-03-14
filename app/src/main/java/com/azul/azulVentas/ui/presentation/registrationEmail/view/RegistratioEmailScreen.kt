package com.azul.azulVentas.ui.presentation.registrationEmail.view

import android.util.Patterns
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.azul.azulVentas.R
import com.azul.azulVentas.core.utils.Result
import com.azul.azulVentas.domain.model.user.User
import com.azul.azulVentas.ui.components.CustomTextField
import com.azul.azulVentas.ui.components.DefaultBackArrow
import com.azul.azulVentas.ui.components.ErrorSuggestion
import com.azul.azulVentas.ui.components.PasswordTextField
import com.azul.azulVentas.ui.presentation.registrationEmail.viewmodel.RegisterEmailViewModel
import com.azul.azulVentas.ui.theme.PrimaryPink
import com.azul.azulVentas.ui.theme.PrimaryPinkBlended
import com.azul.azulVentas.ui.theme.PrimaryPinkDark
import kotlinx.coroutines.delay


@Composable
fun RegistrationEmailScreen(
    navController: NavController,
    registerEmailViewModel: RegisterEmailViewModel
){

    val registerState by registerEmailViewModel.registerState.collectAsState()

    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val emailError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    0f to PrimaryPinkBlended,
                    1f to PrimaryPink
                )
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(top = 32.dp, start = 32.dp, end = 32.dp, bottom = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Box(modifier = Modifier.weight(0.2f)) {
                DefaultBackArrow {
                    navController.popBackStack()
                }
            }
            Box(modifier = Modifier.weight(0.8f)) {
                Text(
                    text = "Registrar Usuario",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(700)
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = "@Email - Contraseña",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = buildAnnotatedString {
                        append("Ingresar credenciales de acceso \ncorreo electrónico - contraseña.")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        ) {}
                    },
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))
                CustomTextField(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    placeholder = "example@email.com",
                    leadingIconRes = R.drawable.ic_email,
                    label = "Email",
                    errorState = emailError,
                    keyboardType = KeyboardType.Email,
                    visualTransformation = VisualTransformation.None,
                    imeAction = ImeAction.Next,
                    onChanged = { newEmail -> email = newEmail },
                    lengthChar = 100
                )

                Spacer(modifier = Modifier.height(8.dp))
                PasswordTextField(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    placeholder = "Contraseña",
                    leadingIconRes = R.drawable.ic_key,
                    visibleIconRes = R.drawable.ic_visibility,
                    visibleOffIconRes = R.drawable.ic_visibility_off,
                    label = "Contraseña",
                    errorState = passwordError,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    onChanged = { newPassword -> password = newPassword },
                    lengthChar = 20
                )

                Spacer(modifier = Modifier.height(10.dp))
                if (emailError.value) {
                    ErrorSuggestion("EMAIL inválido (example@email.com).", isDark = false)
                }
                if (passwordError.value) {
                    Row() { ErrorSuggestion("CONTRASEÑA inválida >= 6 Caracteres ", isDark = false) }
                }

                when (registerState) {
                    is Result.ErrorPG -> {}
                    is Result.Error -> {
                        Row() { ErrorSuggestion("Error: ${email.text}, ${(registerState as Result.Error).message} ", isError = true, isDark = false) }
                    }
                    is Result.Success -> {
                        RegistrarUser(
                            email = email.text,
                            registerState = registerState,
                            registerEmailViewModel = registerEmailViewModel,
                            loginScreenCliked = { navController.popBackStack() }
                        )
                    }
                    null -> {}
                }

                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {

                        val patternEmail = Patterns.EMAIL_ADDRESS
                        val isEmailValid = patternEmail.matcher(email.text).matches() && email.text.isNotEmpty()
                        val isPasswordValid = password.text.isNotEmpty() && password.text.length > 5

                        emailError.value = !isEmailValid
                        passwordError.value = !isPasswordValid

                        val isRequiredFields = isPasswordValid
                        if (isEmailValid && isRequiredFields) { registerEmailViewModel.registerEmail(email.text, password.text) }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(62.dp)
                        .shadow(
                            elevation = 24.dp,
                            shape = RoundedCornerShape(percent = 50),
                            spotColor = PrimaryPinkDark
                        ),

                    shape = RoundedCornerShape(percent = 50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryPinkDark,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Registrar Usuario",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    }
}

@Composable
private fun RegistrarUser(
    email: String,
    registerState: Result<User>?,
    registerEmailViewModel: RegisterEmailViewModel,
    loginScreenCliked: () -> Unit
) {

    Row() { ErrorSuggestion("Registro exitoso: $email", isError = false, isDark = false) }
    Row() { ErrorSuggestion("Actualizando Datos ...", isError = false, isDark = false) }

    //Mostrar mensaje de actualización por 4 segundos
    LaunchedEffect(registerState) {
        delay(4000L)
        registerEmailViewModel.limpiarEstadoRegistro()
        loginScreenCliked()
    }










}