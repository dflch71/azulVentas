package com.azul.azulVentas.domain.usecases.user

import com.azul.azulVentas.domain.model.user.User
import com.azul.azulVentas.domain.repositories.user.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor (
    private val repository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): User? {
        return repository.login(email, password) }

    //suspend fun execute(email: String, password: String): User? {
    //    return repository.login(email, password) }
}