package com.azul.azulVentas.ui.presentation.usuarioEmpresas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.domain.model.empresaPG.EmpresaPG
import com.azul.azulVentas.domain.model.usuarioEmpresas.UsuarioEmpresas
import com.azul.azulVentas.domain.usecases.empresaPG.GetEmpresaPGEmailUseCase
import com.azul.azulVentas.domain.usecases.usuarioEmpresas.GetUsuarioEmpresasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// presentation/viewmodel/EmpresasViewModel.kt
@HiltViewModel
class UsuarioEmpresasPGViewModel @Inject constructor(
    private val getUsuarioEmpresasUseCase: GetUsuarioEmpresasUseCase,
) : ViewModel() {

    private val _usuarioEmpresas = MutableLiveData<List<UsuarioEmpresas>>()
    val usuarioEmpresas: LiveData<List<UsuarioEmpresas>> = _usuarioEmpresas

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun listUsuarioEmpresas(idEmpresa: String, Email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _usuarioEmpresas.value = getUsuarioEmpresasUseCase(idEmpresa, Email)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error .. (WS-AZUL): ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}