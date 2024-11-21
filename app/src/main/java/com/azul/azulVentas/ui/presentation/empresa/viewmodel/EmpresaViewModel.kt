package com.azul.azulVentas.ui.presentation.empresa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.domain.model.empresa.Empresa
import com.azul.azulVentas.domain.usecases.empresa.AddEmpresaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmpresaViewModel @Inject constructor(
    private val addEmpresaUseCase: AddEmpresaUseCase
) : ViewModel() {

    private val _estadoRegistro = MutableLiveData<Result<Boolean>?>()
    val estadoRegistro: MutableLiveData<Result<Boolean>?> = _estadoRegistro

    fun registrarEmpresa(
        nitEmpresa: String,
        nomEmpresa: String,
        direccion: String,
        ciudad: String,
        departamento: String,
        picEmpresa: String,
        email: String,
        repLegal: String,
        telefono: String
    ){
        viewModelScope.launch {
            val empresa = Empresa(nitEmpresa, nomEmpresa, direccion, ciudad, departamento, picEmpresa, email, repLegal, telefono)
            _estadoRegistro.value = addEmpresaUseCase(empresa)
        }
    }

    fun limpiarEstadoRegistro() {
        _estadoRegistro.postValue(null)
    }


}