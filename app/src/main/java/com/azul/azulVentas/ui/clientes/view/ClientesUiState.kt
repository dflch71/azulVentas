package com.azul.azulVentas.ui.clientes.view

import com.azul.azulVentas.ui.clientes.model.ClienteModel

sealed interface ClientesUiState {
    object Loading : ClientesUiState
    data class Error(val throwable: Throwable) : ClientesUiState
    data class Success(val clientes: List<ClienteModel>) : ClientesUiState
}