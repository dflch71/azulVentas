package com.azul.azulVentas.ui.presentation.registration.comoponent

import android.util.Patterns
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.azul.azulVentas.R
import com.azul.azulVentas.ui.components.ActionButton
import com.azul.azulVentas.ui.presentation.registration.viewmodel.RegisterViewModel
import com.azul.azulVentas.ui.theme.DarkTextColor
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreenTemplate(
    registerViewModel: RegisterViewModel,
    modifier: Modifier = Modifier,
    backgroundGradient: Array<Pair<Float, Color>>,
    @DrawableRes imgRes: Int,
    title: String,
    subtitle: String,
    secondaryActionButtonTitle: String,
    secondaryActionButtonColors: ButtonColors,
    actionButtonShadow: Color
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var identification by remember { mutableStateOf("") }
    var companyName by remember { mutableStateOf("") }

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
        Image(
            painter = painterResource(imgRes),
            contentDescription = null,
            modifier = Modifier
                .size(280.dp)
                .padding(start = 8.dp)
        )

        Message(
            title = title,
            subtitle = subtitle
        )

        Spacer(modifier = Modifier.height(8.dp))
        email = emailTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            placeholderText = "You email",
            keyBoardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(8.dp))
        password = passwordTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            leadingIconRes = R.drawable.ic_key,
            visibleIconRes = R.drawable.ic_visibility,
            visibleOffIconRes = R.drawable.ic_visibility_off,
            placeholderText = "Password",
            keyBoardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(8.dp))
        identification = identificationTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            leadingIconRes = R.drawable.ic_person,
            placeholderText = "Identification",
            keyBoardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(8.dp))
        companyName = companyTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            leadingIconRes = R.drawable.ic_business,
            placeholderText = "Company Name",
            keyBoardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        )

        Spacer(modifier = Modifier.height(10.dp))
        ActionButton(
            text = secondaryActionButtonTitle,
            isNavigationArrowVisible = false,
            //onClicked = onSecondaryActionButtonClicked,
            onClicked = {

                    errorMessage = null
                    registerViewModel.register(email, password) { user ->
                        if (user != null) {
                            //Login exitoso, llama a onLoginSuccess
                            //onLoginSuccess()
                        } else {
                            errorMessage = "Register Failed. Try Again."
                        }
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
private fun Message(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = subtitle,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
fun emailTextField(
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    placeholderText: String,
    keyBoardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction
): String {
    var email by remember { mutableStateOf("") }
    var isValidEmail by remember { mutableStateOf(true) }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp),
        value = email,
        onValueChange = { email = it
            isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        },
        visualTransformation = visualTransformation,
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
                imageVector = Icons.Outlined.Email,
                contentDescription = "Email Icon",
                modifier = Modifier.size(24.dp)
            )
        },
        label = { Text(text = placeholderText) },
        placeholder = { Text(text = placeholderText) }
    )

    if (!isValidEmail) {
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Formato correo: abc@xyz.com",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .background(
                    MaterialTheme.colorScheme.tertiaryContainer,
                    RoundedCornerShape(24.dp)
                )
                .fillMaxWidth()
                .padding(start = 8.dp),
            textAlign = TextAlign.Center
        )
    }
    return email


}

@Composable
private fun passwordTextField(
    modifier: Modifier = Modifier,
    @DrawableRes leadingIconRes: Int,
    @DrawableRes visibleIconRes: Int,
    @DrawableRes visibleOffIconRes: Int,
    placeholderText: String,
    keyBoardType: KeyboardType = KeyboardType.Password,
    imeAction: ImeAction
): String {
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isValidPassword by remember { mutableStateOf(true) }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp),
        value = password,
        onValueChange = {
            password = it
            isValidPassword = it.length >= 6 // Example validation: at least 6 characters
        },
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
        trailingIcon = {
            val image = if (passwordVisible) visibleIconRes else visibleOffIconRes

            IconButton(
                onClick = {passwordVisible = !passwordVisible}) {
                Icon(
                    painter = painterResource(image),
                    contentDescription = "Show password",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 8.dp),
                    tint = if (!isValidPassword) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        },

        isError = !isValidPassword,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        label = { Text(text = placeholderText) },
        placeholder = { Text(text = placeholderText) }
    )

    if (!isValidPassword) {
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Contraseña: >= Mínimo 6 caracteres",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .background(
                    MaterialTheme.colorScheme.tertiaryContainer,
                    RoundedCornerShape(24.dp)
                )
                .fillMaxWidth()
                .padding(start = 8.dp),
            textAlign = TextAlign.Center
        )
    }

    return password
}

@Composable
private fun identificationTextField(
    modifier: Modifier = Modifier,
    @DrawableRes leadingIconRes: Int,
    placeholderText: String,
    keyBoardType: KeyboardType,
    imeAction: ImeAction
): String {
    var identification by remember { mutableStateOf("") }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp),
        value = identification,
        onValueChange = {
            identification = it
        },
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

    return identification
}

@Composable
private fun companyTextField(
    modifier: Modifier = Modifier,
    @DrawableRes leadingIconRes: Int,
    placeholderText: String,
    keyBoardType: KeyboardType,
    imeAction: ImeAction
): String {
    var companyName by remember { mutableStateOf("") }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp),
        value = companyName,
        onValueChange = {
            companyName = it
        },
        singleLine = false,
        maxLines = 2,
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

    return companyName
}


