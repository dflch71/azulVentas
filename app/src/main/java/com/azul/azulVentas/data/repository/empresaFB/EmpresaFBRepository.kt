package com.azul.azulVentas.data.repository.empresaFB

import com.azul.azulVentas.domain.model.empresaFB.EmpresaFB
import kotlinx.coroutines.flow.Flow

interface EmpresaFBRepository {
    fun obtenerEmpresas(): Flow<List<EmpresaFB>>
    fun buscarEmpresaPorNit(nit: String): Flow<EmpresaFB?>
}