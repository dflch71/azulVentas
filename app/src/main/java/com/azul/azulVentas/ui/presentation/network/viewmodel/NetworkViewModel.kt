package com.azul.azulVentas.ui.presentation.network.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.data.repository.network.NetworkStatusTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val networkStatusTracker: NetworkStatusTracker
) : ViewModel() {

    val networkStatus: StateFlow<Boolean> = networkStatusTracker.networkStatus

    private val _reconnectionEvent = MutableSharedFlow<Unit>()
    val reconnectionEvent: SharedFlow<Unit> = _reconnectionEvent.asSharedFlow()

    init {
        observeNetworkStatus()
    }

    private fun observeNetworkStatus() {
        viewModelScope.launch {
            var wasDisconnected = false
            networkStatus.collect { isConnected ->
                if (isConnected && wasDisconnected) {
                    _reconnectionEvent.emit(Unit)  // Emitir evento de reconexión
                }
                wasDisconnected = !isConnected
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        networkStatusTracker.unregisterCallback()
    }
}

/*
@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val networkStatusTracker: NetworkStatusTracker
) : ViewModel() {

    // Estado de la red observable
    val networkStatus: StateFlow<Boolean> = networkStatusTracker.networkStatus

    private val _onReconnect = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val onReconnect: SharedFlow<Unit> = _onReconnect

    private var wasPreviouslyDisconnected = false


    // Init para inicializar la verificación de red desde el arranque
    init {
        observeNetworkStatus()
    }

    private fun observeNetworkStatus() {
        // Opcional si necesitas hacer algo más aquí
        viewModelScope.launch {
            networkStatus.collect() { isConnected ->

                if (isConnected && wasPreviouslyDisconnected) {
                    _onReconnect.tryEmit(Unit)
                }
                wasPreviouslyDisconnected = !isConnected

                /*
                if (isConnected) {
                    // Hacer algo si hay una red disponible
                    // Sincronizar datos

                }  else {
                    // Hacer algo si no hay una red disponible

                }*/
            }
        }
    }

    // Desregistrar el callback cuando el ViewModel se limpie (opcional)
    override fun onCleared() {
        super.onCleared()
        networkStatusTracker.unregisterCallback()
    }
}*/

