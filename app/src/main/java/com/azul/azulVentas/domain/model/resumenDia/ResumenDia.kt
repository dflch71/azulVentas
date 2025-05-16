package com.azul.azulVentas.domain.model.resumenDia

data class ResumenDia(
    val tipo: String,
    val fecha_dia: String,
    val hora_dia: Int,
    val hora_am_pm: String,
    val tipo_pago: String,
    val sum_factura: Int,
    val sum_hora: Double,
    val sum_contado: Double,
    val sum_credito: Double
)