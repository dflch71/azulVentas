package com.azul.azulVentas.data.remote.api.EmpresaPG

import com.azul.azulVentas.core.utils.Constants.GET_PATH_EMPRESA
import com.azul.azulVentas.data.remote.model.EmpresaPG.EmpresaPGResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

// data/remote/EmpresaApiService.kt
interface EmpresaPGApiService {
    @GET(GET_PATH_EMPRESA)
    suspend fun getEmpresaPGNit(@Path("Nit_ID") Nit_ID: String): Response<List<EmpresaPGResponse>>
}