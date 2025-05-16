package com.azul.azulVentas.domain.model.resumenOperaciones

data class ResumenOperaciones(
    val tituloDia: String = "",
    val tituloSemana: String = "",
    val tituloPeriodo: String = "",
    val tipo_pago: String = "",
    val facturas: String = "",
    val total: String = "",
    val efectivo: String = "",
    val credito: String = ""
)
