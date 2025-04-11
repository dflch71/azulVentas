package com.azul.azulVentas.ui.presentation.venta.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.usecases.venta.GetVentaPeriodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// presentation/viewmodel/EmpresaViewModel.kt
@HiltViewModel
class VentaPeriodoViewModel @Inject constructor(
    private val getVentaPeriodoUseCase: GetVentaPeriodoUseCase,
) : ViewModel() {

    private val _ventaPeriodo = MutableLiveData<List<ResumenPeriodo>>()
    val ventaPeriodo: LiveData<List<ResumenPeriodo>> = _ventaPeriodo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun ventaPeriodo(EmpresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _ventaPeriodo.value = getVentaPeriodoUseCase(EmpresaID)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error .. (WS-AZUL): ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}