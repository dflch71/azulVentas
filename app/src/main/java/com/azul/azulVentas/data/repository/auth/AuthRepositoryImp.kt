package com.azul.azulVentas.data.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun sendPasswordResetEmail(email: String): Result<String> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success("Correo enviado a $email\nRecibirás un enlace para restablecer tu contraseña!")
        }catch (e: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("El correo ingresado no está registrado."))
        } catch (e: Exception) {
            Result.failure(Exception("Error al enviar el correo:\n${e.localizedMessage}"))
        }
    }
}