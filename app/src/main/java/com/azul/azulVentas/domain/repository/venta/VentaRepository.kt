package com.azul.azulVentas.domain.repository.venta

import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana

interface VentaRepository {
    suspend fun getVentaHora(EmpresaID: String): List<ResumenDia>
    suspend fun getVentaSemana(EmpresaID: String): List<ResumenSemana>
    suspend fun getVentaPeriodo(EmpresaID: String): List<ResumenPeriodo>
}