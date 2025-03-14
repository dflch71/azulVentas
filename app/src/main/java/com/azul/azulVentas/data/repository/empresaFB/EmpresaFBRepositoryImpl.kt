package com.azul.azulVentas.data.repository.empresaFB

import com.azul.azulVentas.data.remote.model.EmpresaFB.EmpresaFBRemoteDataSource
import com.azul.azulVentas.domain.model.empresaFB.EmpresaFB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmpresaFBRepositoryImpl @Inject constructor(
    private val empresaRemoteDataSource: EmpresaFBRemoteDataSource
): EmpresaFBRepository {
    override fun obtenerEmpresas(): Flow<List<EmpresaFB>> {
        return empresaRemoteDataSource.obtenerEmpresas()
    }

    override fun buscarEmpresaPorNit(nit: String): Flow<EmpresaFB?> {
        return empresaRemoteDataSource.buscarEmpresaPorNit(nit)
    }
}