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
    private val getVentaPeriodoUseCase: GetVentaPeriodoUseCase
) : ViewModel() {

    private val _ventaPeriodo = MutableLiveData<List<ResumenPeriodo>>()
    val ventaPeriodo: LiveData<List<ResumenPeriodo>> = _ventaPeriodo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarVentaPeriodo(EmpresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true

            // Assign the result of the use case call to a variable
            val result = getVentaPeriodoUseCase(EmpresaID)

            when {
                result.isSuccess -> {
                    val response = result.getOrDefault(emptyList())
                    _ventaPeriodo.postValue(response)
                    _error.value = null
                    // Puedes realizar acciones adicionales aquí si es necesario
                }

                result.isFailure -> {
                    _ventaPeriodo.postValue(emptyList())
                    _error.value = result.exceptionOrNull()?.message ?: "Error desconocido"
                }
            }

            _isLoading.value = false

        }
    }

}