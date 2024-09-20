package com.azul.azulVentas.modClientes.ui

import com.azul.azulVentas.modClientes.ui.model.ClienteModel

sealed interface ClientesUiState {
    object Loading : ClientesUiState
    data class Error(val throwable: Throwable) : ClientesUiState
    data class Success(val clientes: List<ClienteModel>) : ClientesUiState
}