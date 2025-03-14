package com.azul.azulVentas.domain.usecases.empresaPG

import com.azul.azulVentas.domain.model.empresaPG.EmpresaPG
import com.azul.azulVentas.domain.repository.empresaPG.EmpresaPGRepository
import javax.inject.Inject

// domain/usecase/GetEmpresaPorTerceroUseCase.kt
class GetEmpresaPGNitUseCase @Inject constructor(
    private val repository: EmpresaPGRepository
) {
    suspend operator fun invoke(Nit_ID: String): List<EmpresaPG> {
        return repository.getEmpresaPGNit(Nit_ID)
    }
}