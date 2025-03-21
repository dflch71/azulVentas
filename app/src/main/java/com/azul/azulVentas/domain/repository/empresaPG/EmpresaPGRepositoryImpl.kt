package com.azul.azulVentas.domain.repository.empresaPG


import com.azul.azulVentas.data.remote.api.EmpresaPG.EmpresaPGApiService
import com.azul.azulVentas.data.remote.mappers.empresaPG.toDomain
import com.azul.azulVentas.domain.model.empresaPG.EmpresaPG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// data/repository/EmpresaRepositoryImpl.kt
class EmpresaPGRepositoryImpl @Inject constructor(
    private val apiService: EmpresaPGApiService
) : EmpresaPGRepository {
    override suspend fun getEmpresaPGNit(Nit_ID: String): List<EmpresaPG> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getEmpresaPGNit(Nit_ID)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }

    override suspend fun getEmpresaPGEmail(Email: String): List<EmpresaPG> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getEmpresasPGEmail(Email)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }
}