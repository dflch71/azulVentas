package com.azul.azulVentas.domain.usecases.user

import com.azul.azulVentas.domain.repositories.user.UserRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun signOut() {
        repository.signout()
    }
}