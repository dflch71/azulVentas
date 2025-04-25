package com.azul.azulVentas.ui.presentation.ventaPOS.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.usecases.ventaPos.GetVentaPosPeriodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// presentation/viewmodel/EmpresaViewModel.kt
@HiltViewModel
class VentaPosPeriodoViewModel @Inject constructor(
    private val getVentaPosPeriodoUseCase: GetVentaPosPeriodoUseCase,
) : ViewModel() {

    private val _ventaPosPeriodo = MutableLiveData<List<ResumenPeriodo>>()
    val ventaPosPeriodo: LiveData<List<ResumenPeriodo>> = _ventaPosPeriodo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun ventaPosPeriodo(EmpresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _ventaPosPeriodo.value = getVentaPosPeriodoUseCase(EmpresaID)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error .. (WS-AZUL): ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}