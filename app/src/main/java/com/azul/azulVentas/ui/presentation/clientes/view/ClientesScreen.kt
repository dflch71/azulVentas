package com.azul.azulVentas.ui.presentation.clientes.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.azul.azulVentas.ui.presentation.clientes.model.ClienteModel
import com.azul.azulVentas.ui.presentation.clientes.viewmodel.ClientesViewModel


@Composable
fun ClientesScreen(
    clientesViewModel: ClientesViewModel
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<ClientesUiState>(
        initialValue = ClientesUiState.Loading,
        key1 = lifecycle,
        key2 = clientesViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            clientesViewModel.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is ClientesUiState.Error -> {}
        ClientesUiState.Loading -> { CircularProgressIndicator() }
        is ClientesUiState.Success -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                FabDialog(
                    Modifier.align(Alignment.BottomEnd),
                    clientesViewModel
                )
                ClientesList( (uiState as ClientesUiState.Success).clientes, clientesViewModel )
            }
        }
    }
}

@Composable
private fun FabDialog(modifier: Modifier, clientesViewModel: ClientesViewModel) {

    val showDialog: Boolean by clientesViewModel.showDialog.observeAsState(initial = false)

    if (showDialog) {
        AddClienteDialog (
            onTipoDocumentoAdd = { clientesViewModel.onTipoDocumentoCreated(it) },
            onNumeroDocumentoAdd = { clientesViewModel.onNumeroDocumentoCreated(it) },
            onNameAdd = { clientesViewModel.onNameCreated(it) },
            onLastNameAdd = { clientesViewModel.onLastNameCreated(it) },
            onPhoneAdd = { clientesViewModel.onPhoneCreated(it) },
            onEmailAdd = { clientesViewModel.onEmailCreated(it) },

            onClientAdd = {
                clientesViewModel.onclienteCreated(
                    tipoDocumento = it.tipoDocumento,
                    numeroDocumento = it.numeroDocumento,
                    name = it.nombre,
                    lastName = it.apellido,
                    phone = it.telefono,
                    email = it.email
                )
            },
            onDismissRequest = { clientesViewModel.onDialogClose() },
        )
    }

    FloatingActionButton(
        onClick = { clientesViewModel.onDialogShow() },
        modifier = modifier
            .padding(16.dp)
            .zIndex(1f)
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = null)
    }
}

@Composable
private fun UpdDialog(clientesViewModel: ClientesViewModel, clienteModel: ClienteModel) {

    val showDialogUpd: Boolean by clientesViewModel.showDialogUpd.observeAsState(initial = false)

    if (showDialogUpd) {
        UpdClienteDialog (

            onTipoDocumentoUpd = { clientesViewModel.onTipoDocumentoCreated(it) },
            onNumeroDocumentoUpd = { clientesViewModel.onNumeroDocumentoCreated(it) },
            onNameUpd = { clientesViewModel.onNameCreated(it) },
            onLastNameUpd = { clientesViewModel.onLastNameCreated(it) },
            onPhoneUpd = { clientesViewModel.onPhoneCreated(it) },
            onEmailUpd = { clientesViewModel.onEmailCreated(it) },

            onClientUpd = {
                clientesViewModel.onclienteUpdate(
                    id = it.id,
                    tipoDocumento = it.tipoDocumento,
                    numeroDocumento = it.numeroDocumento,
                    name = it.nombre,
                    lastName = it.apellido,
                    phone = it.telefono,
                    email = it.email
                )
            },
            onDismissRequest = { clientesViewModel.onDialogCloseUpd() },
            clienteModel = clienteModel
        )
    }
}

@Composable
fun ClientesList(clientes: List<ClienteModel>, clientesViewModel: ClientesViewModel) {

    var selectedClient by remember { mutableStateOf<ClienteModel?>(null) }

    selectedClient?.let {
        clientesViewModel.onDialogShowUpd()
        UpdDialog(clientesViewModel, it)
    }

    LazyColumn {
        items(clientes, key = { it.id }) { client ->
            ItemClient(client, clientesViewModel, onClick = { selectedClient = client })
        }
    }

}


@Composable
fun ItemClient(clienteModel: ClienteModel, clientesViewModel: ClientesViewModel, onClick: () -> Unit) {
    Column (
        Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onLongPress = {
                        clientesViewModel.onClienteRemove(clienteModel)
                    })
                }
                .clickable(onClick = onClick),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)

        ){
            Text(text = clienteModel.nombre+" "+clienteModel.apellido,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            )

            Text(text = clienteModel.telefono,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            )

        }

    }
}

