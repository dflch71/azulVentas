package com.azul.azulVentas.ui.presentation.registration.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.domain.model.user.User
import com.azul.azulVentas.domain.usecases.user.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel@Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    /*
    private val _estadoRegistro = MutableLiveData<Result<Boolean>?>()
    val estadoRegistro: MutableLiveData<Result<Boolean>?> = _estadoRegistro

    fun registraUsuario(email: String, password: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = registerUseCase(email, password)
            onResult(user)
            _estadoRegistro.value = Result.success(user != null)
        }
    }

    fun limpiarEstadoRegistro() { _estadoRegistro.postValue(null) }
    */
}
