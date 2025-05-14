package com.azul.azulVentas.ui.presentation.egreso.viewmodel

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
import com.azul.azulVentas.domain.usecases.egreso.GetEgresoSemanaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// presentation/viewmodel/EmpresaViewModel.kt
@HiltViewModel
class EgresoSemanaViewModel @Inject constructor(
    private val getEgresoSemanaUseCase: GetEgresoSemanaUseCase,
) : ViewModel() {

    private val _egresoSemana = MutableLiveData<List<ResumenSemana>>()
    val egresoSemana: LiveData<List<ResumenSemana>> = _egresoSemana

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _egresoSemanaFormatted = mutableStateOf(ResumenOperaciones())
    val egresoSemanaFormatted: State<ResumenOperaciones> = _egresoSemanaFormatted

    fun cargarEgresoSemana(empresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = getEgresoSemanaUseCase(empresaID)

            when {
                result.isSuccess -> {
                    val response = result.getOrDefault(emptyList())
                    _egresoSemana.postValue(response)
                    _error.value = null

                    _egresoSemanaFormatted.value = ResumenOperaciones(
                        tituloSemana = "",
                        total = "",
                        efectivo = "",
                        credito = "",
                        facturas = ""
                    )

                    if (response.isNotEmpty()) {
                        val titulo =
                            "${formatDate(response.first().fecha_dia)} a ${formatDate(response.last().fecha_dia)}"
                        _egresoSemanaFormatted.value = ResumenOperaciones(
                            tituloSemana = titulo,
                            total = formatCurrency(response.sumOf { it.sum_dia }),
                            efectivo = formatCurrency(response.sumOf { it.sum_contado }),
                            credito = formatCurrency(response.sumOf { it.sum_credito }),
                            facturas = response.sumOf { it.facturas }.toString()
                        )
                    }
                }

                result.isFailure -> {
                    _egresoSemana.postValue(emptyList())
                    _error.value = result.exceptionOrNull()?.message ?: "Error desconocido"
                }
            }
            _isLoading.value = false
        }
    }
}