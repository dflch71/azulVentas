package com.azul.azulVentas.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.azul.azulVentas.ui.presentation.clientes.viewmodel.ClientesViewModel
import com.azul.azulVentas.ui.presentation.clientes.model.ClienteModel

@Composable
fun ClientList(clientes: List<ClienteModel>, clientesViewModel: ClientesViewModel) {

    var selectedEntity by remember { mutableStateOf<ClienteModel?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        ClientDialog(
            entity = selectedEntity,
            viewModel = clientesViewModel,
            onDismiss = {
                showDialog = false
                selectedEntity = null
            }
        )
    }

    Column {
        Button(onClick = {
            selectedEntity = null
            showDialog = true
        }
        ) {
            Text("Agregar")
        }

        LazyColumn {
            items(clientes, key = { it.id }) { client ->
                ClientCard(client, onClick = {
                    selectedEntity = client
                    showDialog = true
                })
            }
        }
    }
}

@Composable
fun ClientCard(clientes: ClienteModel, onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "Apellidos ",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.width(110.dp)
                )
                Text(
                    text = ": " + clientes.apellido.uppercase(),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Row(modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "Nombres",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.width(110.dp)
                )
                Text(
                    text = ": " + clientes.nombre.uppercase(),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Row(modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "Número ID",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.width(110.dp)
                )
                Text(
                    text = ": " + clientes.numeroDocumento,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Row(modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "Documento",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.width(110.dp)
                )
                Text(
                    text = ": " + clientes.tipoDocumento,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Row(modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "Teléfono ",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.width(110.dp)
                )
                Text(
                    text = ": " + clientes.telefono.uppercase(),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Row(modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "Email",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.width(110.dp)
                )
                Text(
                    text = ": " + clientes.email,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
