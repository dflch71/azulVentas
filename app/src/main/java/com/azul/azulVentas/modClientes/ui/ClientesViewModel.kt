package com.azul.azulVentas.modClientes.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.modClientes.domain.AddClienteUseCase
import com.azul.azulVentas.modClientes.domain.DeleteClienteUseCase
import com.azul.azulVentas.modClientes.domain.GetClientesUseCase
import com.azul.azulVentas.modClientes.domain.UpdateClienteUseCase
import com.azul.azulVentas.modClientes.ui.ClientesUiState.Success
import com.azul.azulVentas.modClientes.ui.model.ClienteModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientesViewModel @Inject constructor (

    private val addClienteUseCase: AddClienteUseCase,
    private val updateClienteUseCase: UpdateClienteUseCase,
    private val deleteClienteUseCase: DeleteClienteUseCase,

    getClientesUseCase: GetClientesUseCase


) : ViewModel() {

    //Get data from database
    val uiState: StateFlow<ClientesUiState> = getClientesUseCase().map (::Success)
        .catch { ClientesUiState.Error(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ClientesUiState.Loading
        )


    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onDialogShow() {
        _showDialog.value = true
    }

    private val _showDialogUpd = MutableLiveData<Boolean>()
    val showDialogUpd: LiveData<Boolean> = _showDialogUpd

    fun onDialogCloseUpd() {
        _showDialogUpd.value = false
    }

    fun onDialogShowUpd() {
        _showDialogUpd.value = true
    }

    fun onTipoDocumentoCreated(tipoDocumento: String) {
        Log.i("CLIENTES", tipoDocumento)
    }

    fun onNumeroDocumentoCreated(numeroDocumento: String) {
        Log.i("CLIENTES", numeroDocumento)
    }

    fun onNameCreated(name: String) {
        Log.i("CLIENTES", name)
    }

    fun onLastNameCreated(lastName: String) {
        Log.i("CLIENTES", lastName)
    }

    fun onPhoneCreated(phone: String) {
        Log.i("CLIENTES", phone)
    }

    fun onEmailCreated(email: String) {
        Log.i("CLIENTES", email)
    }

    fun onclienteCreated(tipoDocumento: String, numeroDocumento: String, name: String, lastName: String, phone: String, email: String) {

        //_clientes.add(ClienteModel(System.currentTimeMillis().hashCode(), tipoDocumento, numeroDocumento, name, lastName, phone, email))
        Log.i("CLIENTES", "$tipoDocumento, $numeroDocumento, $name, $lastName, $phone, $email")

        //Insert cliente in database
        viewModelScope.launch {
            addClienteUseCase(
                ClienteModel(
                    System.currentTimeMillis().hashCode(),
                    tipoDocumento,
                    numeroDocumento,
                    name,
                    lastName,
                    phone,
                    email
                )
            )
        }

    }

    fun onclienteUpdate(id : Int, tipoDocumento: String, numeroDocumento: String, name: String, lastName: String, phone: String, email: String) {
        viewModelScope.launch {
            updateClienteUseCase(
                ClienteModel(
                    id,
                    tipoDocumento,
                    numeroDocumento,
                    name,
                    lastName,
                    phone,
                    email
                )
            )
        }
    }

    fun onClienteRemove(clienteModel: ClienteModel) {
        //Delete client
        //val cliente = _clientes.find { it.id == clienteModel.id }
        //_clientes.remove(cliente)

        viewModelScope.launch {
            deleteClienteUseCase(clienteModel)
        }
    }

}