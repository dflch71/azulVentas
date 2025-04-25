package com.azul.azulVentas.ui.presentation.ventaPOS.view

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.azul.azulVentas.core.utils.Utility.Companion.ShowRealTimeClock
import com.azul.azulVentas.core.utils.Utility.Companion.formatCurrency
import com.azul.azulVentas.ui.presentation.venta.component.CardResumen
import com.azul.azulVentas.ui.presentation.venta.component.TipoVentaCard
import com.azul.azulVentas.ui.presentation.ventaPOS.viewModel.VentaPosDiaViewModel
import com.azul.azulVentas.ui.presentation.ventaPOS.viewModel.VentaPosPeriodoViewModel
import com.azul.azulVentas.ui.presentation.ventaPOS.viewModel.VentaPosSemanaViewModel
import com.azul.azulVentas.ui.theme.DarkTextColor

@Composable
fun VentaPosScreen(
    empresaID: String,
    ventaPosDiaViewModel: VentaPosDiaViewModel,
    ventaPosSemanaViewModel: VentaPosSemanaViewModel,
    ventaPosPeriodoViewModel: VentaPosPeriodoViewModel,
) {

    val ventaDiaFmt by ventaPosDiaViewModel.ventaDiaFormatted
    val ventaSemanaFmt by ventaPosSemanaViewModel.ventaSemanaFormatted

    //Venta Periodo
    val ventaPosPeriodo by ventaPosPeriodoViewModel.ventaPosPeriodo.observeAsState(initial = emptyList())
    val isLoadingVentaPeriodo by ventaPosPeriodoViewModel.isLoading.collectAsState()
    val errorVentaPosPeriodo by ventaPosPeriodoViewModel.error.collectAsState()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    LaunchedEffect(key1 = true) {
        ventaPosDiaViewModel.ventaPosDiaView(empresaID)
        ventaPosSemanaViewModel.ventaPosSemanaView(empresaID)
        ventaPosPeriodoViewModel.ventaPosPeriodo(empresaID)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Ventas POS",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = DarkTextColor
        )

        Spacer(modifier = Modifier.height(4.dp))
        ShowRealTimeClock()

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.weight(0.33f)
        ){
            if (ventaDiaFmt.tituloDia.isEmpty()) {
                CardResumen(
                    titulo = "Día: no se reportan ventas Pos",
                    total = "$ 0",
                    efectivo = "$ 0",
                    credito = "$ 0",
                    tipo = TipoVentaCard.DIA
                )
            } else {
                CardResumen(
                    titulo = "Día: ${ventaDiaFmt.tituloDia}",
                    total = ventaDiaFmt.total,
                    efectivo = ventaDiaFmt.efectivo,
                    credito = ventaDiaFmt.credito,
                    tipo = TipoVentaCard.DIA
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.weight(0.33f)
        ){
            if (ventaSemanaFmt.tituloSemana.isEmpty()) {
                CardResumen(
                    titulo = "Semana: no se reportan ventas Pos",
                    total = "$ 0",
                    efectivo = "$ 0",
                    credito = "$ 0",
                    tipo = TipoVentaCard.SEMANA
                )
            } else {
                CardResumen(
                    titulo = "Semana: ${ventaSemanaFmt.tituloSemana}",
                    total = ventaSemanaFmt.total,
                    efectivo = ventaSemanaFmt.efectivo,
                    credito = ventaSemanaFmt.credito,
                    tipo = TipoVentaCard.SEMANA
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.34f)
        ) {
            if (ventaPosPeriodo.isEmpty()) {
                CardResumen(
                    titulo = "Periodo: no se reportan ventas Pos",
                    total = "$ 0",
                    efectivo = "$ 0",
                    credito = "$ 0",
                    tipo = TipoVentaCard.PERIODO
                )
            } else {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(0.dp),
                ) {
                    items(ventaPosPeriodo.reversed()) { venta ->
                        CardResumen(
                            modifier = Modifier.width(screenWidth / 1.15f),
                            titulo = "Periodo: ${venta.nom_periodo}",
                            total = formatCurrency(venta.sum_periodo),
                            efectivo = formatCurrency(venta.sum_contado),
                            credito = formatCurrency(venta.sum_credito),
                            tipo = TipoVentaCard.PERIODO
                        )
                    }
                }
            }
        }
    }
}


