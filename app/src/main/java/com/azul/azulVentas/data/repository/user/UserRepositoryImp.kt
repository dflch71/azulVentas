package com.azul.azulVentas.data.repository.user


import com.azul.azulVentas.data.remote.FirebaseAuthService
import com.azul.azulVentas.domain.model.user.User
import com.azul.azulVentas.core.utils.Result
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authService: FirebaseAuthService
) : UserRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return authService.login(email, password)
    }

    override fun signout() {
        authService.signOut()
    }

    override fun isUserLoggedIn(): Boolean {
        return authService.isUserLoggedIn()
    }

    override fun isLoggedIn(): Boolean {
        return authService.isLoggedIn()
    }

    override fun getUserUid(): String? {
        return authService.getUserUid()
    }

    override fun getUserEmail(): String? {
        return authService.getUserEmail()
    }

    override fun getUserLastDay(): String? {
        return authService.getUserLastDay()
    }

    override suspend fun register(email: String, password: String): User? {
        return authService.register(email, password)
    }

}