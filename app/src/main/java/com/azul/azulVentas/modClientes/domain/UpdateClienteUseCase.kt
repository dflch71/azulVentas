package com.azul.azulVentas.modClientes.domain

import com.azul.azulVentas.modClientes.data.ClienteRepository
import com.azul.azulVentas.modClientes.ui.model.ClienteModel
import javax.inject.Inject

class UpdateClienteUseCase @Inject constructor(
    private val clienteRepository: ClienteRepository
) {
    suspend operator fun invoke(clienteModel: ClienteModel) = clienteRepository.updateCliente(clienteModel)
}