package com.azul.azulVentas.ui.presentation.registration.component

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.azul.azulVentas.R
import com.azul.azulVentas.ui.components.ActionButton
import com.azul.azulVentas.ui.components.CustomTextField
import com.azul.azulVentas.ui.components.DefaultBackArrow
import com.azul.azulVentas.ui.components.ErrorSuggestion
import com.azul.azulVentas.ui.components.PasswordTextField
import com.azul.azulVentas.ui.components.ReadTextField
import com.azul.azulVentas.ui.presentation.registration.viewmodel.RegisterEmailViewModel
import com.azul.azulVentas.ui.presentation.registration.viewmodel.RegisterViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.azul.azulVentas.core.utils.Result
import com.azul.azulVentas.domain.model.user.User


@Composable
fun RegistrationScreenTemplate(
    registerViewModel: RegisterViewModel,
    registerEmailViewModel: RegisterEmailViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    backgroundGradient: Array<Pair<Float, Color>>,
    secondaryActionButtonTitle: String,
    secondaryActionButtonColors: ButtonColors,
    actionButtonShadow: Color,
    loginScreenCliked: () -> Unit,
    idEmpresaFB: String = "",
    nomEmpresaFB: String = ""
) {

    val estadoRegistro by registerViewModel.estadoRegistro.observeAsState()

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

    //Guardar el ID registrado de la empresa en BD PG
    var idEmpresa by remember { mutableStateOf(-1) }
    var UID by remember { mutableStateOf("") }

    var nitEmpresa by remember { mutableStateOf(TextFieldValue(idEmpresaFB)) }
    var nomEmpresa by remember { mutableStateOf(TextFieldValue(nomEmpresaFB)) }
    var usuNombre by remember { mutableStateOf(TextFieldValue("")) }
    var usuApellido by remember { mutableStateOf(TextFieldValue("")) }
    var usuTelefono by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    val idEmpresaError = remember { mutableStateOf(false) }
    val nomEmpresaError = remember { mutableStateOf(false) }
    val usuNombreError = remember { mutableStateOf(false) }
    val usuApellidoError = remember { mutableStateOf(false) }
    val usuTelefonoError = remember { mutableStateOf(false) }
    val emailError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    val registerState by registerEmailViewModel.registerState.collectAsState()

    LaunchedEffect(keyboardHeight) {
        coroutineScope.launch {
            scrollState.scrollBy(keyboardHeight.toFloat())
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(*backgroundGradient))
            .systemBarsPadding()
            .verticalScroll(scrollState)
            .imePadding(),
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
            Box(modifier = Modifier.weight(0.5f)) {
                DefaultBackArrow {
                    navController.popBackStack()
                }
            }
            Box(modifier = Modifier.weight(1.5f)) {
                Text(
                    text = "Registrar Usuario",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(700)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        ReadTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            leadingIconRes = R.drawable.ic_identification,
            label = "Nít - ID",
            textValue = nitEmpresa.text
        )

        Spacer(modifier = Modifier.height(8.dp))
        ReadTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            leadingIconRes = R.drawable.ic_factory,
            label = "Nombre Empresa",
            textValue = nomEmpresa.text
        )

        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            placeholder = "Nombres",
            leadingIconRes = R.drawable.ic_person,
            label = "Nombres",
            errorState = usuNombreError,
            keyboardType = KeyboardType.Text,
            visualTransformation = VisualTransformation.None,
            imeAction = ImeAction.Next,
            onChanged = { newUsuNombre -> usuNombre = newUsuNombre },
            lengthChar = 50
        )

        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            placeholder = "Apellidos",
            leadingIconRes = R.drawable.ic_person,
            label = "Apellidos",
            errorState = usuApellidoError,
            keyboardType = KeyboardType.Text,
            visualTransformation = VisualTransformation.None,
            imeAction = ImeAction.Next,
            onChanged = { newUsuNombre -> usuApellido = newUsuNombre },
            lengthChar = 50
        )

        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            placeholder = "Teléfono",
            leadingIconRes = R.drawable.ic_phone,
            label = "Teléfono",
            errorState = usuTelefonoError,
            keyboardType = KeyboardType.Phone,
            visualTransformation = VisualTransformation.None,
            imeAction = ImeAction.Next,
            onChanged = { newTelefono -> usuTelefono = newTelefono },
            lengthChar = 10
        )

        Spacer(modifier = Modifier.height(8.dp))
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
            ErrorSuggestion("EMAIL inválido (example@email.com).")
        }
        if (usuTelefonoError.value) {
            Row() { ErrorSuggestion("TELÉFONO inválido (10 dígitos).") }
        }
        if (passwordError.value) {
            Row() { ErrorSuggestion("CONTRASEÑA inválida >= 6 Caracteres ") }
        }
        if (idEmpresaError.value || nomEmpresaError.value || usuNombreError.value || usuApellidoError.value) {
            Row() {
                ErrorSuggestion("Valor requerido o incompleto.")
            }
        }

        estadoRegistro?.let { result ->
            when {
                result.isSuccess -> {
                    Row() { ErrorSuggestion("USUARIO registrada exitosamente", isError = false) }
                    Row() { ErrorSuggestion("Actualizando Datos ...", isError = false) }

                    //Mostrar mensaje de actualización por 4 segundos
                    coroutineScope.launch {
                        delay(4000L)
                        registerViewModel.limpiarEstadoRegistro()
                        loginScreenCliked()
                    }

                }
                result.isFailure -> {
                    Row() { ErrorSuggestion("Error: ${result.exceptionOrNull()?.message} ${nitEmpresa.text}") }
                }
                else -> {}
            }
        }

        when (registerState) {
            is Result.Success -> {
                RegistrarEmpresa(nitEmpresa, registerState, registerEmailViewModel, loginScreenCliked)
                //Text("Registro exitoso: ${(registerState as Result.Success<User>).data.email}")
            }
            is Result.Error -> {
                if ((registerState as Result.Error).message.contains("0K")) {
                    // Si el mensaje contiene OK, se registra el usuario
                    // Esto porque en PG puede existir un correo duplicado
                    // pero a diferentes empresas
                    RegistrarEmpresa(nitEmpresa, registerState, registerEmailViewModel, loginScreenCliked)
                } else {
                    Row() { ErrorSuggestion("Error: ${(registerState as Result.Error).message} ${email.text}") }
                    //Text("Error: ${(registerState as Result.Error).message}", color = Color.Red)
                }
            }
            null -> {}

        }


        Spacer(modifier = Modifier.height(10.dp))
        ActionButton(
            text = secondaryActionButtonTitle,
            isNavigationArrowVisible = false,
            onClicked = {

                val isNitValid = nitEmpresa.text.isNotEmpty() && nitEmpresa.text.length > 5
                val isNomEmpresa = nomEmpresa.text.isNotEmpty() && nomEmpresa.text.length > 3
                val isUsuNombre =  usuNombre.text.isNotEmpty() && usuNombre.text.length > 3
                val isUsuApellido =  usuApellido.text.isNotEmpty() && usuApellido.text.length > 3
                val patternTelefono = Patterns.PHONE  //Regex("[0-9]{10}")
                val isTelefonoValid =  patternTelefono.matcher(usuTelefono.text).matches() && usuTelefono.text.isNotEmpty() && usuTelefono.text.length == 10
                val patternEmail = Patterns.EMAIL_ADDRESS
                val isEmailValid = patternEmail.matcher(email.text).matches() && email.text.isNotEmpty()
                val isPasswordValid = password.text.isNotEmpty() && password.text.length > 5

                idEmpresaError.value = !isNitValid
                nomEmpresaError.value = !isNomEmpresa
                usuNombreError.value = !isUsuNombre
                usuApellidoError.value = !isUsuApellido
                usuTelefonoError.value = !isTelefonoValid
                emailError.value = !isEmailValid
                passwordError.value = !isPasswordValid

                val isRequiredFields =  isNitValid && isNomEmpresa && isUsuNombre && isUsuApellido && isPasswordValid
                if ( isEmailValid && isTelefonoValid && isRequiredFields ) {
                    /*registerViewModel.registraUsuario(email.text, password.text) { user ->
                        if (user != null) {
                            UID = user.uid;
                            //Login exitoso, llama a onLoginSuccess
                            //onLoginSuccess()
                            //TODO: BD - PG
                        } else {
                            errorMessage = "Register Failed. Try Again."
                        }
                    }*/

                    registerEmailViewModel.registerEmail( email.text, password.text)
                }
            },
            onLongClicked = {},
            colors = secondaryActionButtonColors,
            shadowColor = actionButtonShadow,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        )
    }
}

@Composable
private fun RegistrarEmpresa(
    nitEmpresa: TextFieldValue,
    registerState: Result<User>?,
    registerEmailViewModel: RegisterEmailViewModel,
    loginScreenCliked: () -> Unit
) {
    Row() { ErrorSuggestion("Registro exitoso: ${nitEmpresa.text}", isError = false) }
    Row() { ErrorSuggestion("Actualizando Datos ...", isError = false) }

    //Mostrar mensaje de actualización por 4 segundos
    LaunchedEffect(registerState) {
        delay(4000L)
        registerEmailViewModel.limpiarEstadoRegistro()
        loginScreenCliked()
    }
}

