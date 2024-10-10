package com.azul.azulVentas.data.repository.user

import com.azul.azulVentas.domain.model.user.User
import com.azul.azulVentas.core.utils.Result

interface UserRepository {
    suspend fun login(email: String, password: String): Result<User>
    fun signout()
    fun isUserLoggedIn(): Boolean
    fun isLoggedIn(): Boolean
    fun getUserUid(): String?
    fun getUserEmail(): String?
    fun getUserLastDay(): String?
    suspend fun register(email: String, password: String): User?
}