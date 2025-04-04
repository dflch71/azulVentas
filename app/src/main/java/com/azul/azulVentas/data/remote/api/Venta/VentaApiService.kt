package com.azul.azulVentas.data.remote.api.Venta

import com.azul.azulVentas.core.utils.Constants.GET_PATH_VENTA_DIA
import com.azul.azulVentas.core.utils.Constants.GET_PATH_VENTA_SEMANA
import com.azul.azulVentas.data.remote.model.ResumenDia.ResumenDiaResponse
import com.azul.azulVentas.data.remote.model.ResumenSemana.ResumenSemanaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface VentaApiService {
    @GET(GET_PATH_VENTA_DIA)
    suspend fun getVentaHora(@Path("EmpresaID") EmpresaID: String): Response<List<ResumenDiaResponse>>

    @GET(GET_PATH_VENTA_SEMANA)
    suspend fun getVentaSemana(@Path("EmpresaID") EmpresaID: String): Response<List<ResumenSemanaResponse>>
}