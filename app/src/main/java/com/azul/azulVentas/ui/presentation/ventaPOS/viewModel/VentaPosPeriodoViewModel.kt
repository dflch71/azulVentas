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

    fun cargarVentaPosPeriodo(EmpresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = getVentaPosPeriodoUseCase(EmpresaID)

            when {
                result.isSuccess -> {
                    val response = result.getOrDefault(emptyList())
                    _ventaPosPeriodo.postValue(response)
                    _error.value = null
                    // Puedes realizar acciones adicionales aquí si es necesario
                }

                result.isFailure -> {
                    _ventaPosPeriodo.postValue(emptyList())
                    _error.value = result.exceptionOrNull()?.message ?: "Error desconocido"
                }
            }
            _isLoading.value = false
        }
    }
}