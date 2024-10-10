package com.azul.azulVentas.data.remote

import androidx.compose.ui.text.capitalize
import com.azul.azulVentas.core.utils.Result
import com.azul.azulVentas.data.local.sharePreferences.SessionManager
import com.azul.azulVentas.domain.model.user.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class FirebaseAuthService @Inject constructor(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val sessionManager: SessionManager
) {

    //Login
    suspend fun login(email: String, password: String): Result<User> {
        return try {
            withContext(Dispatchers.IO) {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                val user = result.user?.let { firebaseUser ->
                    User(uid = firebaseUser.uid, email = firebaseUser.email ?: "")
                }
                if (user != null) {
                    //Obtener la fecha actual
                    val currentDateTime = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy HH:mm:ss a")
                    val formattedDateTime = currentDateTime.format(formatter)

                    //Guardar la sesión
                    sessionManager.saveUserSession(user.uid, user.email, formattedDateTime ?: "")
                    Result.Success(user)
                } else {
                    Result.Error("Error al iniciar sesión")
                }
            }
        }
        catch (e: TimeoutCancellationException) { Result.Error("Tiempo de espera agotado - Servidor no disponible") }
        catch (e: FirebaseAuthInvalidUserException) { Result.Error("El correo no está registrado") }
        catch (e: FirebaseAuthInvalidCredentialsException) { Result.Error("Credenciales inválidas (Usurio o contraseña)") }
        catch (e: Exception) { Result.Error(e.localizedMessage ?: "Error desconocido -> ${e.message}") }
    }

    fun signOut() {
        auth.signOut()
        sessionManager.clearSession()
    }

    fun isUserLoggedIn(): Boolean {
        val currentUser= auth.currentUser
        return currentUser != null || sessionManager.isUserLoggedIn()
    }

    fun getUserUid(): String? {
        return sessionManager.getUserSession()
    }

    fun getUserEmail(): String? {
        return sessionManager.getUserEmail()
    }

    fun getUserLastDay(): String? {
        return sessionManager.getUserLastDay()
    }

    fun isLoggedIn(): Boolean {
        return sessionManager.isLoggedIn()
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

