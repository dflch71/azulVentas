package com.azul.azulVentas.ui.presentation.compra.viewmodel

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
import com.azul.azulVentas.domain.usecases.compra.GetCompraSemanaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// presentation/viewmodel/EmpresaViewModel.kt
@HiltViewModel
class CompraSemanaViewModel @Inject constructor(
    private val getCompraSemanaUseCase: GetCompraSemanaUseCase,
) : ViewModel() {

    private val _compraSemana = MutableLiveData<List<ResumenSemana>>()
    val compraSemana: LiveData<List<ResumenSemana>> = _compraSemana

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _compraSemanaFormatted = mutableStateOf(ResumenOperaciones())
    val compraSemanaFormatted: State<ResumenOperaciones> = _compraSemanaFormatted

    fun cargarCompraSemana(empresaID: String) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = getCompraSemanaUseCase(empresaID)

            when {
                result.isSuccess -> {
                    val response = result.getOrDefault(emptyList())
                    _compraSemana.postValue(response)
                    _error.value = null

                    _compraSemanaFormatted.value = ResumenOperaciones(
                        tituloSemana = "",
                        total = "",
                        efectivo = "",
                        credito = ""
                    )

                    if (response.isNotEmpty()) {
                        val titulo =
                            "${formatDate(response.first().fecha_dia)} a ${formatDate(response.last().fecha_dia)}"
                        _compraSemanaFormatted.value = ResumenOperaciones(
                            tituloSemana = titulo,
                            total = formatCurrency(response.sumOf { it.sum_dia }),
                            efectivo = formatCurrency(response.sumOf { it.sum_contado }),
                            credito = formatCurrency(response.sumOf { it.sum_credito }),
                            facturas = response.sumOf { it.facturas }.toString()
                        )
                    }
                }

                result.isFailure -> {
                    _compraSemana.postValue(emptyList())
                    _error.value = result.exceptionOrNull()?.message ?: "Error desconocido"
                }
            }
            _isLoading.value = false
        }
    }
}