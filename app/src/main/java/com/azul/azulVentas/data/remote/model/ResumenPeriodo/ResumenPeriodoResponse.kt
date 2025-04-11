package com.azul.azulVentas.data.remote.model.ResumenPeriodo

import com.google.gson.annotations.SerializedName

data class ResumenPeriodoResponse(
    @SerializedName("tipo") val Tipo: String,
    @SerializedName("periodo") val Periodo: Int,
    @SerializedName("nom_periodo") val NomPerido: String,
    @SerializedName("facturas") val Facturas: Int,
    @SerializedName("sum_periodo") val SumPeriodo: Double,
    @SerializedName("sum_contado") val SumContado: Double,
    @SerializedName("sum_credito") val SumCredito: Double
)
