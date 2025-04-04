package com.azul.azulVentas.data.remote.model.EmpresaEmail

import com.google.gson.annotations.SerializedName

data class EmpresaEmailResponse(
    @SerializedName("usuarios") val usuarios: String,
)
