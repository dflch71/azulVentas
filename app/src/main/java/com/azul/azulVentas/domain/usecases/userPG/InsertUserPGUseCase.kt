package com.azul.azulVentas.domain.usecases.userPG

import com.azul.azulVentas.domain.model.userPG.UserPG
import com.azul.azulVentas.domain.repository.userPG.UserPGRepository
import javax.inject.Inject
import com.azul.azulVentas.core.utils.Result

class InsertUserPGUseCase @Inject constructor(
    private val userPGRepository: UserPGRepository
) {
    suspend operator fun invoke(userPG: UserPG): Result<UserPG> { return userPGRepository.insertUserPG(userPG) }
}

