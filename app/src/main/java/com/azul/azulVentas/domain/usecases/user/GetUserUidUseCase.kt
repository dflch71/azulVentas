package com.azul.azulVentas.domain.usecases.user

import com.azul.azulVentas.data.repository.user.UserRepository
import javax.inject.Inject

class GetUserUidUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke() = repository.getUserUid()
}