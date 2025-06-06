package com.azul.azulVentas.domain.repository.venta

import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana

interface VentaRepository {
    suspend fun getVentaHora(EmpresaID: String): Result<List<ResumenDia>>
    suspend fun getVentaHoraFecha(EmpresaID: String, Fecha: String): Result<List<ResumenDia>>
    suspend fun getVentaSemana(EmpresaID: String): Result<List<ResumenSemana>>
    suspend fun getVentaPeriodo(EmpresaID: String): Result<List<ResumenPeriodo>>
}