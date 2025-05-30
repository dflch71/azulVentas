package com.azul.azulVentas.data.remote.model.ResumenDia

import com.google.gson.annotations.SerializedName

data class ResumenDiaResponse(
    @SerializedName("tipo") val Tipo: String,
    @SerializedName("fecha_dia") val FechaDia: String,
    @SerializedName("hora_dia") val HoraDia: Int,
    @SerializedName("hora_am_pm") val HoraAmPm: String,
    @SerializedName("tipo_pago") val TipoPago: String,
    @SerializedName("facturas") val SumFactura: Int,
    @SerializedName("sum_hora") val SumHora: Double,
    @SerializedName("sum_contado") val SumContado: Double,
    @SerializedName("sum_credito") val SumCredito: Double
)
