package com.azul.azulVentas.data.remote.model.UserPG

import com.google.gson.annotations.SerializedName

data class UserPGResponse(
    //@SerializedName("USUARIO_ID") val usuario_id: Int,
    @SerializedName("EMPRESA_ID") val empID: Int,
    @SerializedName("USU_NOMBRE") val usuNombre: String,
    @SerializedName("USU_APELLIDO") val usuApellido: String,
    @SerializedName("USU_TELEFONO") val usuTelefono: String,
    @SerializedName("USU_EMAIL") val usuEmail: String,
    @SerializedName("USU_PASS") val usuPass: String,
    @SerializedName("USU_USUARIO") val usuUsuario: String
)