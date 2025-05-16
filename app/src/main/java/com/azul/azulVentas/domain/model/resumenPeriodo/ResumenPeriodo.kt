package com.azul.azulVentas.domain.model.resumenPeriodo

data class ResumenPeriodo(
    val tipo: String,
    val periodo: Int ,
    val nom_periodo: String,
    val tipo_pago: String,
    val facturas: Int,
    val sum_periodo: Double,
    val sum_contado: Double,
    val sum_credito: Double,
)

