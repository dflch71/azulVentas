package com.azul.azulVentas.domain.repository.empresaPG

import com.azul.azulVentas.domain.model.empresaPG.EmpresaPG

interface EmpresaPGRepository {
    suspend fun getEmpresaPGNit(Nit_ID: String): List<EmpresaPG>
}