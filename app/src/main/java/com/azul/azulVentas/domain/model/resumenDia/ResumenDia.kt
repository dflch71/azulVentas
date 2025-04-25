package com.azul.azulVentas.domain.model.resumenDia

data class ResumenDia(
    val tipo: String,
    val fecha_dia: String,
    val hora_dia: Int,
    val sum_hora: Double,
    val sum_contado: Double,
    val sum_credito: Double,
    val sum_factura: Int
)