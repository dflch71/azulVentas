package com.azul.azulVentas.ui.presentation.egreso.viewmodel

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
import com.azul.azulVentas.domain.usecases.egreso.GetEgresoDiaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

// presentation/viewmodel/EmpresaViewModel.kt
@HiltViewModel
class EgresoDiaViewModel @Inject constructor(
    private val getEgresoDiaUseCase: GetEgresoDiaUseCase,
) : ViewModel() {

    private val _egresoDia = MutableLiveData<List<ResumenDia>>()
    val egresoDia: LiveData<List<ResumenDia>> = _egresoDia

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _egresoDiaFormatted = mutableStateOf(ResumenOperaciones())
    val egresoDiaFormatted: State<ResumenOperaciones> = _egresoDiaFormatted

    fun cargarEgresoDia(empresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = getEgresoDiaUseCase(empresaID)

            when {
                result.isSuccess -> {
                    val response = result.getOrDefault(emptyList())
                    _egresoDia.postValue(response)
                    _error.value = null

                    _egresoDiaFormatted.value = ResumenOperaciones(
                        tituloDia = "",
                        total = "",
                        efectivo = "",
                        credito = "",
                        facturas = "",
                    )

                    if (response.isNotEmpty()) {
                        val fecha = response.first().fecha_dia + "T00:00:00"
                        val date = stringToLocalDateTime(fecha) ?: LocalDateTime.now()
                        val tDia = "${formatDate(response.first().fecha_dia)} - ${
                            calculateDaysToTargetDate(date)
                        } Días"

                        _egresoDiaFormatted.value = ResumenOperaciones(
                            tituloDia = tDia,
                            total = formatCurrency(response.sumOf { it.sum_hora }),
                            efectivo = formatCurrency(response.sumOf { it.sum_contado }),
                            credito = formatCurrency(response.sumOf { it.sum_credito }),
                            facturas = response.sumOf { it.sum_factura }.toString(),
                        )
                    }
                }

                result.isFailure -> {
                    _egresoDia.postValue(emptyList())
                    _error.value = result.exceptionOrNull()?.message ?: "Error desconocido"
                }
            }

            _isLoading.value = false
        }
    }
}