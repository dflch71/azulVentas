package com.azul.azulVentas.domain.model.resumenSemana

data class ResumenSemana(
    val tipo: String,
    val fecha_dia: String,
    val dia_mes: Int,
    val dia_semana: String,
    val tipo_pago: String,
    val facturas: Int,
    val sum_dia: Double,
    val sum_contado: Double,
    val sum_credito: Double,
)