package com.azul.azulVentas.ui.presentation.empresa.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azul.azulVentas.R
import com.azul.azulVentas.ui.theme.DarkTextColor
import kotlinx.coroutines.launch


@Composable
fun EmpresaScreenTemplate(
    modifier: Modifier = Modifier,
    backgroundGradient: Array<Pair<Float, Color>>,
    title: String,
)
{
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

    var nitEmpresa by remember { mutableStateOf("") }
    var nomEmpresa by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var departamento by remember { mutableStateOf("") }
    var picEmpresa by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var repLegal by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }

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
        Message( title = title )

        Spacer(modifier = Modifier.height(8.dp))
        nitEmpresa = empresaTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            leadingIconRes = R.drawable.ic_business,
            placeholderText = "Nít",
            keyBoardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(8.dp))
        nomEmpresa = empresaTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            leadingIconRes = R.drawable.ic_business,
            placeholderText = "Nombre Empresa",
            keyBoardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(8.dp))
        direccion = empresaTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            leadingIconRes = R.drawable.ic_location,
            placeholderText = "Dirección",
            keyBoardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(8.dp))
        ciudad = empresaTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            leadingIconRes = R.drawable.ic_business,
            placeholderText = "Ciudad",
            keyBoardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(8.dp))
        departamento = empresaTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            leadingIconRes = R.drawable.ic_business,
            placeholderText = "Departamento",
            keyBoardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(8.dp))
        picEmpresa = empresaTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            leadingIconRes = R.drawable.ic_image,
            placeholderText = "Ruta Imagen",
            keyBoardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(8.dp))
        email = empresaTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            leadingIconRes = R.drawable.ic_email,
            placeholderText = "Email",
            keyBoardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(8.dp))
        repLegal = empresaTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            leadingIconRes = R.drawable.ic_person,
            placeholderText = "Nombre Representante",
            keyBoardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(8.dp))
        telefono = empresaTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            leadingIconRes = R.drawable.ic_phone,
            placeholderText = "Teléfono",
            keyBoardType = KeyboardType.Phone,
            imeAction = ImeAction.Next
        )
    }
}

@Composable
private fun Message(
    modifier: Modifier = Modifier,
    title: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            //color = MaterialTheme.colorScheme.primary,
            fontSize = 26.sp,
            fontWeight = FontWeight(700)
        )

    }
}

@Composable
private fun empresaTextField(
    modifier: Modifier = Modifier,
    @DrawableRes leadingIconRes: Int,
    placeholderText: String,
    keyBoardType: KeyboardType,
    imeAction: ImeAction
): String {
    var nit by remember { mutableStateOf("") }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp),
        value = nit,
        onValueChange = { nit = it },
        singleLine = true,
        shape = RoundedCornerShape(percent = 50),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedTextColor = DarkTextColor,
            unfocusedTextColor = DarkTextColor,
            unfocusedPlaceholderColor = DarkTextColor,
            focusedPlaceholderColor = DarkTextColor,
            focusedLeadingIconColor = DarkTextColor,
            unfocusedLeadingIconColor = DarkTextColor,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Medium
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyBoardType,
            imeAction = imeAction
        ),
        leadingIcon = {
            Icon(
                painter = painterResource(leadingIconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        label = { Text(text = placeholderText) },
        placeholder = { Text(text = placeholderText) }
    )

    return nit
}