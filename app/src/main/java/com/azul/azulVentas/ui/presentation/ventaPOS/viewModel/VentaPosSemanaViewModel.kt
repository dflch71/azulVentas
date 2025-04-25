package com.azul.azulVentas.ui.presentation.ventaPOS.viewModel

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
import com.azul.azulVentas.domain.usecases.ventaPos.GetVentaPosSemanaUseCase

// presentation/viewmodel/EmpresaViewModel.kt
@HiltViewModel
class VentaPosSemanaViewModel @Inject constructor(
    private val getVentaPosSemanaUseCase: GetVentaPosSemanaUseCase,
) : ViewModel() {

    private val _ventaPosSemana = MutableLiveData<List<ResumenSemana>>()
    val ventaPosSemana: LiveData<List<ResumenSemana>> = _ventaPosSemana

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun ventaPosSemana(EmpresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _ventaPosSemana.value = getVentaPosSemanaUseCase(EmpresaID)
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

    fun ventaPosSemanaView(empresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val response = getVentaPosSemanaUseCase(empresaID)
            _ventaPosSemana.postValue(response)

            if (response.isNotEmpty()) {
                val titulo = "${formatDate(response.first().fecha_dia)} a ${formatDate(response.last().fecha_dia)}"
                _ventaSemanaFormatted.value = ResumenOperaciones(
                    tituloSemana = titulo,
                    total = formatCurrency(response.sumOf { it.sum_dia }),
                    efectivo = formatCurrency(response.sumOf { it.sum_contado }),
                    credito = formatCurrency(response.sumOf { it.sum_credito })
                )
            }
            _isLoading.value = false
        }
    }
}