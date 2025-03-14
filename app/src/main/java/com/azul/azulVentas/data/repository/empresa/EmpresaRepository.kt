package com.azul.azulVentas.data.repository.empresa

import com.azul.azulVentas.domain.model.empresaFB.EmpresaFB

interface EmpresaRepository {
    suspend fun insertarEmpresa(empresa: EmpresaFB): Result<Boolean>
    suspend fun verificarEmpresa(nit: String): Result<Boolean>
}