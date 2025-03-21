package com.azul.azulVentas.ui.presentation.registration.component

import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.azul.azulVentas.core.utils.Result.Error
import com.azul.azulVentas.core.utils.Result.ErrorPG
import com.azul.azulVentas.core.utils.Result.Success
import com.azul.azulVentas.domain.model.userPG.UserPG
import com.azul.azulVentas.ui.components.ActionButton
import com.azul.azulVentas.ui.components.CustomTextField
import com.azul.azulVentas.ui.components.DefaultBackArrow
import com.azul.azulVentas.ui.components.ErrorSuggestion
import com.azul.azulVentas.ui.components.ReadTextField
import com.azul.azulVentas.ui.presentation.login.viewmodel.AuthViewModel
import com.azul.azulVentas.ui.presentation.userPG.viewmodel.UserPGViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun RegistrationScreenTemplate(
    authViewModel: AuthViewModel,
    userPGViewModel: UserPGViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    backgroundGradient: Array<Pair<Float, Color>>,
    secondaryActionButtonTitle: String,
    secondaryActionButtonColors: ButtonColors,
    actionButtonShadow: Color,
    empresaScreenClicked: () -> Unit,
    idEmpresaFB: String = "",
    nomEmpresaFB: String = "",
    idPG: String = ""
) {

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

    var nitEmpresa by remember { mutableStateOf(TextFieldValue(idEmpresaFB)) }
    var nomEmpresa by remember { mutableStateOf(TextFieldValue(nomEmpresaFB)) }
    var email by remember { mutableStateOf(TextFieldValue(authViewModel.getUserEmail().toString())) }
    var password by remember { mutableStateOf(TextFieldValue(authViewModel.getUserUid().toString())) }

    var usuNombre by remember { mutableStateOf(TextFieldValue("")) }
    var usuApellido by remember { mutableStateOf(TextFieldValue("")) }
    var usuTelefono by remember { mutableStateOf(TextFieldValue("")) }

    val usuNombreError = remember { mutableStateOf(false) }
    val usuApellidoError = remember { mutableStateOf(false) }
    val usuTelefonoError = remember { mutableStateOf(false) }


    var msgError by remember { mutableStateOf("") }
    var showSuccessMessage by remember { mutableStateOf(false) }
    var showErrorMessage by remember { mutableStateOf(false) }


    LaunchedEffect(keyboardHeight) {
        coroutineScope.launch {
            scrollState.scrollBy(keyboardHeight.toFloat())
        }
    }

    val insertUserState by userPGViewModel.insertUserState.collectAsState()
    when (insertUserState) {
        is Success -> {
            val user = (insertUserState as Success<UserPG>).data
            if (user.EMPRESA_ID != -1) {
                // Handle the successful insertion
                // Show success message or navigate
                showSuccessMessage = true
            }
        }

        is ErrorPG -> {
            val errorState = insertUserState as ErrorPG // Correct type cast
            val error = errorState.throwable.message ?: "Unknown error"
            // Handle the error
            showErrorMessage = true
            msgError = error
        }

        is Error -> {}
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
            label = "Nít - ID (${idPG})",
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
        ReadTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            leadingIconRes = R.drawable.ic_factory,
            label = "Email Registrado",
            textValue = email.text
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

        Spacer(modifier = Modifier.height(10.dp))
        if (usuTelefonoError.value) {
            ErrorSuggestion("TELÉFONO inválido (10 dígitos).", isDark = false)
        }
        if (usuNombreError.value || usuApellidoError.value) {
            Row() {
                ErrorSuggestion("Valor requerido o incompleto.", isDark = false)
            }
        }

        if (showSuccessMessage) {
            MsgInsert(
                userPGViewModel,
                empresaScreenClicked
            )
        }
        if (showErrorMessage) { MsgError(msgError) }

        Spacer(modifier = Modifier.height(10.dp))
        ActionButton(
            text = secondaryActionButtonTitle,
            isNavigationArrowVisible = false,
            onClicked = {

                val isUsuNombre = usuNombre.text.isNotEmpty() && usuNombre.text.length > 3
                val isUsuApellido = usuApellido.text.isNotEmpty() && usuApellido.text.length > 3
                val patternTelefono = Patterns.PHONE  //Regex("[0-9]{10}")
                val isTelefonoValid = patternTelefono.matcher(usuTelefono.text)
                    .matches() && usuTelefono.text.isNotEmpty() && usuTelefono.text.length == 10

                usuNombreError.value = !isUsuNombre
                usuApellidoError.value = !isUsuApellido
                usuTelefonoError.value = !isTelefonoValid


                val isRequiredFields = isUsuNombre && isUsuApellido
                if (isTelefonoValid && isRequiredFields) {
                    val userPG = UserPG(
                        //USUARIO_ID = 0,
                        EMPRESA_ID = idPG.toInt(),
                        USU_NOMBRE = usuNombre.text.uppercase(),
                        USU_APELLIDO = usuApellido.text.uppercase(),
                        USU_TELEFONO = usuTelefono.text,
                        USU_EMAIL = email.text,
                        USU_PASS = password.text,
                        USU_USUARIO = password.text,
                    )
                    userPGViewModel.insertUserPG(userPG)
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
fun MsgInsert(
    userPGViewModel: UserPGViewModel,
    empresaScreenClicked: () -> Unit
) {
    var showSuccessMessage by remember { mutableStateOf(true) }
    var showUpdatingMessage by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = showSuccessMessage,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column {
                    Row { ErrorSuggestion("Registro exitoso", isError = false, isDark = false) }
                    Row {
                        ErrorSuggestion(
                            "Actualizando Datos ...",
                            isError = false,
                            isDark = false
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = showUpdatingMessage,
                enter = fadeIn(),
                exit = fadeOut()
            ) {}
        }
    }

    LaunchedEffect(Unit) {
        delay(4000) // Delay of 2 seconds
        showSuccessMessage = false
        showUpdatingMessage = true
        delay(2000)
        showUpdatingMessage = false
        //Reset the state
        userPGViewModel.clearInsertResult()
        empresaScreenClicked()
    }
}

/*@Composable
fun MsgError(
    msg: String,
    userPGViewModel: UserPGViewModel,
    empresaScreenClicked: () -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        if (msg.contains("End of input at")) {
            MsgInsert(
                userPGViewModel,
                empresaScreenClicked
            )
        }
        else {
            Row() { ErrorSuggestion("Error PG: $msg", isDark = false) }
        }
    }
}*/

@Composable
fun MsgError(msg: String, ){
    Box(
        modifier = Modifier.fillMaxSize()
    ) { Row() { ErrorSuggestion("Error PG: $msg", isDark = false) } }
}






