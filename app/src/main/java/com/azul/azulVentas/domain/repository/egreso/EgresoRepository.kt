package com.azul.azulVentas.domain.repository.egreso

import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana

interface EgresoRepository {
    suspend fun getEgresoHora(EmpresaID: String): List<ResumenDia>
    suspend fun getEgresoSemana(EmpresaID: String): List<ResumenSemana>
    suspend fun getEgresoPeriodo(EmpresaID: String): List<ResumenPeriodo>
}