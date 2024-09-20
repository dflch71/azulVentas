package com.azul.azulVentas.modClientes.domain

import com.azul.azulVentas.modClientes.data.ClienteDao
import com.azul.azulVentas.modClientes.data.ClienteRepository
import com.azul.azulVentas.modClientes.ui.model.ClienteModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClientesUseCase @Inject constructor(
    private val clienteRepository: ClienteRepository
) {
    operator fun invoke(): Flow<List<ClienteModel>> = clienteRepository.Clientes
}