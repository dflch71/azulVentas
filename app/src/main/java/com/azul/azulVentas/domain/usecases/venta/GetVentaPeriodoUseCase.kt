package com.azul.azulVentas.domain.usecases.venta

import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.repository.venta.VentaRepository
import javax.inject.Inject

// domain/usecase/GetEmpresaPorTerceroUseCase.kt
class GetVentaPeriodoUseCase @Inject constructor(
    private val repository: VentaRepository
) {
    suspend operator fun invoke(EmpresaID: String): Result<List<ResumenPeriodo>> {
        return repository.getVentaPeriodo(EmpresaID)
    }
}