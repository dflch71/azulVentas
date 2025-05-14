package com.azul.azulVentas.domain.usecases.ventaPos

import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.repository.ventaPos.VentaPosRepository
import javax.inject.Inject

// domain/usecase/GetEmpresaPorTerceroUseCase.kt
class GetVentaPosDiaUseCase @Inject constructor(
    private val repository: VentaPosRepository
) {
    suspend operator fun invoke(EmpresaID: String): Result<List<ResumenDia>> {
        return repository.getVentaPosHora(EmpresaID)
    }
}