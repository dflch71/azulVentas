package com.azul.azulVentas.domain.usecases.cliente

import com.azul.azulVentas.data.repository.ClienteRepository
import com.azul.azulVentas.ui.presentation.clientes.model.ClienteModel
import javax.inject.Inject

class UpdateClienteUseCase @Inject constructor(
    private val clienteRepository: ClienteRepository
) {
    suspend operator fun invoke(clienteModel: ClienteModel) = clienteRepository.updateCliente(clienteModel)
}