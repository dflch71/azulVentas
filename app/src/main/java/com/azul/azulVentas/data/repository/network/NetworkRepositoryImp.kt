package com.azul.azulVentas.data.repository.network

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val connectivityObserver: ConnectivityObserver
) : NetworkRepository {

    override fun isNetworkAvailable(): Flow<Boolean> {
        return connectivityObserver.observeNetworkStatus()
    }

}
