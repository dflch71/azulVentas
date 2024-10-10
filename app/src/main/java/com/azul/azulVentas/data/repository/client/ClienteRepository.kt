package com.azul.azulVentas.data.repository.client

import com.azul.azulVentas.data.local.dao.ClienteDao
import com.azul.azulVentas.data.local.entities.ClienteEntity
import com.azul.azulVentas.ui.presentation.clientes.model.ClienteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClienteRepository @Inject constructor(private val clienteDao: ClienteDao) {

    val Clientes: Flow<List<ClienteModel>> = clienteDao.getClientes().map { items ->
        items.map {
            ClienteModel(
                it.id,
                it.tipoDocumento,
                it.numeroDocumento,
                it.nombre,
                it.apellido,
                it.telefono,
                it.email
            )
        }
    }

    suspend fun insertCliente(cliente: ClienteModel) {

        clienteDao.insertCliente(cliente.toData())

        //Replace with this
        //clienteDao.insertCliente(
        //    ClienteEntity(
        //        cliente.id,
        //        cliente.tipoDocumento,
        //        cliente.numeroDocumento,
        //        cliente.nombre,
        //        cliente.apellido,
        //        cliente.telefono,
        //        cliente.email
        //    )
        //)
    }

    suspend fun updateCliente(cliente: ClienteModel) {
        clienteDao.updateCliente(cliente.toData())
    }

    suspend fun deleteCliente(cliente: ClienteModel) {
        clienteDao.deleteCliente(cliente.toData())
    }
}

fun ClienteModel.toData(): ClienteEntity {
    return ClienteEntity(
        this.id,
        this.tipoDocumento,
        this.numeroDocumento,
        this.nombre,
        this.apellido,
        this.telefono,
        this.email
    )
}


