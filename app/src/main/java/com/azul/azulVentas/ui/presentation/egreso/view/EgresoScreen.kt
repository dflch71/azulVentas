package com.azul.azulVentas.ui.presentation.egreso.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.azul.azulEgresos.ui.presentation.egreso.viewmodel.EgresoPeriodoViewModel
import com.azul.azulVentas.core.utils.Utility.Companion.ShowRealTimeClock
import com.azul.azulVentas.core.utils.Utility.Companion.formatCurrency
import com.azul.azulVentas.domain.model.resumenOperaciones.ResumenOperaciones
import com.azul.azulVentas.ui.presentation.egreso.viewmodel.EgresoDiaViewModel
import com.azul.azulVentas.ui.presentation.egreso.viewmodel.EgresoSemanaViewModel
import com.azul.azulVentas.ui.presentation.venta.component.CardResumen
import com.azul.azulVentas.ui.presentation.venta.component.TipoVentaCard
import com.azul.azulVentas.ui.presentation.egreso.component.CardEgreso
import com.azul.azulVentas.ui.presentation.egreso.component.TipoCard
import com.azul.azulVentas.ui.theme.DarkTextColor

@Composable
fun EgresoScreen(
    empresaID: String,
    nombreEmpresa: String,
    egresoDiaViewModel: EgresoDiaViewModel,
    egresoSemanaViewModel: EgresoSemanaViewModel,
    egresoPeriodoViewModel: EgresoPeriodoViewModel,
) {

    val egresoDiaFmt by egresoDiaViewModel.egresoDiaFormatted
    val egresoSemanaFmt by egresoSemanaViewModel.egresoSemanaFormatted
    val egresoPeriodo by egresoPeriodoViewModel.egresoPeriodo.observeAsState(initial = emptyList())

    val isLoadingEgresoPeriodo by egresoPeriodoViewModel.isLoading.collectAsState()
    val errorEgresoPeriodo by egresoPeriodoViewModel.error.collectAsState()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    LaunchedEffect(key1 = true) {
        egresoDiaViewModel.egresoDiaView(empresaID)
        egresoSemanaViewModel.egresoSemanaView(empresaID)
        egresoPeriodoViewModel.egresoPeriodo(empresaID)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {

        Text(
            text = "Egresos",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = DarkTextColor
        )

        Text(
            text = "$nombreEmpresa - $empresaID",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.DarkGray,
            maxLines = 1
        )

        ShowRealTimeClock()

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.weight(0.33f)
        ){
            if (egresoDiaFmt.tituloDia.isEmpty()) {
                CardEgreso(
                    titulo = "Día: no se reportan egresos",
                    total = "$ 0",
                    facturas = "0",
                    tipo = TipoCard.DIA
                )
            } else {
                CardEgreso(
                    titulo = "Día: ${egresoDiaFmt.tituloDia}",
                    total = egresoDiaFmt.total,
                    facturas = egresoDiaFmt.facturas,
                    tipo = TipoCard.DIA
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.weight(0.33f)
        ){
            if (egresoSemanaFmt.tituloSemana.isEmpty()) {
                CardEgreso(
                    titulo = "Últ 7 Días: no se reportan egresos",
                    total = "$ 0",
                    facturas = "0",
                    tipo = TipoCard.SEMANA
                )
            } else {
                CardEgreso(
                    titulo = "Últ 7 Días: ${egresoSemanaFmt.tituloSemana}",
                    total = egresoSemanaFmt.total,
                    facturas = egresoSemanaFmt.facturas,
                    tipo = TipoCard.SEMANA
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.34f)
        ) {
            if (egresoPeriodo.isEmpty()) {
                CardEgreso(
                    titulo = "Periodo: no se reportan egresos",
                    total = "$ 0",
                    facturas = "0",
                    tipo = TipoCard.PERIODO
                )
            } else {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(0.dp),
                ) {
                    items(egresoPeriodo.reversed()) { egreso ->
                        CardEgreso(
                            modifier = Modifier.width(screenWidth / 1.15f),
                            titulo = "Periodo: ${egreso.nom_periodo}",
                            total = formatCurrency(egreso.sum_periodo),
                            facturas = egreso.facturas.toString(),
                            tipo = TipoCard.PERIODO
                        )
                    }
                }
            }
        }
    }
}


