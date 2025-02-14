package com.azul.azulVentas.domain.usecases.empresa

import com.azul.azulVentas.data.repository.empresa.EmpresaRepository
import com.azul.azulVentas.domain.model.empresa.Empresa
import javax.inject.Inject

class AddEmpresaUseCase @Inject constructor(
    private val empresaRepository: EmpresaRepository
){
    suspend operator fun invoke(empresa: Empresa): Result<Boolean> {
        return try {
            val existsResult = empresaRepository.verificarEmpresa(empresa.nit)
            if (existsResult.isSuccess) {
                if (existsResult.getOrNull() == true) {
                    Result.failure(Exception("Ya existe una EMPRESA registrada con este NÍT y/o ID -->"))
                } else {
                    empresaRepository.insertarEmpresa(empresa)
                }
            } else {
                Result.failure(existsResult.exceptionOrNull() ?: Exception("Error de verificación"))
            }
        } catch (e: Exception) { Result.failure(e) }
    }
}