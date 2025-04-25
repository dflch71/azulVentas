package com.azul.azulVentas.data.repository.auth

interface AuthRepository {
    suspend fun sendPasswordResetEmail(email: String): Result<String>

    //Anonynous sign in
    suspend fun signInAnonymously(): Result<Unit>
    fun isUserAuthenticated(): Boolean
}