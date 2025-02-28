package com.azul.azulVentas.domain.usecases.empresaFB

import com.azul.azulVentas.data.repository.empresaFB.EmpresaFBRepository
import com.azul.azulVentas.domain.model.empresa.Empresa
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObtenerEmpresasFBUseCase @Inject constructor(
    private val repository: EmpresaFBRepository
) {
    operator fun invoke(): Flow<List<Empresa>> {
        return repository.obtenerEmpresas()
    }
}