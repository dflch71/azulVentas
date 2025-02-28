package com.azul.azulVentas.data.repository.empresaFB

import com.azul.azulVentas.data.remote.EmpresaFBRemoteDataSource
import com.azul.azulVentas.domain.model.empresa.Empresa
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmpresaFBRepositoryImpl @Inject constructor(
    private val empresaRemoteDataSource: EmpresaFBRemoteDataSource
): EmpresaFBRepository {
    override fun obtenerEmpresas(): Flow<List<Empresa>> {
        return empresaRemoteDataSource.obtenerEmpresas()
    }

    override fun buscarEmpresaPorNit(nit: String): Flow<Empresa?> {
        return empresaRemoteDataSource.buscarEmpresaPorNit(nit)
    }
}