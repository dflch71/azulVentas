package com.azul.azulVentas.domain.model.empresa

data class Empresa(
    val nit: String,
    val nombre: String,
    val direccion: String,
    val ciudad: String,
    val departamento: String,
    val picEmpresa: String,
    val email: String,
    val repLegal: String,
    val telefono: String
)
