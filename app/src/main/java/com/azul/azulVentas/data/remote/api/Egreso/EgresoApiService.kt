package com.azul.azulVentas.data.remote.api.Egreso

import com.azul.azulVentas.core.utils.Constants.GET_PATH_EGRESO_DIA
import com.azul.azulVentas.core.utils.Constants.GET_PATH_EGRESO_PERIODO
import com.azul.azulVentas.core.utils.Constants.GET_PATH_EGRESO_SEMANA
import com.azul.azulVentas.data.remote.model.ResumenDia.ResumenDiaResponse
import com.azul.azulVentas.data.remote.model.ResumenPeriodo.ResumenPeriodoResponse
import com.azul.azulVentas.data.remote.model.ResumenSemana.ResumenSemanaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EgresoApiService {
    @GET(GET_PATH_EGRESO_DIA)
    suspend fun getEgresoHora(@Path("EmpresaID") EmpresaID: String): Response<List<ResumenDiaResponse>>

    @GET(GET_PATH_EGRESO_SEMANA)
    suspend fun getEgresoSemana(@Path("EmpresaID") EmpresaID: String): Response<List<ResumenSemanaResponse>>

    @GET(GET_PATH_EGRESO_PERIODO)
    suspend fun getEgresoPeriodo(@Path("EmpresaID") EmpresaID: String): Response<List<ResumenPeriodoResponse>>
}