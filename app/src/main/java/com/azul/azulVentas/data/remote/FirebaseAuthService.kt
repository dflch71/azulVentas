package com.azul.azulVentas.data.remote

import com.azul.azulVentas.domain.model.user.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthService @Inject constructor() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    //Login
    suspend fun login(email: String, password: String): User? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user?.let { firebaseUser ->
                User(uid = firebaseUser.uid, email = firebaseUser.email ?: "")
            }
        } catch (e: Exception) {
            null
        }
    }

    fun signOut() {
        auth.signOut()
    }

    //Register
    suspend fun register(email: String, password: String): User? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let { firebaseUser ->
                User(uid = firebaseUser.uid, email = firebaseUser.email ?: "")
            }
        } catch (e: Exception) {
            null
        }
    }
}