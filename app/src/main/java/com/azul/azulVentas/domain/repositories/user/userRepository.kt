package com.azul.azulVentas.domain.repositories.user

import com.azul.azulVentas.domain.model.user.User

interface UserRepository {
    suspend fun login(email: String, password: String): User?
    suspend fun signout()
    suspend fun register(email: String, password: String): User?
}