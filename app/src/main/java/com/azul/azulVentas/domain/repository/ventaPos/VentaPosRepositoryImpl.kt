package com.azul.azulVentas.domain.repository.ventaPos


import com.azul.azulVentas.data.remote.api.VentaPos.VentaPosApiService
import com.azul.azulVentas.data.remote.mappers.ResumenDia.toDomain
import com.azul.azulVentas.data.remote.mappers.ResumenPeriodo.toDomain
import com.azul.azulVentas.data.remote.mappers.ResumenSemana.toDomain
import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VentaPosRepositoryImpl @Inject constructor(
    private val apiService: VentaPosApiService
) : VentaPosRepository {

    override suspend fun getVentaPosHora(EmpresaID: String): List<ResumenDia> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getVentaPosHora(EmpresaID)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }

    override suspend fun getVentaPosSemana(EmpresaID: String): List<ResumenSemana> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getVentaPosSemana(EmpresaID)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }

    override suspend fun getVentaPosPeriodo(EmpresaID: String): List<ResumenPeriodo> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getVentaPosPeriodo(EmpresaID)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }
}