package com.azul.azulVentas.domain.usecases.compra

import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana
import com.azul.azulVentas.domain.repository.compra.CompraRepository
import javax.inject.Inject

// domain/usecase/GetEmpresaPorTerceroUseCase.kt
class GetCompraSemanaUseCase @Inject constructor(
    private val repository: CompraRepository
) {
    suspend operator fun invoke(EmpresaID: String): List<ResumenSemana> {
        return repository.getCompraSemana(EmpresaID)
    }
}