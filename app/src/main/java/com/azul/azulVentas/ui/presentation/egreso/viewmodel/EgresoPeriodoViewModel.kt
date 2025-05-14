package com.azul.azulEgresos.ui.presentation.egreso.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.usecases.egreso.GetEgresoPeriodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// presentation/viewmodel/EmpresaViewModel.kt
@HiltViewModel
class EgresoPeriodoViewModel @Inject constructor(
    private val getEgresoPeriodoUseCase: GetEgresoPeriodoUseCase,
) : ViewModel() {

    private val _egresoPeriodo = MutableLiveData<List<ResumenPeriodo>>()
    val egresoPeriodo: LiveData<List<ResumenPeriodo>> = _egresoPeriodo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun egresoPeriodo(EmpresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = getEgresoPeriodoUseCase(EmpresaID)

            when {
                result.isSuccess -> {
                    val response = result.getOrDefault(emptyList())
                    _egresoPeriodo.postValue(response)
                    _error.value = null
                    // Puedes realizar acciones adicionales aquí si es necesario
                }

                result.isFailure -> {
                    _egresoPeriodo.postValue(emptyList())
                    _error.value = result.exceptionOrNull()?.message ?: "Error desconocido"
                }
            }
            _isLoading.value = false
        }
    }
}