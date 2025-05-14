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

    fun cargarCompraPeriodo(EmpresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true

            // Assign the result of the use case call to a variable
            val result = getCompraPeriodoUseCase(EmpresaID)

            when {
                result.isSuccess -> {
                    val response = result.getOrDefault(emptyList())
                    _compraPeriodo.postValue(response)
                    _error.value = null
                }

                result.isFailure -> {
                    _compraPeriodo.postValue(emptyList())
                    _error.value = result.exceptionOrNull()?.message ?: "Error desconocido"
                }
            }

            _isLoading.value = false

        }
    }
}