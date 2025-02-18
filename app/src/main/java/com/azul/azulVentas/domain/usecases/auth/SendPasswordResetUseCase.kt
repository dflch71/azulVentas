package com.azul.azulVentas.domain.usecases.auth

import com.azul.azulVentas.data.repository.auth.AuthRepository
import javax.inject.Inject

class SendPasswordResetUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(email: String): Result<String> {
        return authRepository.sendPasswordResetEmail(email)
    }
}