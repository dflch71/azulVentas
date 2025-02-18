package com.azul.azulVentas.data.repository.auth

interface AuthRepository {
    suspend fun sendPasswordResetEmail(email: String): Result<String>
}