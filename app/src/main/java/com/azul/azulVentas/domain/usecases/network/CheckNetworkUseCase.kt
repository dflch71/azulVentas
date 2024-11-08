package com.azul.azulVentas.domain.usecases.network

import androidx.lifecycle.LiveData
import com.azul.azulVentas.data.repository.network.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckNetworkUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return networkRepository.isNetworkAvailable()
    }
}