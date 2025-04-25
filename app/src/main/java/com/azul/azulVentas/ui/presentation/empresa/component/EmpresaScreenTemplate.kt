package com.azul.azulVentas.ui.presentation.empresa.component

import android.icu.lang.UCharacter.toUpperCase
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.azul.azulVentas.ui.presentation.empresa.viewmodel.EmpresaViewModel
import com.azul.azulVentas.ui.theme.DarkTextColor
import com.azul.azulVentas.ui.theme.PrimaryYellowDark
import com.azul.azulVentas.ui.theme.PrimaryYellowMid
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun EmpresaScreenTemplate(
    empresaViewModel: EmpresaViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    backgroundGradient: Array<Pair<Float, Color>>,
    loginScreenClicked: () -> Unit
)
{
    val estadoRegistro by empresaViewModel.estadoRegistro.observeAsState()

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

    var nitEmpresa by remember { mutableStateOf(TextFieldValue("")) }
    var nomEmpresa by remember { mutableStateOf(TextFieldValue("")) }
    var direccion by remember { mutableStateOf(TextFieldValue("")) }
    var ciudad by remember { mutableStateOf(TextFieldValue("")) }
    var departamento by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var repLegal by remember { mutableStateOf(TextFieldValue("")) }
    var telefono by remember { mutableStateOf(TextFieldValue("")) }

    val nitErrorState = remember { mutableStateOf(false) }
    val nameErrorState = remember { mutableStateOf(false) }
    val adressErrorState = remember { mutableStateOf(false) }
    val cityErrorState = remember { mutableStateOf(false) }
    val departamentErrorState = remember { mutableStateOf(false) }
    val emailErrorState = remember { mutableStateOf(false) }
    val personErrorState = remember { mutableStateOf(false) }
    val phoneErrorState = remember { mutableStateOf(false) }

    val animate = remember { mutableStateOf(true) }

    LaunchedEffect(keyboardHeight) {
        coroutineScope.launch { scrollState.scrollBy(keyboardHeight.toFloat()) }
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
                    text = "Registrar Empresa",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(700)
                )
            }
        }

        CustomTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            placeholder = "Nít - ID",
            leadingIconRes = R.drawable.ic_identification,
            label = "Nít - ID",
            errorState = nitErrorState,
            keyboardType = KeyboardType.Number,
            visualTransformation = VisualTransformation.None,
            imeAction = ImeAction.Next,
            onChanged = { newNit -> nitEmpresa = newNit },
            lengthChar = 20
        )

        Spacer(modifier = Modifier.height(4.dp))
        CustomTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            placeholder = "Nombre Empresa",
            leadingIconRes = R.drawable.ic_factory,
            label = "Nombre Empresa",
            errorState = nameErrorState,
            keyboardType = KeyboardType.Text,
            visualTransformation = VisualTransformation.None,
            imeAction = ImeAction.Next,
            onChanged = { newNomEmpresa -> nomEmpresa = newNomEmpresa },
            lengthChar = 100
        )

        Spacer(modifier = Modifier.height(4.dp))
        CustomTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            placeholder = "Dirección",
            leadingIconRes = R.drawable.ic_location,
            label = "Dirección",
            errorState = adressErrorState,
            keyboardType = KeyboardType.Text,
            visualTransformation = VisualTransformation.None,
            imeAction = ImeAction.Next,
            onChanged = { newDireccion -> direccion = newDireccion },
            lengthChar = 100
        )

        Spacer(modifier = Modifier.height(4.dp))
        CustomTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            placeholder = "Ciudad",
            leadingIconRes = R.drawable.ic_city,
            label = "Ciudad",
            errorState = cityErrorState,
            keyboardType = KeyboardType.Text,
            visualTransformation = VisualTransformation.None,
            imeAction = ImeAction.Next,
            onChanged = { newCiudad -> ciudad = newCiudad},
            lengthChar = 100
        )

        Spacer(modifier = Modifier.height(4.dp))
        CustomTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            placeholder = "Departamento",
            leadingIconRes = R.drawable.ic_map,
            label = "Departamento",
            errorState = departamentErrorState,
            keyboardType = KeyboardType.Text,
            visualTransformation = VisualTransformation.None,
            imeAction = ImeAction.Next,
            onChanged = { newDepartamento -> departamento = newDepartamento},
            lengthChar = 100
        )

        Spacer(modifier = Modifier.height(4.dp))
        CustomTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            placeholder = "example@email.com",
            leadingIconRes = R.drawable.ic_email,
            label = "Email",
            errorState = emailErrorState,
            keyboardType = KeyboardType.Email,
            visualTransformation = VisualTransformation.None,
            imeAction = ImeAction.Next,
            onChanged = { newEmail -> email = newEmail },
            lengthChar = 100
        )

        Spacer(modifier = Modifier.height(4.dp))
        CustomTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            placeholder = "Representante",
            leadingIconRes = R.drawable.ic_person,
            label = "Representante",
            errorState = personErrorState,
            keyboardType = KeyboardType.Text,
            visualTransformation = VisualTransformation.None,
            imeAction = ImeAction.Next,
            onChanged = { newRepLegal -> repLegal = newRepLegal },
            lengthChar = 100
        )

        Spacer(modifier = Modifier.height(4.dp))
        CustomTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            placeholder = "Teléfono",
            leadingIconRes = R.drawable.ic_phone,
            label = "Teléfono",
            errorState = phoneErrorState,
            keyboardType = KeyboardType.Phone,
            visualTransformation = VisualTransformation.None,
            imeAction = ImeAction.Done,
            onChanged = { newTelefono -> telefono = newTelefono },
            lengthChar = 10
        )

        Spacer(modifier = Modifier.height(10.dp))
        if (emailErrorState.value) {
            ErrorSuggestion("EMAIL inválido (example@email.com).")
        }
        if (phoneErrorState.value) {
            Row() { ErrorSuggestion("TELÉFONO inválido (10 dígitos).") }
        }
        if (nitErrorState.value || nameErrorState.value || adressErrorState.value || cityErrorState.value ||
            departamentErrorState.value || personErrorState.value) {
            Row() {
                ErrorSuggestion("Valor requerido o incompleto.")
            }
        }

        estadoRegistro?.let { result ->
            when {
                result.isSuccess -> {
                        Row() { ErrorSuggestion("EMPRESA registrada exitosamente", isError = false) }
                        Row() { ErrorSuggestion("Actualizando Datos ...", isError = false) }

                        //Mostrar mensaje de actualización por 4 segundos
                        coroutineScope.launch {
                            delay(4000L)
                            empresaViewModel.limpiarEstadoRegistro()
                            loginScreenClicked()
                        }

                }
                result.isFailure -> {
                    Row() {
                        ErrorSuggestion("Error: ${result.exceptionOrNull()?.message} ${nitEmpresa.text}")
                    }
                }
                else -> {}
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        ActionButton(
            text = "Registrar",
            isNavigationArrowVisible = true,
            onClicked = {
                val isNitValid = !nitEmpresa.text.isEmpty() && nitEmpresa.text.length > 5
                val isNameValid = !nomEmpresa.text.isEmpty() && nomEmpresa.text.length > 3
                val isAdressValid = !direccion.text.isEmpty() && direccion.text.length > 2
                val isCityValid = !ciudad.text.isEmpty() && ciudad.text.length > 3
                val isDepartamentValid = !departamento.text.isEmpty() && departamento.text.length > 3
                val patternEmail = Patterns.EMAIL_ADDRESS
                val isEmailValid = patternEmail.matcher(email.text).matches() && !email.text.isEmpty()
                val isPersonValid = !repLegal.text.isEmpty() && repLegal.text.length > 3
                val patternTelefono = Patterns.PHONE  //Regex("[0-9]{10}")
                val isTelefonoValid =  patternTelefono.matcher(telefono.text).matches() && !telefono.text.isEmpty() && telefono.text.length == 10

                nitErrorState.value = !isNitValid
                nameErrorState.value = !isNameValid
                adressErrorState.value = !isAdressValid
                cityErrorState.value = !isCityValid
                departamentErrorState.value = !isDepartamentValid
                emailErrorState.value = !isEmailValid
                personErrorState.value = !isPersonValid
                phoneErrorState.value = !isTelefonoValid

                val isRequiredFields = isNitValid && isNameValid && isAdressValid && isCityValid && isDepartamentValid && isPersonValid
                if (isEmailValid && isTelefonoValid && isRequiredFields) {
                    animate.value = !animate.value
                    empresaViewModel.registrarEmpresa(
                        toUpperCase(nitEmpresa.text),
                        toUpperCase(nomEmpresa.text),
                        toUpperCase(direccion.text),
                        toUpperCase(ciudad.text),
                        toUpperCase(departamento.text),
                        "gs://azul-invoice.appspot.com/azulSoluciones.png",
                        email.text,
                        toUpperCase(repLegal.text),
                        toUpperCase(telefono.text)
                    )
                }
            },
            onLongClicked = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryYellowDark,
                contentColor = DarkTextColor
            ),
            shadowColor = PrimaryYellowDark,
            modifier = Modifier.padding(top = 36.dp, bottom = 4.dp, start = 24.dp, end = 24.dp)
        )

        Spacer(modifier = Modifier.weight(weight = 1f))
        ActionButton(
            text = "Cancelar",
            isNavigationArrowVisible = true,
            onClicked = { loginScreenClicked() },
            onLongClicked = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryYellowMid,
                contentColor = DarkTextColor
            ),
            shadowColor = PrimaryYellowDark,
            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp, start = 24.dp, end = 24.dp)
        )
    }
}

