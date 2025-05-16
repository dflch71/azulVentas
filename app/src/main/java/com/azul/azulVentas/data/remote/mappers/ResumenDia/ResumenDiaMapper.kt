package com.azul.azulVentas.data.remote.mappers.ResumenDia

import com.azul.azulVentas.data.remote.model.ResumenDia.ResumenDiaResponse
import com.azul.azulVentas.domain.model.resumenDia.ResumenDia

fun ResumenDiaResponse.toDomain(): ResumenDia {
    return ResumenDia(
        tipo = this.Tipo ?: "",
        fecha_dia = this.FechaDia ?: "",
        hora_dia = this.HoraDia ?: 0,
        hora_am_pm = this.HoraAmPm ?: "",
        tipo_pago = this.TipoPago ?: "",
        sum_factura = this.SumFactura ?: 0,
        sum_hora = this.SumHora ?: 0.0,
        sum_contado = this.SumContado ?: 0.0,
        sum_credito = this.SumCredito ?: 0.0,
    )
}