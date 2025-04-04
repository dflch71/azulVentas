package com.azul.azulVentas.data.remote.model.ResumenSemana

import com.google.gson.annotations.SerializedName

data class ResumenSemanaResponse(
    @SerializedName("tipo") val Tipo: String,
    @SerializedName("fecha_dia") val FechaDia: String,
    @SerializedName("dia_mes") val DiaMes: Int,
    @SerializedName("dia_semana") val DiaSemana: String,
    @SerializedName("facturas") val Facturas: Int,
    @SerializedName("sum_dia") val SumDia: Double,
    @SerializedName("sum_contado") val SumContado: Double,
    @SerializedName("sum_credito") val SumCredito: Double
)
