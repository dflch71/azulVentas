package com.azul.azulVentas.data.remote.api.VentaPos

import com.azul.azulVentas.core.utils.Constants.GET_PATH_VENTA_DIA_FECHA
import com.azul.azulVentas.core.utils.Constants.GET_PATH_VENTA_POS_DIA
import com.azul.azulVentas.core.utils.Constants.GET_PATH_VENTA_POS_DIA_FECHA
import com.azul.azulVentas.core.utils.Constants.GET_PATH_VENTA_POS_PERIODO
import com.azul.azulVentas.core.utils.Constants.GET_PATH_VENTA_POS_SEMANA
import com.azul.azulVentas.data.remote.model.ResumenDia.ResumenDiaResponse
import com.azul.azulVentas.data.remote.model.ResumenPeriodo.ResumenPeriodoResponse
import com.azul.azulVentas.data.remote.model.ResumenSemana.ResumenSemanaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface VentaPosApiService {
    @GET(GET_PATH_VENTA_POS_DIA)
    suspend fun getVentaPosHora(@Path("EmpresaID") EmpresaID: String): Response<List<ResumenDiaResponse>>

    @GET(GET_PATH_VENTA_POS_DIA_FECHA)
    suspend fun getVentaPosHoraFecha(@Path("EmpresaID") EmpresaID: String,  @Path("Fecha") Fecha: String): Response<List<ResumenDiaResponse>>

    @GET(GET_PATH_VENTA_POS_SEMANA)
    suspend fun getVentaPosSemana(@Path("EmpresaID") EmpresaID: String): Response<List<ResumenSemanaResponse>>

    @GET(GET_PATH_VENTA_POS_PERIODO)
    suspend fun getVentaPosPeriodo(@Path("EmpresaID") EmpresaID: String): Response<List<ResumenPeriodoResponse>>
}