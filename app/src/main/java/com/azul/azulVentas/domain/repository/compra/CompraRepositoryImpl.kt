package com.azul.azulVentas.domain.repository.compra


import com.azul.azulVentas.data.remote.api.Compra.CompraApiService
import com.azul.azulVentas.data.remote.mappers.ResumenDia.toDomain
import com.azul.azulVentas.data.remote.mappers.ResumenPeriodo.toDomain
import com.azul.azulVentas.data.remote.mappers.ResumenSemana.toDomain
import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CompraRepositoryImpl @Inject constructor(
    private val apiService: CompraApiService
) : CompraRepository {

    override suspend fun getCompraHora(EmpresaID: String): List<ResumenDia> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getCompraHora(EmpresaID)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }

    override suspend fun getCompraSemana(EmpresaID: String): List<ResumenSemana> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getCompraSemana(EmpresaID)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }

    override suspend fun getCompraPeriodo(EmpresaID: String): List<ResumenPeriodo> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getCompraPeriodo(EmpresaID)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }
}