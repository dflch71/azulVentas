package com.azul.azulVentas.domain.usecases.egreso

import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.repository.egreso.EgresoRepository
import javax.inject.Inject

// domain/usecase/GetEmpresaPorTerceroUseCase.kt
class GetEgresoPeriodoUseCase @Inject constructor(
    private val repository: EgresoRepository
) {
    suspend operator fun invoke(EmpresaID: String): List<ResumenPeriodo> {
        return repository.getEgresoPeriodo(EmpresaID)
    }
}