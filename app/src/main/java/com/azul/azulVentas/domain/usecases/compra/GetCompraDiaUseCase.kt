package com.azul.azulVentas.domain.usecases.compra

import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.repository.compra.CompraRepository
import javax.inject.Inject

// domain/usecase/GetEmpresaPorTerceroUseCase.kt
class GetCompraDiaUseCase @Inject constructor(
    private val repository: CompraRepository
) {
    suspend operator fun invoke(EmpresaID: String): Result<List<ResumenDia>> {
        return repository.getCompraHora(EmpresaID)
    }
}