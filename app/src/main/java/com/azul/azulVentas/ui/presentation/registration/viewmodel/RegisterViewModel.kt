package com.azul.azulVentas.ui.presentation.registration.viewmodel

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

    fun register(email: String, password: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = registerUseCase(email, password)
            onResult(user)
        }
    }
}
