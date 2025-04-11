package com.azul.azulVentas.ui.presentation.venta.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.model.resumenOperaciones.ResumenOperaciones
import com.azul.azulVentas.domain.usecases.venta.GetVentaDiaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import com.azul.azulVentas.core.utils.Utility.Companion.stringToLocalDateTime
import com.azul.azulVentas.core.utils.Utility.Companion.formatDate
import com.azul.azulVentas.core.utils.Utility.Companion.calculateDaysToTargetDate
import com.azul.azulVentas.core.utils.Utility.Companion.formatCurrency
import java.time.LocalDateTime


// presentation/viewmodel/EmpresaViewModel.kt
@HiltViewModel
class VentaDiaViewModel @Inject constructor(
    private val getVentaDiaUseCase: GetVentaDiaUseCase,
) : ViewModel() {

    private val _ventaDia = MutableLiveData<List<ResumenDia>>()
    val ventaDia: LiveData<List<ResumenDia>> = _ventaDia

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun ventaDia(EmpresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _ventaDia.value = getVentaDiaUseCase(EmpresaID)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error .. (WS-AZUL): ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private val _ventaDiaFormatted = mutableStateOf(ResumenOperaciones())
    val ventaDiaFormatted: State<ResumenOperaciones> = _ventaDiaFormatted

    fun ventaDiaView(empresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val response = getVentaDiaUseCase(empresaID)
            _ventaDia.postValue(response)

            if (response.isNotEmpty()) {
                val fecha = response.first().fecha_dia + "T00:00:00"
                val date = stringToLocalDateTime(fecha) ?: LocalDateTime.now()
                val tDia = "${formatDate(response.first().fecha_dia)} - ${calculateDaysToTargetDate(date)} Días"
                //val tSemana = "${formatDate(response.first().fecha_dia)} - ${formatDate(response.last().fecha_dia)}"
                //val tPeriodo = ""

                _ventaDiaFormatted.value = ResumenOperaciones(
                    tituloDia = tDia,
                    //tituloSemana = tSemana,
                    //tituloPeriodo = tPeriodo,
                    total = formatCurrency(response.sumOf { it.sum_hora }),
                    efectivo = formatCurrency(response.sumOf { it.sum_contado }),
                    credito = formatCurrency(response.sumOf { it.sum_credito })
                )
            }
            _isLoading.value = false
        }
    }
}