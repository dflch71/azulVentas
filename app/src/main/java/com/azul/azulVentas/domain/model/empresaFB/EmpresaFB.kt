package com.azul.azulVentas.domain.model.empresaFB

import com.google.firebase.database.PropertyName

// Esta clase esta definida con los mismos campos que la
// clase Empresa de Realtime Database de Firebase

data class EmpresaFB(
    @PropertyName("nit") val nit: String,
    @PropertyName("nombre") val nombre: String,
    @PropertyName("direccion") val direccion: String,
    @PropertyName("ciudad") val ciudad: String,
    @PropertyName("departamento") val departamento: String,
    @PropertyName("picEmpresa") val picEmpresa: String,
    @PropertyName("email") val email: String,
    @PropertyName("repLegal") val repLegal: String,
    @PropertyName("telefono") val telefono: String
) {
    // Constructor sin argumentos
    constructor() : this("", "", "", "", "", "", "", "", "")
}
