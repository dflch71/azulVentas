package com.azul.azulVentas.ui.presentation.login.component

import android.util.Patterns
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.azul.azulVentas.R
import com.azul.azulVentas.ui.components.ActionButton
import com.azul.azulVentas.ui.presentation.login.viewmodel.AuthViewModel
import com.azul.azulVentas.ui.presentation.login.viewmodel.LoginState
import com.azul.azulVentas.ui.theme.DarkTextColor
import com.azul.azulVentas.ui.theme.PrimaryPink
import kotlinx.coroutines.launch

@Composable
fun AuthenticationScreenTemplate(
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    backgroundGradient: Array<Pair<Float, Color>>,
    @DrawableRes imgRes: Int,
    title: String,
    subtitle: String,
    mainActionButtonTitle: String,
    secondaryActionButtonTitle: String,
    mainActionButtonColors: ButtonColors,
    secondaryActionButtonColors: ButtonColors,
    actionButtonShadow: Color,
    onMainActionButtonClicked: () -> Unit,
    onSecondaryActionButtonClicked: () -> Unit,
    onLoginSuccess: () -> Unit,
    onRecoveryClicked: () -> Unit
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val loginState = authViewModel.loginState

    // UI lógica basada en el estado del login
    when (loginState) {
        is LoginState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center)
            {
                CircularProgressIndicator()
                Thread.sleep(5000)
            }
        }

        is LoginState.Success -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center)
            {
                LinearProgressIndicator()
                Thread.sleep(5000)
            }
            // Acción cuando el login sea exitoso
            onLoginSuccess()
        }

        is LoginState.Error -> errorMessage = loginState.message
        else -> {}
    }

    LaunchedEffect(keyboardHeight) { coroutineScope.launch { scrollState.scrollBy(keyboardHeight.toFloat()) } }

    Box( modifier = Modifier.fillMaxSize())
    {
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
                    .size(240.dp)
                    .padding(start = 4.dp)
            )

            Message(
                title = title,
                subtitle = subtitle
            )

            Spacer(modifier = Modifier.height(8.dp))
            email = emailTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                placeholderText = "Email",
                keyBoardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
            authViewModel.setEmail_(email)

            Spacer(modifier = Modifier.height(8.dp))
            password = passwordTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                leadingIconRes = R.drawable.ic_key,
                visibleIconRes = R.drawable.ic_visibility,
                visibleOffIconRes = R.drawable.ic_visibility_off,
                placeholderText = "Contraseña",
                keyBoardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
            authViewModel.setPassword_(password)

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Olvidé mi contraseña",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                textDecoration = TextDecoration.Underline,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.End)
                    .wrapContentWidth()
                    .padding(horizontal = 36.dp)
                    .clickable { onRecoveryClicked() },
            )

            Spacer(modifier = Modifier.height(8.dp))
            ActionButton(
                text = mainActionButtonTitle,
                isNavigationArrowVisible = true,
                onClicked =
                {
                    errorMessage = null
                    authViewModel.login(email, password)
                },
                onLongClicked = {},
                colors = mainActionButtonColors,
                shadowColor = actionButtonShadow,
                modifier = Modifier.padding(horizontal = 24.dp),
            )

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = errorMessage!!,
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
                    textAlign = TextAlign.Start,
                    maxLines = 2
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 16.dp, end = 16.dp)
            ) {
                CustomButton(
                    onClick = {},
                    iconRes = R.drawable.google,
                    description = "Google"
                )
                CustomButton(
                    onClick = {},
                    iconRes = R.drawable.facebook,
                    description = "Facebook"
                )
            }

            Separator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(30.dp)
            )

            ActionButton(
                text = secondaryActionButtonTitle,
                isNavigationArrowVisible = false,
                onClicked = onSecondaryActionButtonClicked,
                onLongClicked = {},
                colors = secondaryActionButtonColors,
                shadowColor = actionButtonShadow,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp)
            )
        }

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
private fun Separator(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DashedLine(
            modifier = Modifier.weight(weight = 1f)
        )
        Text(
            text = "Or",
            style = MaterialTheme.typography.labelMedium,
            color = Color.White
        )
        DashedLine(
            modifier = Modifier.weight(weight = 1f)
        )
    }
}

@Composable
private fun DashedLine(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        drawLine(
            color = Color.White,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f), 0f),
            cap = StrokeCap.Round,
            strokeWidth = 1.dp.toPx()
        )
    }
}

@Composable
fun CustomButton(
    onClick: () -> Unit,
    @DrawableRes iconRes: Int,
    description: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            //.fillMaxWidth()
            .height(90.dp)
            .width(150.dp)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = PrimaryPink
        )
    ) {
        Column(
            modifier = Modifier.weight(0.25f),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                painter = painterResource(id = iconRes), // Replace with your Google logo drawable
                contentDescription = null,
                tint = Color.Unspecified, // No tint needed for the logo
                modifier = Modifier.size(36.dp)
            )
            Text(
                description,
                color = Color.Black
            )
        }
    }
}