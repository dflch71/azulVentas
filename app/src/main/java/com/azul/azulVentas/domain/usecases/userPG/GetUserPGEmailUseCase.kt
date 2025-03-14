package com.azul.azulVentas.domain.usecases.userPG


import com.azul.azulVentas.domain.model.userPG.UserPG
import com.azul.azulVentas.domain.repository.userPG.UserPGRepository
import javax.inject.Inject

// domain/usecase/GetEmpresaPorTerceroUseCase.kt
class GetUserPGEmailUseCase @Inject constructor(
    private val repository: UserPGRepository
) {
    suspend operator fun invoke(usu_email: String): List<UserPG> { return repository.getUserPGEmail(usu_email) }
}