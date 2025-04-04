package com.azul.azulVentas.data.remote.mappers.userPG

import com.azul.azulVentas.data.remote.model.EmpresaEmail.EmpresaEmailResponse
import com.azul.azulVentas.domain.model.usuarioEmpresas.UsuarioEmpresas

fun EmpresaEmailResponse.toDomain(): UsuarioEmpresas {
    return UsuarioEmpresas(usuarios = this.usuarios.toInt())
}
