package com.azul.azulVentas.ui.presentation.network.sync

import androidx.compose.material3.SnackbarHostState
import com.azul.azulVentas.ui.presentation.network.viewmodel.NetworkViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NetworkSyncManager(
    private val networkViewModel: NetworkViewModel,
    private val snackbarHostState: SnackbarHostState,
    private val coroutineScope: CoroutineScope,
    private val onRefresh: () -> Unit,
    private val refreshIntervalMs: Long = 600_000 // 10 minutos
) {

    fun startObserving() {
        // Escuchar reconexión
        coroutineScope.launch {
            networkViewModel.reconnectionEvent.collect {
                snackbarHostState.showSnackbar("Conexión restablecida. Cargando datos…")
                onRefresh()
            }
        }

        // Actualización periódica
        coroutineScope.launch {
            while (true) {
                delay(refreshIntervalMs)
                if (networkViewModel.networkStatus.value) {
                    snackbarHostState.showSnackbar("Actualización automática de datos")
                    onRefresh()
                }
            }
        }

        coroutineScope.launch {
            networkViewModel.networkStatus.collect { isConnected ->
                if (!isConnected) {
                    snackbarHostState.showSnackbar("Sin conexión a internet")
                }
            }
        }
    }
}
