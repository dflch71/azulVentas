package com.azul.azulVentas.ui.presentation.login.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.domain.model.user.User
import com.azul.azulVentas.domain.usecases.user.SignOutUseCase
import com.azul.azulVentas.domain.usecases.user.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    fun login(email: String, password: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            //val user = loginUseCase.execute(email, password)
            val user = loginUseCase(email, password)
            onResult(user)
        }
    }

    private val _isUserLoggedOut = MutableStateFlow(false)
    val isUserLoggedOut: StateFlow<Boolean> = _isUserLoggedOut

    fun signout() {
        // Implementar lógica de signout si es necesario
        viewModelScope.launch {
            signOutUseCase.signOut()
            _isUserLoggedOut.value = true
        }
    }


    // Variables para almacenar el email y la contraseña
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun setEmail_(value: String) {
        email = value
    }

    fun setPassword_(value: String) {
        password = value
    }
}