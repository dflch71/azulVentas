package com.azul.azulVentas.domain.usecases.cliente

import com.azul.azulVentas.data.repository.ClienteRepository
import com.azul.azulVentas.ui.presentation.clientes.model.ClienteModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClientesUseCase @Inject constructor(
    private val clienteRepository: ClienteRepository
) {
    operator fun invoke(): Flow<List<ClienteModel>> = clienteRepository.Clientes
}