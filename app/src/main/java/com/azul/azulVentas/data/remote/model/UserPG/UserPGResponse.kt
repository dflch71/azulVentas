package com.azul.azulVentas.data.remote.model.UserPG

import com.google.gson.annotations.SerializedName

data class UserPGResponse(
    //@SerializedName("USUARIO_ID") val usuario_id: Int,
    @SerializedName("EMPRESA_ID") val emp_Id: Int,
    @SerializedName("USU_NOMBRE") val usu_nombre: String,
    @SerializedName("USU_APELLIDO") val usu_apellido: String,
    @SerializedName("USU_TELEFONO") val usu_telefono: String,
    @SerializedName("USU_EMAIL") val usu_email: String,
    @SerializedName("USU_PASS") val usu_pass: String,
    @SerializedName("USU_USUARIO") val usu_usuario: String
)