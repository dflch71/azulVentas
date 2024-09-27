package com.azul.azulVentas.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ClienteEntity (
    @PrimaryKey
    val id: Int,
    var tipoDocumento: String,
    var numeroDocumento: String,
    var nombre: String,
    var apellido: String,
    var telefono: String,
    var email: String
)