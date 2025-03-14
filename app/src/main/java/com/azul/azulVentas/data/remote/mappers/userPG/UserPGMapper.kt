package com.azul.azulVentas.data.remote.mappers.userPG

import com.azul.azulVentas.data.remote.model.UserPG.UserPGResponse
import com.azul.azulVentas.domain.model.userPG.UserPG

fun UserPGResponse.toDomain(): UserPG {
    return UserPG(
        //USUARIO_ID = this.usuario_id ?: 0,
        EMPRESA_ID = this.emp_Id ?: 0,
        USU_NOMBRE = this.usu_nombre ?: "",
        USU_APELLIDO = this.usu_apellido ?: "",
        USU_TELEFONO = this.usu_telefono ?: "",
        USU_EMAIL = this.usu_email ?: "",
        USU_PASS = this.usu_pass ?: "",
        USU_USUARIO = this.usu_usuario ?: ""
    )
}

fun UserPG.toResponse(): UserPGResponse {
    return UserPGResponse(
        //usuario_id = this.USUARIO_ID,
        emp_Id = this.EMPRESA_ID,
        usu_nombre = this.USU_NOMBRE,
        usu_apellido = this.USU_APELLIDO,
        usu_telefono = this.USU_TELEFONO,
        usu_email = this.USU_EMAIL,
        usu_pass = this.USU_PASS,
        usu_usuario = this.USU_USUARIO
    )


}