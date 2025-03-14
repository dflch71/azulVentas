package com.azul.azulVentas.ui.presentation.empresaFB.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.domain.model.empresaFB.EmpresaFB
import com.azul.azulVentas.domain.usecases.empresaFB.BuscarEmpresaFBPorNitUseCase
import com.azul.azulVentas.domain.usecases.empresaFB.ObtenerEmpresasFBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmpresaFBViewModel @Inject constructor(
    private val obtenerEmpresasUseCase: ObtenerEmpresasFBUseCase,
    private val buscarEmpresaPorNitUseCase: BuscarEmpresaFBPorNitUseCase
) : ViewModel() {

    // Sealed class for state management
    sealed class EmpresaState {
        object Loading : EmpresaState()
        data class Success(val empresas: List<EmpresaFB>) : EmpresaState()
        data class Error(val message: String) : EmpresaState()
    }

    sealed class EmpresaEncontradaState {
        object Loading : EmpresaEncontradaState()
        data class Success(val empresa: EmpresaFB?) : EmpresaEncontradaState()
        data class Error(val message: String) : EmpresaEncontradaState()
        data class Empty(val message: String) : EmpresaEncontradaState()
    }

    private val _empresasState = MutableStateFlow<EmpresaState>(EmpresaState.Loading)
    val empresasState: StateFlow<EmpresaState> = _empresasState

    private val _empresaEncontradaState =
        MutableStateFlow<EmpresaEncontradaState>(EmpresaEncontradaState.Loading)
    val empresaEncontradaState: StateFlow<EmpresaEncontradaState> = _empresaEncontradaState

    init {
        //obtenerEmpresas() // Load companies on init
    }

    private fun obtenerEmpresas() {
        viewModelScope.launch {
            _empresasState.value = EmpresaState.Loading
            try {
                obtenerEmpresasUseCase().collect { empresas ->
                    _empresasState.value = EmpresaState.Success(empresas)
                }
            } catch (e: Exception) {
                _empresasState.value = EmpresaState.Error("Error loading companies")
            }
        }
    }

    fun buscarEmpresaPorNit(nit: String) {
        viewModelScope.launch {
            _empresaEncontradaState.value = EmpresaEncontradaState.Loading
            try {
                buscarEmpresaPorNitUseCase(nit).collect { empresa ->
                    _empresaEncontradaState.value = EmpresaEncontradaState.Success(empresa)
                    if ((empresa == null) && (nit.isNotEmpty())) {
                        _empresaEncontradaState.value = EmpresaEncontradaState.Empty("Empresa no Encontrada: $nit")
                    }
                }
            } catch (e: Exception) {
                _empresaEncontradaState.value =
                    EmpresaEncontradaState.Error("Error finding company")
            }
        }
    }
}