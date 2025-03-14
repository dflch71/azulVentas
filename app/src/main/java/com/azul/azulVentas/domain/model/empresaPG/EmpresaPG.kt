package com.azul.azulVentas.domain.model.empresaPG

data class EmpresaPG(
    val EMPRESA_ID: Int,
    val EMP_RAZON_SOCIAL: String,
    val EMP_TERCERO: String,
    val EMP_DIRECCION: String,
    val EMP_BARRIO: String,
    val EMP_CIUDAD: String,
    val EMP_TELEFONO: String,
    val EMP_EMAIL: String
)