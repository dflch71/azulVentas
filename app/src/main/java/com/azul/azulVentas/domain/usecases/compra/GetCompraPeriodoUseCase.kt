package com.azul.azulVentas.domain.usecases.compra

import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.repository.compra.CompraRepository
import javax.inject.Inject

// domain/usecase/GetEmpresaPorTerceroUseCase.kt
class GetCompraPeriodoUseCase @Inject constructor(
    private val repository: CompraRepository
) {
    suspend operator fun invoke(EmpresaID: String): List<ResumenPeriodo> {
        return repository.getCompraPeriodo(EmpresaID)
    }
}