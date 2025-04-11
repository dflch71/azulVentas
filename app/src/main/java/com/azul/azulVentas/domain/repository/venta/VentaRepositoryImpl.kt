package com.azul.azulVentas.domain.repository.venta


import com.azul.azulVentas.data.remote.api.Venta.VentaApiService
import com.azul.azulVentas.data.remote.mappers.ResumenDia.toDomain
import com.azul.azulVentas.data.remote.mappers.ResumenPeriodo.toDomain
import com.azul.azulVentas.data.remote.mappers.ResumenSemana.toDomain
import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VentaRepositoryImpl @Inject constructor(
    private val apiService: VentaApiService
) : VentaRepository {

    override suspend fun getVentaHora(EmpresaID: String): List<ResumenDia> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getVentaHora(EmpresaID)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }

    override suspend fun getVentaSemana(EmpresaID: String): List<ResumenSemana> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getVentaSemana(EmpresaID)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }

    override suspend fun getVentaPeriodo(EmpresaID: String): List<ResumenPeriodo> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getVentaPeriodo(EmpresaID)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }
}