package com.azul.azulVentas.ui.presentation.empresaPG.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.domain.model.empresaPG.EmpresaPG
import com.azul.azulVentas.domain.usecases.empresaPG.GetEmpresaPGNitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// presentation/viewmodel/EmpresaViewModel.kt
@HiltViewModel
class EmpresaPGViewModel @Inject constructor(
    private val getEmpresaPGNitUseCase: GetEmpresaPGNitUseCase
) : ViewModel() {

    private val _empresaPG = MutableLiveData<List<EmpresaPG>>()
    val empresaPG: LiveData<List<EmpresaPG>> = _empresaPG

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun findEmpresaPG(NitID: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _empresaPG.value = getEmpresaPGNitUseCase(NitID)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error .. (WS-AZUL): ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}