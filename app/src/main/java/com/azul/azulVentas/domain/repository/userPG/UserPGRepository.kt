package com.azul.azulVentas.domain.repository.userPG

import com.azul.azulVentas.domain.model.userPG.UserPG
import com.azul.azulVentas.core.utils.Result

interface UserPGRepository {
    suspend fun getUserPGEmail(usu_email: String): List<UserPG>
    //suspend fun insertUserPG(userPG: UserPG): UserPG?
    suspend fun insertUserPG(userPG: UserPG): Result<UserPG>
}