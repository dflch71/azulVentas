package com.azul.azulVentas.domain.usecases

import com.azul.azulVentas.data.repository.ClienteRepository
import com.azul.azulVentas.ui.clientes.model.ClienteModel
import javax.inject.Inject

class DeleteClienteUseCase @Inject constructor(
    private val clienteRepository: ClienteRepository
) {
    suspend operator fun invoke(clienteModel: ClienteModel) = clienteRepository.deleteCliente(clienteModel)
}