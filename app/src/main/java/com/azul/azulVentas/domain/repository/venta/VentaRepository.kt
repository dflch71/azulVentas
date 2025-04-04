package com.azul.azulVentas.domain.repository.venta

import com.azul.azulVentas.domain.model.empresaPG.EmpresaPG
import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana
import com.azul.azulVentas.domain.model.usuarioEmpresas.UsuarioEmpresas

interface VentaRepository {
    suspend fun getVentaHora(EmpresaID: String): List<ResumenDia>
    suspend fun getVentaSemana(EmpresaID: String): List<ResumenSemana>
}