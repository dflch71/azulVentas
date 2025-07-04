package com.azul.azulVentas.ui.presentation.venta.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.core.utils.Utility.Companion.formatCurrency
import com.azul.azulVentas.core.utils.Utility.Companion.formatDate
import com.azul.azulVentas.domain.model.resumenOperaciones.ResumenOperaciones
import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana
import com.azul.azulVentas.domain.usecases.venta.GetVentaSemanaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// presentation/viewmodel/EmpresaViewModel.kt
@HiltViewModel
class VentaSemanaViewModel @Inject constructor(
    private val getVentaSemanaUseCase: GetVentaSemanaUseCase
) : ViewModel() {

    private val _ventaSemana = MutableLiveData<List<ResumenSemana>>()
    val ventaSemana: LiveData<List<ResumenSemana>> = _ventaSemana

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _ventaSemanaFormatted = mutableStateOf(ResumenOperaciones())
    val ventaSemanaFormatted: State<ResumenOperaciones> = _ventaSemanaFormatted

    fun cargarVentaSemana(empresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = getVentaSemanaUseCase(empresaID)

            when {
                result.isSuccess -> {
                    val response = result.getOrDefault(emptyList())
                    _ventaSemana.postValue(response)
                    _error.value = null

                    _ventaSemanaFormatted.value = ResumenOperaciones(
                        tituloSemana = "",
                        total = "",
                        efectivo = "",
                        credito = ""
                    )

                    if (response.isNotEmpty()) {
                        val titulo = "${formatDate(response.first().fecha_dia)} a ${formatDate(response.last().fecha_dia)}"
                        _ventaSemanaFormatted.value = ResumenOperaciones(
                            tituloSemana = titulo,
                            total = formatCurrency(response.sumOf { it.sum_dia }),
                            facturas = response.sumOf { it.facturas }.toString(),
                            efectivo = formatCurrency(response.sumOf { it.sum_contado }),
                            credito = formatCurrency(response.sumOf { it.sum_credito })
                        )
                    }
                }

                result.isFailure -> {
                    _ventaSemana.postValue(emptyList())
                    _error.value = result.exceptionOrNull()?.message ?: "Error desconocido"
                }
            }
            _isLoading.value = false
        }
    }

    //Lsitado muestra completo los valores, pero me muestra varios valores por un dia
    fun listarVentaSemana(empresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true

            // Assign the result of the use case call to a variable
            val result = getVentaSemanaUseCase(empresaID)

            when {
                result.isSuccess -> {
                    val response = result.getOrDefault(emptyList())
                    _ventaSemana.postValue(response)
                    _error.value = null
                }

                result.isFailure -> {
                    _ventaSemana.postValue(emptyList())
                    _error.value = result.exceptionOrNull()?.message ?: "Error desconocido"
                }
            }
            _isLoading.value = false
        }
    }

    //Listado donde me totaliza los valores x día
    fun listarVentaSemanaAgrupada(empresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = getVentaSemanaUseCase(empresaID)

            when {
                result.isSuccess -> {
                    val response = result.getOrDefault(emptyList())

                    val resumenPorFecha = response
                        .groupBy { it.fecha_dia }
                        .map { (fecha, ventasDelDia) ->
                            ventasDelDia.reduce { acc, venta ->
                                acc.copy(
                                    facturas = acc.facturas + venta.facturas,
                                    sum_dia = acc.sum_dia + venta.sum_dia,
                                    sum_credito = acc.sum_credito + venta.sum_credito,
                                    sum_contado = acc.sum_contado + venta.sum_contado
                                )
                            }
                        }
                        .sortedBy { it.fecha_dia } // Si quieres que aparezcan ordenados por fecha

                    _ventaSemana.postValue(resumenPorFecha)
                    _error.value = null
                }

                result.isFailure -> {
                    _ventaSemana.postValue(emptyList())
                    _error.value = result.exceptionOrNull()?.message ?: "Error desconocido"
                }
            }
            _isLoading.value = false
        }
    }

}