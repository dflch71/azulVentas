package com.azul.azulVentas.ui.presentation.compra.viewmodel

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
import com.azul.azulVentas.domain.usecases.compra.GetCompraDiaUseCase
import com.azul.azulVentas.domain.usecases.ventaPos.GetVentaPosDiaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


// presentation/viewmodel/EmpresaViewModel.kt
@HiltViewModel
class CompraDiaViewModel @Inject constructor(
    private val getCompraDiaUseCase: GetCompraDiaUseCase,
) : ViewModel() {

    private val _compraDia = MutableLiveData<List<ResumenDia>>()
    val compraDia: LiveData<List<ResumenDia>> = _compraDia

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun compraDia(EmpresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _compraDia.value = getCompraDiaUseCase(EmpresaID)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error .. (WS-AZUL): ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private val _compraFormatted = mutableStateOf(ResumenOperaciones())
    val compraDiaFormatted: State<ResumenOperaciones> = _compraFormatted

    fun compraDiaView(empresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val response = getCompraDiaUseCase(empresaID)
            _compraDia.postValue(response)

            if (response.isNotEmpty()) {
                val fecha = response.first().fecha_dia + "T00:00:00"
                val date = stringToLocalDateTime(fecha) ?: LocalDateTime.now()
                val tDia = "${formatDate(response.first().fecha_dia)} - ${calculateDaysToTargetDate(date)} Días"
                //val tSemana = "${formatDate(response.first().fecha_dia)} - ${formatDate(response.last().fecha_dia)}"
                //val tPeriodo = ""

                _compraFormatted.value = ResumenOperaciones(
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