package com.azul.azulVentas.modClientes.ui.model

data class ClienteModel (
    val id: Int = System.currentTimeMillis().hashCode(),
    val tipoDocumento: String,
    val numeroDocumento: String,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val email: String
)