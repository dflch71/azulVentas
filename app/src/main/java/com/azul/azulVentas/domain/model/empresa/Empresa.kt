package com.azul.azulVentas.domain.model.empresa

import com.google.firebase.database.PropertyName

//Esta clase esta definida con los mismos campos que la
// clase Empresa de Realtimi Database de Firebase

data class Empresa(
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
