package com.azul.azulVentas.domain.usecases.network

import com.azul.azulVentas.data.repository.network.NetworkRepository
import javax.inject.Inject

class CheckNetworkUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke(): Boolean {
        return networkRepository.isNetworkAvailable()
    }
}