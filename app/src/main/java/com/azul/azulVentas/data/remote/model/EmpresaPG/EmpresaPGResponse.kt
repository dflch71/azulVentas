package com.azul.azulVentas.data.remote.model.EmpresaPG

import com.google.gson.annotations.SerializedName

// data/remote/model/EmpresaResponse.kt
data class EmpresaPGResponse(
    @SerializedName("EMPRESA_ID") val emp_Id: Int,
    @SerializedName("EMP_RAZON_SOCIAL") val emp_RazonSocial: String,
    @SerializedName("EMP_TERCERO") val emp_Tercero: String,
    @SerializedName("EMP_DIRECCION") val emp_Direccion: String,
    @SerializedName("EMP_BARRIO") val emp_Barrio: String,
    @SerializedName("EMP_CIUDAD") val emp_Ciudad: String,
    @SerializedName("EMP_TELEFONO") val emp_Telefono: String,
    @SerializedName("EMP_EMAIL") val emp_Email: String
)
