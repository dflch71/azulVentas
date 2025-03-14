package com.azul.azulVentas.data.remote.mappers.empresaPG

import com.azul.azulVentas.data.remote.model.EmpresaPG.EmpresaPGResponse
import com.azul.azulVentas.domain.model.empresaPG.EmpresaPG

fun EmpresaPGResponse.toDomain(): EmpresaPG {
    return EmpresaPG(
        EMPRESA_ID = this.emp_Id ?: 0,
        EMP_RAZON_SOCIAL = this.emp_RazonSocial ?: "",
        EMP_TERCERO = this.emp_Tercero ?: "",
        EMP_DIRECCION = this.emp_Direccion ?: "",
        EMP_BARRIO = emp_Barrio ?: "",
        EMP_CIUDAD = emp_Ciudad ?: "",
        EMP_TELEFONO = emp_Telefono ?: "",
        EMP_EMAIL = emp_Email ?: "",
    )
}


