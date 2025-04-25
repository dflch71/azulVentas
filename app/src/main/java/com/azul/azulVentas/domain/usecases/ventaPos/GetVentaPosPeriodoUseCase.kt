package com.azul.azulVentas.domain.usecases.ventaPos

import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.repository.ventaPos.VentaPosRepository
import javax.inject.Inject

// domain/usecase/GetEmpresaPorTerceroUseCase.kt
class GetVentaPosPeriodoUseCase @Inject constructor(
    private val repository: VentaPosRepository
) {
    suspend operator fun invoke(EmpresaID: String): List<ResumenPeriodo> {
        return repository.getVentaPosPeriodo(EmpresaID)
    }
}