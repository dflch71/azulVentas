package com.azul.azulVentas.domain.usecases.venta

import com.azul.azulVentas.domain.model.empresaPG.EmpresaPG
import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana
import com.azul.azulVentas.domain.repository.empresaPG.EmpresaPGRepository
import com.azul.azulVentas.domain.repository.venta.VentaRepository
import javax.inject.Inject

// domain/usecase/GetEmpresaPorTerceroUseCase.kt
class GetVentaSemanaUseCase @Inject constructor(
    private val repository: VentaRepository
) {
    suspend operator fun invoke(EmpresaID: String): Result<List<ResumenSemana>> {
        return repository.getVentaSemana(EmpresaID)
    }
}