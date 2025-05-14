package com.azul.azulVentas.domain.usecases.egreso

import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.repository.egreso.EgresoRepository
import javax.inject.Inject

// domain/usecase/GetEmpresaPorTerceroUseCase.kt
class GetEgresoDiaUseCase @Inject constructor(
    private val repository: EgresoRepository
) {
    suspend operator fun invoke(EmpresaID: String): Result<List<ResumenDia>> {
        return repository.getEgresoHora(EmpresaID)
    }
}