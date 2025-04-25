package com.azul.azulVentas.ui.presentation.compra.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.usecases.compra.GetCompraPeriodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// presentation/viewmodel/EmpresaViewModel.kt
@HiltViewModel
class CompraPeriodoViewModel @Inject constructor(
    private val getCompraPeriodoUseCase: GetCompraPeriodoUseCase,
) : ViewModel() {

    private val _compraPeriodo = MutableLiveData<List<ResumenPeriodo>>()
    val compraPeriodo: LiveData<List<ResumenPeriodo>> = _compraPeriodo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun compraPeriodo(EmpresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _compraPeriodo.value = getCompraPeriodoUseCase(EmpresaID)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error .. (WS-AZUL): ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}