package com.azul.azulVentas.domain.repository.compra

import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo
import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana

interface CompraRepository {
    suspend fun getCompraHora(EmpresaID: String): List<ResumenDia>
    suspend fun getCompraSemana(EmpresaID: String): List<ResumenSemana>
    suspend fun getCompraPeriodo(EmpresaID: String): List<ResumenPeriodo>
}