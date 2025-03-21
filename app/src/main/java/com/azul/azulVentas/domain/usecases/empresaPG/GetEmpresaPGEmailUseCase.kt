package com.azul.azulVentas.domain.usecases.empresaPG

import com.azul.azulVentas.domain.model.empresaPG.EmpresaPG
import com.azul.azulVentas.domain.repository.empresaPG.EmpresaPGRepository
import javax.inject.Inject

// domain/usecase/GetEmpresaPorTerceroUseCase.kt
class GetEmpresaPGEmailUseCase @Inject constructor(
    private val repository: EmpresaPGRepository
) {
    suspend operator fun invoke(Email: String): List<EmpresaPG> {
        return repository.getEmpresaPGEmail(Email)
    }
}