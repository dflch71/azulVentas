package com.azul.azulVentas.data.remote.api.Compra

import com.azul.azulVentas.core.utils.Constants.GET_PATH_COMPRA_DIA
import com.azul.azulVentas.core.utils.Constants.GET_PATH_COMPRA_PERIODO
import com.azul.azulVentas.core.utils.Constants.GET_PATH_COMPRA_SEMANA
import com.azul.azulVentas.data.remote.model.ResumenDia.ResumenDiaResponse
import com.azul.azulVentas.data.remote.model.ResumenPeriodo.ResumenPeriodoResponse
import com.azul.azulVentas.data.remote.model.ResumenSemana.ResumenSemanaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CompraApiService {
    @GET(GET_PATH_COMPRA_DIA)
    suspend fun getCompraHora(@Path("EmpresaID") EmpresaID: String): Response<List<ResumenDiaResponse>>

    @GET(GET_PATH_COMPRA_SEMANA)
    suspend fun getCompraSemana(@Path("EmpresaID") EmpresaID: String): Response<List<ResumenSemanaResponse>>

    @GET(GET_PATH_COMPRA_PERIODO)
    suspend fun getCompraPeriodo(@Path("EmpresaID") EmpresaID: String): Response<List<ResumenPeriodoResponse>>
}