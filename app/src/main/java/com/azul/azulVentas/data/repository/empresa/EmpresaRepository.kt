package com.azul.azulVentas.data.repository.empresa

import com.azul.azulVentas.domain.model.empresa.Empresa

interface EmpresaRepository {
    suspend fun insertarEmpresa(empresa: Empresa): Result<Boolean>
    suspend fun verificarEmpresa(nit: String): Result<Boolean>
}