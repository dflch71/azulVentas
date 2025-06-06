package com.azul.azulVentas.ui.presentation.venta.viewmodel

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
import com.azul.azulVentas.domain.usecases.venta.GetVentaDiaFechaUseCase
import com.azul.azulVentas.domain.usecases.venta.GetVentaDiaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

// presentation/viewmodel/EmpresaViewModel.kt
@HiltViewModel
class VentaDiaFechaViewModel @Inject constructor(
    private val getVentaDiaFechaUseCase: GetVentaDiaFechaUseCase
) : ViewModel() {

    private val _ventaDiaFecha = MutableLiveData<List<ResumenDia>>()
    val ventaDiaFecha: LiveData<List<ResumenDia>> = _ventaDiaFecha

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _ventaDiaFechaFormatted = mutableStateOf(ResumenOperaciones())
    val ventaDiaFechaFormatted: State<ResumenOperaciones> = _ventaDiaFechaFormatted

    fun cargarVentaDiaFecha(empresaID: String, fecha: String) {
        viewModelScope.launch {
            _isLoading.value = true

            // Assign the result of the use case call to a variable
            val result = getVentaDiaFechaUseCase(empresaID, fecha)

            when {
                result.isSuccess -> {
                    val response = result.getOrDefault(emptyList())
                    _ventaDiaFecha.postValue(response)
                    _error.value = null

                    _ventaDiaFechaFormatted.value = ResumenOperaciones(
                        tituloDia = "",
                        total = "",
                        efectivo = "",
                        credito = ""
                    )

                    if (response.isNotEmpty()) {
                        val fecha = response.first().fecha_dia + "T00:00:00"
                        val date = stringToLocalDateTime(fecha) ?: LocalDateTime.now()
                        val tDia = "${formatDate(response.first().fecha_dia)} - ${calculateDaysToTargetDate(date)} Días"

                        _ventaDiaFechaFormatted.value = ResumenOperaciones(
                            tituloDia = tDia,
                            total = formatCurrency(response.sumOf { it.sum_hora }),
                            efectivo = formatCurrency(response.sumOf { it.sum_contado }),
                            credito = formatCurrency(response.sumOf { it.sum_credito }),
                            facturas = (response.sumOf { it.sum_factura }).toString()
                        )
                    }
                }

                result.isFailure -> {
                    _ventaDiaFecha.postValue(emptyList())
                    _error.value = result.exceptionOrNull()?.message ?: "Error desconocido"
                }
            }
            _isLoading.value = false
        }
    }

    fun listarVentaDiaFecha(empresaID: String, fecha: String) {
        viewModelScope.launch {
            _isLoading.value = true

            // Assign the result of the use case call to a variable
            val result = getVentaDiaFechaUseCase(empresaID, fecha)

            when {
                result.isSuccess -> {
                    val response = result.getOrDefault(emptyList())
                    _ventaDiaFecha.postValue(response)
                    _error.value = null
                }

                result.isFailure -> {
                    _ventaDiaFecha.postValue(emptyList())
                    _error.value = result.exceptionOrNull()?.message ?: "Error desconocido"
                }
            }
            _isLoading.value = false
        }
    }
}