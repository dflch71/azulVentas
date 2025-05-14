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
import java.io.IOException
import java.net.ConnectException
import javax.inject.Inject

class CompraRepositoryImpl @Inject constructor(
    private val apiService: CompraApiService
) : CompraRepository {

    override suspend fun getCompraHora(EmpresaID: String): Result<List<ResumenDia>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCompraHora(EmpresaID)
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

    override suspend fun getCompraSemana(EmpresaID: String): Result<List<ResumenSemana>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCompraSemana(EmpresaID)
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

    override suspend fun getCompraPeriodo(EmpresaID: String): Result<List<ResumenPeriodo>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCompraPeriodo(EmpresaID)
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