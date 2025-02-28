package com.azul.azulVentas.ui.presentation.registration.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.domain.model.user.User
import com.azul.azulVentas.domain.usecases.user.RegisterEmailUseCase
import com.azul.azulVentas.core.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterEmailViewModel@Inject constructor(
    private val registerEmailUseCase: RegisterEmailUseCase
) : ViewModel() {

    private val _registerState = MutableStateFlow<Result<User>?>(null)
    val registerState: StateFlow<Result<User>?> = _registerState

    fun registerEmail(email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = registerEmailUseCase(email, password)
        }
    }

    fun limpiarEstadoRegistro() {
        _registerState.value = null
    }

}
