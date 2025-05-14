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
import java.io.IOException
import java.net.ConnectException
import javax.inject.Inject

class EgresoRepositoryImpl @Inject constructor(
    private val apiService: EgresoApiService
) : EgresoRepository {

    override suspend fun getEgresoHora(EmpresaID: String): Result<List<ResumenDia>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getEgresoHora(EmpresaID)
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

    override suspend fun getEgresoSemana(EmpresaID: String): Result<List<ResumenSemana>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getEgresoSemana(EmpresaID)
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

    override suspend fun getEgresoPeriodo(EmpresaID: String): Result<List<ResumenPeriodo>> {
        return withContext(Dispatchers.IO) {

            try {
                val response = apiService.getEgresoPeriodo(EmpresaID)
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