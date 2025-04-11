package com.azul.azulVentas.data.remote.mappers.ResumenPeriodo

import com.azul.azulVentas.data.remote.model.ResumenPeriodo.ResumenPeriodoResponse
import com.azul.azulVentas.domain.model.resumenPeriodo.ResumenPeriodo

fun ResumenPeriodoResponse.toDomain(): ResumenPeriodo {
    return ResumenPeriodo(
        tipo = this.Tipo ?: "",
        periodo = this.Periodo ?: 0,
        nom_periodo = this.NomPerido ?: "",
        facturas = this.Facturas ?: 0,
        sum_periodo = this.SumPeriodo ?: 0.0,
        sum_contado = this.SumContado ?: 0.0,
        sum_credito = this.SumCredito ?: 0.0
    )
}