@Composable
fun AddClienteDialog(
    onTipoDocumentoAdd: (String) -> Unit = {},
    onNumeroDocumentoAdd: (String) -> Unit = {},
    onNameAdd: (String) -> Unit = {},
    onLastNameAdd: (String) -> Unit = {},
    onPhoneAdd: (String) -> Unit = {},
    onEmailAdd: (String) -> Unit = {},
    onClientAdd: (ClienteModel) -> Unit = {},
    onDismissRequest: () -> Unit
){

    var myTipoDocumento  by remember { mutableStateOf("") }
    var myNumeroDocumento  by remember { mutableStateOf("") }
    var myName  by remember { mutableStateOf("") }
    var myLastName  by remember { mutableStateOf("") }
    var myPhone by remember { mutableStateOf("") }
    var myEmail by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            modifier = Modifier
                .padding(10.dp),
            shape = RoundedCornerShape(4.dp),

        ) {
            Box (modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
            ) {

                var showDialog by remember { mutableStateOf(false) }

                Column {

                    if (showDialog) {
                        AzulVentasAlertDialog(
                            onDismiss = { showDialog = false },
                            onDismissRequest = { onDismissRequest() }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                showDialog = true
                            }
                        ){
                            Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "Crear Cliente",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        OutlinedButton(
                            onClick = {

                                onTipoDocumentoAdd(myTipoDocumento)
                                onNumeroDocumentoAdd(myNumeroDocumento)
                                onNameAdd(myName)
                                onLastNameAdd(myLastName)
                                onPhoneAdd(myPhone)
                                onEmailAdd(myEmail)

                                onClientAdd(
                                    ClienteModel(
                                        0,
                                        myTipoDocumento,
                                        myNumeroDocumento,
                                        myName,
                                        myLastName,
                                        myPhone,
                                        myEmail
                                    )
                                )

                                onDismissRequest()
                            }
                        ) {
                            Text("Guardar")
                        }

                    }

                    HorizontalDivider()
                    myTipoDocumento = TiposDocumentoMenu()
                    myNumeroDocumento = NumeroIdentificacion()
                    myName = Nombres()
                    myLastName = Apellidos()
                    myPhone = NumeroTelefono()
                    myEmail = Email()

                    Spacer(modifier = Modifier.height(20.dp))

                }
            }
        }
    }
}


@Composable
fun UpdClienteDialog(
    onTipoDocumentoUpd: (String) -> Unit = {},
    onNumeroDocumentoUpd: (String) -> Unit = {},
    onNameUpd: (String) -> Unit = {},
    onLastNameUpd: (String) -> Unit = {},
    onPhoneUpd: (String) -> Unit = {},
    onEmailUpd: (String) -> Unit = {},
    onClientUpd: (ClienteModel) -> Unit = {},
    onDismissRequest: () -> Unit,
    clienteModel: ClienteModel
){
    var myID  by remember { mutableStateOf(clienteModel.id) }
    var myTipoDocumento  by remember { mutableStateOf(clienteModel.tipoDocumento) }
    var myNumeroDocumento  by remember { mutableStateOf(clienteModel.numeroDocumento) }
    var myName  by remember { mutableStateOf(clienteModel.nombre) }
    var myLastName  by remember { mutableStateOf(clienteModel.apellido) }
    var myPhone by remember { mutableStateOf(clienteModel.telefono) }
    var myEmail by remember { mutableStateOf(clienteModel.email) }

    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            modifier = Modifier
                .padding(10.dp),
            shape = RoundedCornerShape(4.dp),

            ) {
            Box (modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
            ) {

                var showDialog by remember { mutableStateOf(false) }

                Column {

                    if (showDialog) {
                        AzulVentasAlertDialogUpd(
                            onDismiss = { showDialog = false },
                            onDismissRequest = { onDismissRequest() }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                showDialog = true
                            }
                        ){
                            Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "Modificar Cliente",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        OutlinedButton(
                            onClick = {

                                onTipoDocumentoUpd(myTipoDocumento)
                                onNumeroDocumentoUpd(myNumeroDocumento)
                                onNameUpd(myName)
                                onLastNameUpd(myLastName)
                                onPhoneUpd(myPhone)
                                onEmailUpd(myEmail)

                                onClientUpd(
                                    ClienteModel(
                                        myID,
                                        myTipoDocumento,
                                        myNumeroDocumento,
                                        myName,
                                        myLastName,
                                        myPhone,
                                        myEmail
                                    )
                                )

                                onDismissRequest()
                                showDialog = false

                            }
                        ) {
                            Text("Actualizar")
                        }

                    }

                    HorizontalDivider()
                    myTipoDocumento = TiposDocumentoMenuUpd(myTipoDocumento)
                    myNumeroDocumento = NumeroIdentificacionUpd(myNumeroDocumento)
                    myName = NombresUpd(myName)
                    myLastName = ApellidosUpd(myLastName)
                    myPhone = NumeroTelefonoUpd(myPhone)
                    myEmail = EmailUpd(myEmail)

                    Spacer(modifier = Modifier.height(20.dp))

                }
            }
        }
    }
}


