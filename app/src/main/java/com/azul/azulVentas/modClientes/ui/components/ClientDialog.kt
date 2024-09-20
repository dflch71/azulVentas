package com.azul.azulVentas.modClientes.ui.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.azul.azulVentas.modClientes.ui.ClientesViewModel
import com.azul.azulVentas.modClientes.ui.model.ClienteModel

@Composable
fun ClientDialog(

    entity: ClienteModel?,
    viewModel: ClientesViewModel,
    onDismiss: () -> Unit

) {

    var myID                by remember { mutableStateOf(entity?.id ?: "") }
    var myTipoDocumento     by remember { mutableStateOf(entity?.tipoDocumento ?: "") }
    var myNumeroDocumento   by remember { mutableStateOf(entity?.numeroDocumento ?: "") }
    var myName              by remember { mutableStateOf(entity?.nombre ?: "") }
    var myLastName          by remember { mutableStateOf(entity?.apellido ?: "") }
    var myPhone             by remember { mutableStateOf(entity?.telefono ?: "") }
    var myEmail             by remember { mutableStateOf(entity?.email ?: "") }

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
        )
    ){
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = if (entity == null) "CREAR CLIENTE" else "MODIFICAR CLIENTE",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp)
                )

                myTipoDocumento = TiposDocumento(myTipoDocumento.trim())
                myNumeroDocumento = NumeroIdentificacion(myNumeroDocumento.trim())
                myName = Nombres(myName.trim())
                myLastName = Apellidos(myLastName.trim())
                myPhone = NumeroTelefono(myPhone.trim())
                myEmail = Email(myEmail.trim())

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    val openCancelDialog = remember { mutableStateOf(false) }

                    if (openCancelDialog.value) {
                        CancelDialog(
                            onDismissRequest = { openCancelDialog.value = false },
                            onConfirmation = {
                                openCancelDialog.value = false
                                onDismiss()
                            },
                            dialogTitle = "CANCELAR",
                            dialogText = "Esta acción no se puede deshacer y no guardara los cambios.\n\n¿Desea continuar?",
                            icon = Icons.Default.Info
                        )
                    }

                    TextButton(onClick = {
                        openCancelDialog.value = true
                    }) { Text("CANCELAR") }

                    Spacer(modifier = Modifier.width(8.dp))

                    val context = LocalContext.current

                    TextButton(onClick = {
                        if (myTipoDocumento.isEmpty() || myNumeroDocumento.isEmpty() || myName.isEmpty() ||
                            myLastName.isEmpty() || myPhone.isEmpty() || myEmail.isEmpty()) {
                            Toast.makeText(context, ">> Todos los campos son obligatorios\n>> El número de TELÉFONO debe contener 10 dígitos\n>> El EMAIL debe ser válido _@_.com", Toast.LENGTH_SHORT).show()
                            return@TextButton
                        }

                        if (entity == null) {
                            viewModel.onclienteCreated(myTipoDocumento, myNumeroDocumento, myName, myLastName, myPhone, myEmail)
                        } else {
                            viewModel.onclienteUpdate(myID as Int, myTipoDocumento, myNumeroDocumento, myName, myLastName, myPhone, myEmail)
                        }

                        onDismiss()
                    }) {
                        Text(if (entity == null) "GUARDAR" else "ACTUALIZAR")
                    }

                }
            }
        }
    }
}

@Composable
fun TiposDocumento(tipoDoc: String): String {

    val suggestions = listOf("CEDULA CIUDADANIA", "TARJETA IDENTIDAD", "PASAPORTE", "REGISTRO CIVIL", "NA")

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(tipoDoc) }
    var textfieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(10.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            label = {Text("Tipo Documento")},
            isError = selectedText.isEmpty(),
            singleLine = true,
            maxLines = 1,
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { expanded = !expanded })
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorLabelColor = MaterialTheme.colorScheme.primary,
                errorTrailingIconColor = MaterialTheme.colorScheme.primary
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textfieldSize.width.toDp()})
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = label
                        expanded = false
                    },
                    text = { Text(text = label) }
                )
            }
        }
    }

    return selectedText
}

@Composable
fun NumeroIdentificacion(numID: String): String {

    var selectedText by remember { mutableStateOf(numID) }

    Column(Modifier.padding(10.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth(),
            label = {Text("Número Identificación")},
            singleLine = true,
            maxLines = 1,
            isError = selectedText.isEmpty(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                Icon(Icons.Outlined.CheckCircle, contentDescription = null)
            },
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorLabelColor = MaterialTheme.colorScheme.primary,
                errorTrailingIconColor = MaterialTheme.colorScheme.primary
            )
        )
    }
    return selectedText
}

@Composable
fun Nombres(nombre: String): String {
    var selectedText by remember { mutableStateOf(nombre) }
    Column(Modifier.padding(10.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth(),
            label = {Text("Nombres")},
            singleLine = true,
            maxLines = 1,
            isError = selectedText.isEmpty(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Characters
            ),
            trailingIcon = {
                Icon(Icons.Outlined.Person, contentDescription = null)
            },
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorLabelColor = MaterialTheme.colorScheme.primary,
                errorTrailingIconColor = MaterialTheme.colorScheme.primary
            )
        )
    }
    return selectedText
}

@Composable
fun Apellidos(apellido: String): String {
    var selectedText by remember { mutableStateOf(apellido) }
    Column(Modifier.padding(10.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth(),
            label = {Text("Apellidos")},
            singleLine = true,
            maxLines = 1,
            isError = selectedText.isEmpty(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Characters
            ),
            trailingIcon = {
                Icon(Icons.Outlined.Person, contentDescription = null)
            },
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorLabelColor = MaterialTheme.colorScheme.primary,
                errorTrailingIconColor = MaterialTheme.colorScheme.primary
            )
        )
    }
    return selectedText
}

@Composable
fun NumeroTelefono(numTel: String): String {

    val maxChar = 10
    var selectedText by remember { mutableStateOf(numTel) }
    var isPhoneValid = remember(selectedText) { selectedText.matches(Regex("[0-9]{10}")) } && selectedText.length <= maxChar

    Column(Modifier.padding(10.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {
                if (it.length <= maxChar) { selectedText = it }
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {Text("Número Teléfono")},
            singleLine = true,
            maxLines = 1,
            isError = !isPhoneValid,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                Icon(Icons.Outlined.Phone, contentDescription = null)
            },
            supportingText = {
                Text(
                    text = "${selectedText.length} / $maxChar",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorLabelColor = MaterialTheme.colorScheme.primary,
                errorTrailingIconColor = MaterialTheme.colorScheme.primary
            )
        )
    }
    return if (isPhoneValid) selectedText else ""
}

@Composable
fun Email(correo: String): String {

    var selectedText by remember { mutableStateOf(correo) }
    val isEmailValid = remember(selectedText) { selectedText.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) }

    Column(Modifier.padding(10.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth(),
            label = {Text("Email")},
            singleLine = true,
            maxLines = 1,
            isError = !isEmailValid,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                Icon(Icons.Outlined.Email, contentDescription = null)
            },
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorLabelColor = MaterialTheme.colorScheme.primary,
                errorTrailingIconColor = MaterialTheme.colorScheme.primary
            )
        )

        if (!isEmailValid) {
            Text(
                text = "Email no valido",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

    return if (isEmailValid) selectedText else ""
}

