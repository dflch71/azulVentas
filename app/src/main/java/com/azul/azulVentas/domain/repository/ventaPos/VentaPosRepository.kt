package com.azul.azulVentas.domain.repository.ventaPos

import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana

interface VentaPosRepository {
    suspend fun getVentaPosHora(EmpresaID: String): Result<List<ResumenDia>>
    suspend fun getVentaPosHoraFecha(EmpresaID: String, Fecha: String): Result<List<ResumenDia>>
    suspend fun getVentaPosSemana(EmpresaID: String): Result<List<ResumenSemana>>
    suspend fun getVentaPosPeriodo(EmpresaID: String): Result<List<ResumenPeriodo>>
}