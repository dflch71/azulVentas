package com.azul.azulVentas.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azul.azulVentas.R
import com.azul.azulVentas.ui.theme.DarkTextColor

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    @DrawableRes leadingIconRes: Int,
    label: String,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation,
    errorState: MutableState<Boolean>,
    imeAction: ImeAction,
    onChanged: (TextFieldValue) -> Unit,
    lengthChar: Int
) {
    //state
    var selectedText by remember { mutableStateOf(TextFieldValue("")) }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp),
        value = selectedText,
        onValueChange = { newText ->
            if (newText.text.length <= lengthChar) {
                selectedText = newText
                onChanged(newText)
            }
        },
        placeholder = { Text(text = placeholder) },
        label = { Text(text = label) },
        shape = RoundedCornerShape(percent = 50),
        leadingIcon = {
            Icon(
                painter = painterResource(leadingIconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        singleLine = true,
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
            unfocusedContainerColor = Color.White,
            errorLeadingIconColor = Color.Red
        ),
        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType, imeAction = imeAction),
        isError = errorState.value
    )
}

@Composable
fun ReadTextField(
    modifier: Modifier = Modifier,
    @DrawableRes leadingIconRes: Int,
    label: String,
    textValue: String,
) {
    //state
    var selectedText by remember { mutableStateOf(TextFieldValue(textValue)) }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp),
        value = selectedText,
        onValueChange = { },
        label = { Text(text = label) },
        shape = RoundedCornerShape(percent = 50),
        leadingIcon = {
            Icon(
                painter = painterResource(leadingIconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedContainerColor = Color(red = 216, green = 236, blue = 250),
            unfocusedContainerColor = Color(red = 216, green = 219, blue = 236),
        ),
        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
        readOnly = true
    )
}

@Composable
fun ActionTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    @DrawableRes leadingIconRes: Int,
    @DrawableRes trailingIconRes: Int,
    label: String,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation,
    errorState: MutableState<Boolean>,
    imeAction: ImeAction,
    onChanged: (TextFieldValue) -> Unit,
    lengthChar: Int,
    onClicked: () -> Unit
) {
    //state
    var selectedText by remember { mutableStateOf(TextFieldValue("")) }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp),
        value = selectedText,
        onValueChange = { newText ->
            if (newText.text.length <= lengthChar) {
                selectedText = newText
                onChanged(newText)
            }
        },
        placeholder = { Text(text = placeholder) },
        label = { Text(text = label) },
        shape = RoundedCornerShape(percent = 50),
        leadingIcon = {
            Icon(
                painter = painterResource(leadingIconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },

        trailingIcon = {
            IconButton(
                onClick = { onClicked() }
            ) {
                Icon(
                    painter = painterResource(trailingIconRes),
                    contentDescription = "Show password",
                    modifier = Modifier
                        //.size(64.dp)
                        .padding(end = 16.dp)
                )
            }
        },

        singleLine = true,
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
            unfocusedContainerColor = Color.White,
            errorLeadingIconColor = Color.Red
        ),
        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType, imeAction = imeAction),
        isError = errorState.value
    )
}

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    @DrawableRes leadingIconRes: Int,
    @DrawableRes trailingIconRes: Int,
    label: String,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation,
    errorState: MutableState<Boolean>,
    imeAction: ImeAction,
    onChanged: (TextFieldValue) -> Unit,
    lengthChar: Int,
    onClicked: () -> Unit
) {
    //state
    var selectedText by remember { mutableStateOf(TextFieldValue("")) }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp),
        value = selectedText,
        onValueChange = { newText ->
            if (newText.text.length <= lengthChar) {
                selectedText = newText
                onChanged(newText)
            }
        },
        placeholder = { Text(text = placeholder) },
        label = { Text(text = label) },
        shape = RoundedCornerShape(percent = 50),
        leadingIcon = {
            Icon(
                painter = painterResource(leadingIconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },

        trailingIcon = {
            IconButton(
                onClick = { onClicked() }
            ) {
                Icon(
                    painter = painterResource(trailingIconRes),
                    contentDescription = "Show password",
                    modifier = Modifier
                        //.size(64.dp)
                        .padding(end = 16.dp)
                )
            }
        },

        singleLine = true,
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
            unfocusedContainerColor = Color.White,
            errorLeadingIconColor = Color.Red
        ),
        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onSearch = {
                onClicked()
            }
        ),
        isError = errorState.value
    )
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    @DrawableRes leadingIconRes: Int,
    @DrawableRes visibleIconRes: Int,
    @DrawableRes visibleOffIconRes: Int,
    label: String,
    keyboardType: KeyboardType,
    errorState: MutableState<Boolean>,
    imeAction: ImeAction,
    onChanged: (TextFieldValue) -> Unit,
    lengthChar: Int
) {
    //state
    var selectedText by remember { mutableStateOf(TextFieldValue("")) }
    var isVisible by remember { mutableStateOf(false) }

    // Determinar si el texto tiene más de 6 caracteres
    val isValidLength = selectedText.text.length > 5
    val backgroundColor = if (selectedText.text.length > 5) Color.White else Color(red = 253, green = 237, blue = 236)

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp),
        value = selectedText,
        onValueChange = { newText ->
            if (newText.text.length <= lengthChar) {
                selectedText = newText
                onChanged(newText)
            }
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
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = Color.White,
            errorLeadingIconColor = Color.Red
        ),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Medium,
            color = if (!isValidLength) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        leadingIcon = {
            Icon(
                painter = painterResource(leadingIconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        trailingIcon = {
            val image = if (isVisible) visibleIconRes else visibleOffIconRes

            IconButton(
                onClick = {isVisible = !isVisible}) {
                Icon(
                    painter = painterResource(image),
                    contentDescription = "Show password",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 8.dp),
                    tint = if (errorState.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        },

        isError = errorState.value,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
    )
}

@Composable
fun ErrorSuggestion(message: String, isError: Boolean = true) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 10.dp,),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = if (isError) R.drawable.ic_error else R.drawable.ic_success), contentDescription = "Error Icon")
        Text(text = message, color = MaterialTheme.colorScheme.inverseOnSurface, fontSize = 14.sp)
    }
}
