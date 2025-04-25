package com.azul.azulVentas.domain.repository.egreso


import com.azul.azulVentas.data.remote.api.Egreso.EgresoApiService
import com.azul.azulVentas.data.remote.mappers.ResumenDia.toDomain
import com.azul.azulVentas.data.remote.mappers.ResumenPeriodo.toDomain
import com.azul.azulVentas.data.remote.mappers.ResumenSemana.toDomain
import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EgresoRepositoryImpl @Inject constructor(
    private val apiService: EgresoApiService
) : EgresoRepository {

    override suspend fun getEgresoHora(EmpresaID: String): List<ResumenDia> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getEgresoHora(EmpresaID)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }

    override suspend fun getEgresoSemana(EmpresaID: String): List<ResumenSemana> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getEgresoSemana(EmpresaID)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }

    override suspend fun getEgresoPeriodo(EmpresaID: String): List<ResumenPeriodo> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getEgresoPeriodo(EmpresaID)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }
}