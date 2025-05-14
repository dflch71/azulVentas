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
import java.io.IOException
import java.net.ConnectException
import javax.inject.Inject

class VentaPosRepositoryImpl @Inject constructor(
    private val apiService: VentaPosApiService
) : VentaPosRepository {

    override suspend fun getVentaPosHora(EmpresaID: String): Result<List<ResumenDia>> {
        return withContext(Dispatchers.IO) {

            try {
                val response = apiService.getVentaPosHora(EmpresaID)
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

    override suspend fun getVentaPosSemana(EmpresaID: String): Result<List<ResumenSemana>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getVentaPosSemana(EmpresaID)
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

    override suspend fun getVentaPosPeriodo(EmpresaID: String): Result<List<ResumenPeriodo>> {

        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getVentaPosPeriodo(EmpresaID)
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