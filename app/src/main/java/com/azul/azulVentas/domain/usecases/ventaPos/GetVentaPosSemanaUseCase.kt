package com.azul.azulVentas.domain.usecases.ventaPos

import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana
import com.azul.azulVentas.domain.repository.ventaPos.VentaPosRepository
import javax.inject.Inject

// domain/usecase/GetEmpresaPorTerceroUseCase.kt
class GetVentaPosSemanaUseCase @Inject constructor(
    private val repository: VentaPosRepository
) {
    suspend operator fun invoke(EmpresaID: String): List<ResumenSemana> {
        return repository.getVentaPosSemana(EmpresaID)
    }
}