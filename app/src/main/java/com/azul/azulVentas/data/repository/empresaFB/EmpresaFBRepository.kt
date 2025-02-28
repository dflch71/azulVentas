package com.azul.azulVentas.data.repository.empresaFB

import com.azul.azulVentas.domain.model.empresa.Empresa
import kotlinx.coroutines.flow.Flow

interface EmpresaFBRepository {
    fun obtenerEmpresas(): Flow<List<Empresa>>
    fun buscarEmpresaPorNit(nit: String): Flow<Empresa?>
}