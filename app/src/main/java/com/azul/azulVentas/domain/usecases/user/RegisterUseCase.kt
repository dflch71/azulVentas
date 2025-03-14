package com.azul.azulVentas.domain.usecases.user

import com.azul.azulVentas.domain.model.user.User
import com.azul.azulVentas.data.repository.user.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor (
    private val repository: UserRepository
) {
    //suspend operator fun invoke(email: String, password: String): User? {
    //    return repository.register(email, password) }
}