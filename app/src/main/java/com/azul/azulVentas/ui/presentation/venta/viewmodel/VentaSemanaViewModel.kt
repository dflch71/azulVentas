package com.azul.azulVentas.ui.presentation.venta.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.domain.model.resumenOperaciones.ResumenOperaciones
import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana
import com.azul.azulVentas.domain.usecases.venta.GetVentaSemanaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import com.azul.azulVentas.core.utils.Utility.Companion.formatCurrency
import com.azul.azulVentas.core.utils.Utility.Companion.formatDate

// presentation/viewmodel/EmpresaViewModel.kt
@HiltViewModel
class VentaSemanaViewModel @Inject constructor(
    private val getVentaSemanaUseCase: GetVentaSemanaUseCase,
) : ViewModel() {

    private val _ventaSemana = MutableLiveData<List<ResumenSemana>>()
    val ventaSemana: LiveData<List<ResumenSemana>> = _ventaSemana

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun ventaSemana(EmpresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _ventaSemana.value = getVentaSemanaUseCase(EmpresaID)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error .. (WS-AZUL): ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private val _ventaSemanaFormatted = mutableStateOf(ResumenOperaciones())
    val ventaSemanaFormatted: State<ResumenOperaciones> = _ventaSemanaFormatted

    fun ventaSemanaView(empresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val response = getVentaSemanaUseCase(empresaID)
            _ventaSemana.postValue(response)

            if (response.isNotEmpty()) {
                val titulo = "${formatDate(response.first().fecha_dia)} - ${formatDate(response.last().fecha_dia)}"
                _ventaSemanaFormatted.value = ResumenOperaciones(
                    tituloPeriodos = titulo,
                    total = formatCurrency(response.sumOf { it.sum_dia }),
                    efectivo = formatCurrency(response.sumOf { it.sum_contado }),
                    credito = formatCurrency(response.sumOf { it.sum_credito })
                )
            }
            _isLoading.value = false
        }
    }
}