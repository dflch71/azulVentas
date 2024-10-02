package com.azul.azulVentas.data.repository


import com.azul.azulVentas.data.remote.FirebaseAuthService
import com.azul.azulVentas.domain.model.user.User
import com.azul.azulVentas.domain.repositories.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authService: FirebaseAuthService
) : UserRepository {

    override suspend fun login(email: String, password: String): User? {
        return authService.login(email, password)
    }

    override suspend fun logout() {
        authService.logout()
    }
}