package com.azul.azulVentas.ui.presentation.empresas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.domain.model.empresaPG.EmpresaPG
import com.azul.azulVentas.domain.usecases.empresaPG.GetEmpresaPGEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// presentation/viewmodel/EmpresasViewModel.kt
@HiltViewModel
class EmpresasPGViewModel @Inject constructor(
    private val getEmpresaPGEmailUseCase: GetEmpresaPGEmailUseCase,
) : ViewModel() {

    private val _empresasPG = MutableLiveData<List<EmpresaPG>>()
    val empresasPG: LiveData<List<EmpresaPG>> = _empresasPG

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun listEmpresasPG(Email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _empresasPG.value = getEmpresaPGEmailUseCase(Email)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error .. (WS-AZUL): ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}