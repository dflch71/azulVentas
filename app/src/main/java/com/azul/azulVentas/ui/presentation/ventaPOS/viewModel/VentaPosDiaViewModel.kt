package com.azul.azulVentas.ui.presentation.ventaPOS.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.core.utils.Utility.Companion.calculateDaysToTargetDate
import com.azul.azulVentas.core.utils.Utility.Companion.formatCurrency
import com.azul.azulVentas.core.utils.Utility.Companion.formatDate
import com.azul.azulVentas.core.utils.Utility.Companion.stringToLocalDateTime
import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.model.resumenOperaciones.ResumenOperaciones
import com.azul.azulVentas.domain.usecases.ventaPos.GetVentaPosDiaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


// presentation/viewmodel/EmpresaViewModel.kt
@HiltViewModel
class VentaPosDiaViewModel @Inject constructor(
    private val getVentaPosDiaUseCase: GetVentaPosDiaUseCase,
) : ViewModel() {

    private val _ventaPosDia = MutableLiveData<List<ResumenDia>>()
    val ventaPosDia: LiveData<List<ResumenDia>> = _ventaPosDia

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _ventaPosDiaFormatted = mutableStateOf(ResumenOperaciones())
    val ventaPosDiaFormatted: State<ResumenOperaciones> = _ventaPosDiaFormatted

    fun cargarVentaPosDia(empresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = getVentaPosDiaUseCase(empresaID)

            when {
                result.isSuccess -> {
                    val response = result.getOrDefault(emptyList())
                    _ventaPosDia.postValue(response)
                    _error.value = null

                    _ventaPosDiaFormatted.value = ResumenOperaciones(
                        tituloDia = "",
                        total = "",
                        efectivo = "",
                        credito = ""
                    )

                    if (response.isNotEmpty()) {
                        val fecha = response.first().fecha_dia + "T00:00:00"
                        val date = stringToLocalDateTime(fecha) ?: LocalDateTime.now()
                        val tDia = "${formatDate(response.first().fecha_dia)} - ${
                            calculateDaysToTargetDate(date)
                        } Días"

                        _ventaPosDiaFormatted.value = ResumenOperaciones(
                            tituloDia = tDia,
                            total = formatCurrency(response.sumOf { it.sum_hora }),
                            efectivo = formatCurrency(response.sumOf { it.sum_contado }),
                            credito = formatCurrency(response.sumOf { it.sum_credito }),
                            facturas = response.sumOf { it.sum_factura }.toString()
                        )
                    }
                }

                result.isFailure -> {
                    _ventaPosDia.postValue(emptyList())
                    _error.value = result.exceptionOrNull()?.message ?: "Error desconocido"
                }
            }
            _isLoading.value = false
        }
    }
}