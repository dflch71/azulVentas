package com.azul.azulVentas.ui.presentation.registration.comoponent

import androidx.annotation.DrawableRes
import android.util.Patterns
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.azul.azulVentas.R
import com.azul.azulVentas.ui.components.ActionButton
import com.azul.azulVentas.ui.presentation.login.viewmodel.AuthViewModel
import com.azul.azulVentas.ui.presentation.registration.viewmodel.RegisterViewModel
import com.azul.azulVentas.ui.theme.DarkTextColor
import com.azul.azulVentas.ui.theme.PrimaryPink
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreenTemplate(
    registerViewModel: RegisterViewModel,
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
    onSecondaryActionButtonClicked: () -> Unit

) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        email = emailTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            placeholderText = "You email",
            keyBoardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        password = passwordTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            leadingIconRes = R.drawable.ic_key,
            visibleIconRes = R.drawable.ic_visibility,
            visibleOffIconRes = R.drawable.ic_visibility_off,
            placeholderText = "Password",
            keyBoardType = KeyboardType.Password,
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
                            // Login exitoso, llama a onLoginSuccess
                            //onLoginSuccess()
                        } else {
                            errorMessage = "Login failed. Try again."
                        }
                    }

            },
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
        placeholder = { Text(text = placeholderText) }
    )

    if (!isValidEmail) {
        Text(
            text = "Invalid email format",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 4.dp)
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
                    tint = if (!isValidPassword) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.background
                )
            }

        },

        isError = !isValidPassword,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        placeholder = { Text(text = placeholderText) }
    )

    if (!isValidPassword) {
        Text(
            text = "length >= 6",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 4.dp)
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