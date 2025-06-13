package com.azul.azulVentas.domain.usecases.ventaPos

import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.repository.ventaPos.VentaPosRepository
import javax.inject.Inject

// domain/usecase/GetEmpresaPorTerceroUseCase.kt
class GetVentaPosDiaFechaUseCase @Inject constructor(
    private val repository: VentaPosRepository
) {
    suspend operator fun invoke(EmpresaID: String, Fecha: String): Result<List<ResumenDia>> {
        return repository.getVentaPosHoraFecha(EmpresaID, Fecha)
    }
}