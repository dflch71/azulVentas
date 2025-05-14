package com.azul.azulVentas.domain.repository.egreso

import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana

interface EgresoRepository {
    suspend fun getEgresoHora(EmpresaID: String): Result<List<ResumenDia>>
    suspend fun getEgresoSemana(EmpresaID: String): Result<List<ResumenSemana>>
    suspend fun getEgresoPeriodo(EmpresaID: String): Result<List<ResumenPeriodo>>
}