package com.azul.azulVentas.domain.usecases.auth

import com.azul.azulVentas.data.repository.auth.AuthRepository
import jakarta.inject.Inject

class SignInAnonymouslyUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.signInAnonymously()
}