@Composable
fun TiposDocumentoMenu(): String {

    val suggestions = listOf("CEDULA CIUDADANIA", "TARJETA IDENTIDAD", "PASAPORTE", "REGISTRO CIVIL", "NA")

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
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
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { expanded = !expanded })
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            readOnly = true
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
fun TiposDocumentoMenuUpd(valor: String): String {

    val suggestions = listOf("CEDULA CIUDADANIA", "TARJETA IDENTIDAD", "PASAPORTE", "REGISTRO CIVIL", "NA")

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(valor) }
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
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { expanded = !expanded })
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            readOnly = true
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
fun NumeroIdentificacion(): String {

    var selectedText by remember { mutableStateOf("") }

    Column(Modifier.padding(10.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth(),
            label = {Text("Número Identificación")},
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                Icon(Icons.Outlined.CheckCircle, contentDescription = null)
            }
        )
    }
    return selectedText
}

@Composable
fun NumeroIdentificacionUpd(valor: String): String {

    var selectedText by remember { mutableStateOf(valor) }

    Column(Modifier.padding(10.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth(),
            label = {Text("Número Identificación")},
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                Icon(Icons.Outlined.CheckCircle, contentDescription = null)
            }
        )
    }
    return selectedText
}

@Composable
fun Nombres(): String {
    var selectedText by remember { mutableStateOf("") }
    Column(Modifier.padding(10.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth(),
            label = {Text("Nombres")},
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Characters
            ),
            trailingIcon = {
                Icon(Icons.Outlined.Person, contentDescription = null)
            }
        )
    }
    return selectedText
}

@Composable
fun NombresUpd(valor: String): String {
    var selectedText by remember { mutableStateOf(valor) }
    Column(Modifier.padding(10.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth(),
            label = {Text("Nombres")},
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Characters
            ),
            trailingIcon = {
                Icon(Icons.Outlined.Person, contentDescription = null)
            }
        )
    }
    return selectedText
}

@Composable
fun Apellidos(): String {
    var selectedText by remember { mutableStateOf("") }
    Column(Modifier.padding(10.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth(),
            label = {Text("Apellidos")},
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Characters
            ),
            trailingIcon = {
                Icon(Icons.Outlined.Person, contentDescription = null)
            }
        )
    }
    return selectedText
}

@Composable
fun ApellidosUpd(valor: String): String {
    var selectedText by remember { mutableStateOf(valor) }
    Column(Modifier.padding(10.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth(),
            label = {Text("Apellidos")},
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Characters
            ),
            trailingIcon = {
                Icon(Icons.Outlined.Person, contentDescription = null)
            }
        )
    }
    return selectedText
}

@Composable
fun NumeroTelefono(): String {

    val maxChar = 10
    var selectedText by remember { mutableStateOf("") }

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
                )
            },
        )
    }
    return selectedText
}

@Composable
fun NumeroTelefonoUpd(valor: String): String {

    val maxChar = 10
    var selectedText by remember { mutableStateOf(valor) }

    Column(Modifier.padding(10.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {
                //if (selectedText.length < maxChar) { selectedText = it }
                if (it.length <= maxChar) { selectedText = it }
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {Text("Número Teléfono")},
            singleLine = true,
            maxLines = 1,
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
                )
            },
        )
    }
    return selectedText
}

@Composable
fun Email(): String {

    var selectedText by remember { mutableStateOf("") }
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
            }
        )
    }
    return selectedText
}

@Composable
fun EmailUpd(valor: String): String {

    var selectedText by remember { mutableStateOf(valor) }
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
            }
        )
    }
    return selectedText
}

@Composable
fun AzulVentasAlertDialog(
    onDismiss: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(

        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),

        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        iconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,

        onDismissRequest = {
            onDismiss()
        },

        confirmButton ={
            TextButton(
                onClick = {
                    onDismissRequest()
                }) {
                Text(text = "SI")
            }
        },

        dismissButton ={
            TextButton(
                onClick = {
                    onDismiss()
                }) {
                Text(text = "NO")
            }

        },

        title = {
            Text(text = "Crear Cliente")
        },

        text = {
            Text(text = "Esta seguro de cancelar la creación del cliente?"+'\n'+"Si acepta, se cerrará la ventana sin grabar los datos.")
        },

        icon = {
            Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = null )
        }
    )
}

@Composable
fun AzulVentasAlertDialogUpd(
    onDismiss: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(

        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),

        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        iconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,

        onDismissRequest = {
            onDismiss()
        },

        confirmButton ={
            TextButton(
                onClick = {
                    onDismissRequest()
                }) {
                Text(text = "SI")
            }
        },

        dismissButton ={
            TextButton(
                onClick = {
                    onDismiss()
                }) {
                Text(text = "NO")
            }

        },

        title = {
            Text(text = "Modificar Cliente")
        },

        text = {
            Text(text = "Esta seguro de cancelar la modificación del cliente?"+'\n'+"Si acepta, se cerrará la ventana sin grabar los datos.")
        },

        icon = {
            Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = null )
        }
    )
}




