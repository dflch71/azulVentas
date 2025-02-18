package com.azul.azulVentas.ui.presentation.recoverPassword.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.domain.usecases.auth.SendPasswordResetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecoverPasswordViewModel @Inject constructor(
    private val sendPasswordResetUseCase: SendPasswordResetUseCase
) : ViewModel() {

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun sendPasswordReset(email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = sendPasswordResetUseCase(email)
            _isLoading.value = false
            _message.value = result.getOrElse { it.localizedMessage }
        }
    }

    fun clearMessage() { _message.value = null }
}