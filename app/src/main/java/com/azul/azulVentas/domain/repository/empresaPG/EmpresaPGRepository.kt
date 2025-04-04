package com.azul.azulVentas.domain.repository.empresaPG

import com.azul.azulVentas.domain.model.empresaPG.EmpresaPG
import com.azul.azulVentas.domain.model.usuarioEmpresas.UsuarioEmpresas

interface EmpresaPGRepository {
    suspend fun getEmpresaPGNit(Nit_ID: String): List<EmpresaPG>
    suspend fun getEmpresaPGEmail(Email: String): List<EmpresaPG>
    suspend fun getEmpresasPGEmpresaEmail(idEmpresa: String, Email: String): List<UsuarioEmpresas>
}