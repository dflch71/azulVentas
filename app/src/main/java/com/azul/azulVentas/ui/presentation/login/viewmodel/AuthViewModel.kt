package com.azul.azulVentas.ui.presentation.login.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.core.utils.Result
import com.azul.azulVentas.domain.model.user.User
import com.azul.azulVentas.domain.usecases.user.GetUserEmailUseCase
import com.azul.azulVentas.domain.usecases.user.GetUserLastDayUseCase
import com.azul.azulVentas.domain.usecases.user.GetUserUidUseCase
import com.azul.azulVentas.domain.usecases.user.IsLoggedInUseCase
import com.azul.azulVentas.domain.usecases.user.LoginUseCase
import com.azul.azulVentas.domain.usecases.user.SignOutUseCase
import com.azul.azulVentas.domain.usecases.user.IsUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val getUserUidUseCase: GetUserUidUseCase,
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val getUserLastDayUseCase: GetUserLastDayUseCase
) : ViewModel() {

    var loginState by mutableStateOf<LoginState>(LoginState.Idle)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {

            loginState = LoginState.Loading

            repeat(3) { attempt ->

                try {
                    withTimeout(5000) {
                        val result = loginUseCase(email, password)
                        when (result) {
                            is Result.Success -> loginState = LoginState.Success(result.data)
                            is Result.Error -> loginState = LoginState.Error(result.message)
                            is Result.ErrorPG -> loginState = LoginState.Error(result.throwable.message.toString())
                        }
                        return@withTimeout
                    }
                }
                catch (e: TimeoutCancellationException) {
                    if (attempt < 2) {
                        loginState = LoginState.Error("Verificando conexión a internet...\nVerificando Credenciales de acceso...")
                        delay(1000)
                    } else {
                        loginState = LoginState.Error("Tiempo de espera agotado.\nCerrando sesión...")
                        //signout()
                    }
                }
            }

        }
    }

    fun signout() { signOutUseCase() }

    fun isUserLoggedIn(): Boolean { return isUserLoggedInUseCase() }

    fun isLoggedIn(): Boolean { return isLoggedInUseCase() }

    fun getUserUid(): String? { return getUserUidUseCase() }

    fun getUserEmail(): String? { return getUserEmailUseCase() }

    fun getUserLastDay(): String? { return getUserLastDayUseCase() }

    // Variables para almacenar el email y la contraseña
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun setEmail_(value: String) { email = value }

    fun setPassword_(value: String) { password = value }

    fun clearState() { loginState = LoginState.Idle }
}

sealed class LoginState {
    object Idle : LoginState()          // Estado inactivo, cuando no ha pasado nada aún
    object Loading : LoginState()       // Estado de carga, cuando el usuario está en proceso de autenticarse
    data class Success(val user: User) : LoginState()  // Estado de éxito, cuando el usuario ha iniciado sesión correctamente
    data class Error(val message: String) : LoginState()  // Estado de error, con un mensaje describiendo el error
}