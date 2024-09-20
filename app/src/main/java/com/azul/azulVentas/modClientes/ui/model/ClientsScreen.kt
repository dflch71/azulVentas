package com.azul.azulVentas.modClientes.ui.model

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.azul.azulVentas.modClientes.ui.ClientesList
import com.azul.azulVentas.modClientes.ui.ClientesUiState
import com.azul.azulVentas.modClientes.ui.ClientesViewModel
import com.azul.azulVentas.modClientes.ui.components.ClientList


@Composable
fun ClientsScreen (
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
                ClientList( (uiState as ClientesUiState.Success).clientes, clientesViewModel )
            }
        }
    }
}