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
import java.io.IOException
import java.net.ConnectException
import javax.inject.Inject

class VentaRepositoryImpl @Inject constructor(
    private val apiService: VentaApiService
) : VentaRepository {

    override suspend fun getVentaHora(EmpresaID: String): Result<List<ResumenDia>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getVentaHora(EmpresaID)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!.map { it.toDomain() })
                } else {
                    Result.failure(Exception("Error del servidor: ${response.code()}"))
                }
            } catch (e: ConnectException) {
                Result.failure(Exception("No se pudo conectar al servidor"))
            } catch (e: IOException) {
                Result.failure(Exception("Error de red: ${e.localizedMessage}"))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getVentaSemana(EmpresaID: String): Result<List<ResumenSemana>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getVentaSemana(EmpresaID)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!.map { it.toDomain() })
                } else {
                    Result.failure(Exception("Error del servidor: ${response.code()}"))
                }
            } catch (e: ConnectException) {
                Result.failure(Exception("No se pudo conectar al servidor"))
            } catch (e: IOException) {
                Result.failure(Exception("Error de red: ${e.localizedMessage}"))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getVentaPeriodo(EmpresaID: String): Result<List<ResumenPeriodo>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getVentaPeriodo(EmpresaID)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!.map { it.toDomain() })
                } else {
                    Result.failure(Exception("Error del servidor: ${response.code()}"))
                }
            } catch (e: ConnectException) {
                Result.failure(Exception("No se pudo conectar al servidor"))
            } catch (e: IOException) {
                Result.failure(Exception("Error de red: ${e.localizedMessage}"))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}