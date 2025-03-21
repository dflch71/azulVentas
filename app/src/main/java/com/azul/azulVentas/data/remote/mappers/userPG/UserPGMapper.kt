package com.azul.azulVentas.data.remote.mappers.userPG

import com.azul.azulVentas.data.remote.model.UserPG.UserPGResponse
import com.azul.azulVentas.domain.model.userPG.UserPG

fun UserPGResponse.toDomain(): UserPG {
    return UserPG(
        //USUARIO_ID = this.usuario_id ?: 0,
        EMPRESA_ID = this.empID ?: 0,
        USU_NOMBRE = this.usuNombre ?: "",
        USU_APELLIDO = this.usuApellido ?: "",
        USU_TELEFONO = this.usuTelefono ?: "",
        USU_EMAIL = this.usuEmail ?: "",
        USU_PASS = this.usuPass ?: "",
        USU_USUARIO = this.usuUsuario ?: ""
    )
}

fun UserPG.toResponse(): UserPGResponse {
    return UserPGResponse(
        //usuario_id = this.USUARIO_ID,
        empID = this.EMPRESA_ID ?: 0,
        usuNombre = this.USU_NOMBRE.trim() ?: "",
        usuApellido = this.USU_APELLIDO.trim() ?: "",
        usuTelefono = this.USU_TELEFONO ?: "",
        usuEmail = this.USU_EMAIL.trim() ?: "",
        usuPass = this.USU_PASS ?: "",
        usuUsuario = this.USU_USUARIO ?: ""
    )

}