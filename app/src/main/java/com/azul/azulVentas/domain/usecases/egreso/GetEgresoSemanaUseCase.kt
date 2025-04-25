package com.azul.azulVentas.domain.usecases.egreso

import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana
import com.azul.azulVentas.domain.repository.egreso.EgresoRepository
import javax.inject.Inject

// domain/usecase/GetEmpresaPorTerceroUseCase.kt
class GetEgresoSemanaUseCase @Inject constructor(
    private val repository: EgresoRepository
) {
    suspend operator fun invoke(EmpresaID: String): List<ResumenSemana> {
        return repository.getEgresoSemana(EmpresaID)
    }
}