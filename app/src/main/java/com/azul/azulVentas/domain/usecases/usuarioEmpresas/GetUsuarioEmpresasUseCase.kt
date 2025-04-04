package com.azul.azulVentas.domain.usecases.usuarioEmpresas

import com.azul.azulVentas.domain.model.empresaPG.EmpresaPG
import com.azul.azulVentas.domain.model.usuarioEmpresas.UsuarioEmpresas
import com.azul.azulVentas.domain.repository.empresaPG.EmpresaPGRepository
import javax.inject.Inject

// domain/usecase/GetEmpresaPorTerceroUseCase.kt
class GetUsuarioEmpresasUseCase @Inject constructor(
    private val repository: EmpresaPGRepository
) {
    suspend operator fun invoke(idEmpresa: String, Email: String): List<UsuarioEmpresas> {
        return repository.getEmpresasPGEmpresaEmail(idEmpresa, Email)
    }
}

