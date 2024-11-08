package com.azul.azulVentas.domain.usecases.user

import com.azul.azulVentas.domain.model.user.User
import com.azul.azulVentas.data.repository.user.UserRepository
import com.azul.azulVentas.core.utils.Result
import javax.inject.Inject

class LoginUseCase @Inject constructor (
    private val repository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return repository.login(email, password)
    }
}