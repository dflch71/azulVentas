package com.azul.azulVentas.data.remote.api.Venta

import com.azul.azulVentas.core.utils.Constants.GET_PATH_VALIDAR_USUARIO_EMAIL
import com.azul.azulVentas.core.utils.Constants.GET_PATH_VENTA_DIA
import com.azul.azulVentas.core.utils.Constants.GET_PATH_VENTA_DIA_FECHA
import com.azul.azulVentas.core.utils.Constants.GET_PATH_VENTA_PERIODO
import com.azul.azulVentas.core.utils.Constants.GET_PATH_VENTA_SEMANA
import com.azul.azulVentas.data.remote.model.EmpresaEmail.EmpresaEmailResponse
import com.azul.azulVentas.data.remote.model.ResumenDia.ResumenDiaResponse
import com.azul.azulVentas.data.remote.model.ResumenPeriodo.ResumenPeriodoResponse
import com.azul.azulVentas.data.remote.model.ResumenSemana.ResumenSemanaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface VentaApiService {
    @GET(GET_PATH_VENTA_DIA)
    suspend fun getVentaHora(@Path("EmpresaID") EmpresaID: String): Response<List<ResumenDiaResponse>>

    @GET(GET_PATH_VENTA_DIA_FECHA)
    suspend fun getVentaHoraFecha(@Path("EmpresaID") EmpresaID: String,  @Path("Fecha") Fecha: String): Response<List<ResumenDiaResponse>>

    @GET(GET_PATH_VENTA_SEMANA)
    suspend fun getVentaSemana(@Path("EmpresaID") EmpresaID: String): Response<List<ResumenSemanaResponse>>

    @GET(GET_PATH_VENTA_PERIODO)
    suspend fun getVentaPeriodo(@Path("EmpresaID") EmpresaID: String): Response<List<ResumenPeriodoResponse>>
}