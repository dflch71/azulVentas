package com.azul.azulVentas.data.remote.mappers.ResumenSemana

import com.azul.azulVentas.data.remote.model.ResumenSemana.ResumenSemanaResponse
import com.azul.azulVentas.domain.model.resumenSemana.ResumenSemana

fun ResumenSemanaResponse.toDomain(): ResumenSemana {
    return ResumenSemana(
        tipo = this.Tipo ?: "",
        fecha_dia = this.FechaDia ?: "",
        dia_mes = this.DiaMes ?: 0,
        dia_semana = this.DiaSemana ?: "",
        tipo_pago = this.TipoPago ?: "",
        facturas = this.Facturas ?: 0,
        sum_dia = this.SumDia ?: 0.0,
        sum_contado = this.SumContado ?: 0.0,
        sum_credito = this.SumCredito ?: 0.0
    )
}