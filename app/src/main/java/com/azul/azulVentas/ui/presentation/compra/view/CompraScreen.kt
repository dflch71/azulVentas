package com.azul.azulVentas.ui.presentation.compra.view

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
import com.azul.azulVentas.ui.presentation.compra.viewmodel.CompraDiaViewModel
import com.azul.azulVentas.ui.presentation.compra.viewmodel.CompraPeriodoViewModel
import com.azul.azulVentas.ui.presentation.compra.viewmodel.CompraSemanaViewModel
import com.azul.azulVentas.ui.presentation.venta.component.CardResumen
import com.azul.azulVentas.ui.presentation.venta.component.TipoVentaCard
import com.azul.azulVentas.ui.theme.DarkTextColor

@Composable
fun CompraScreen(
    empresaID: String,
    compraDiaViewModel: CompraDiaViewModel,
    compraSemanaViewModel: CompraSemanaViewModel,
    compraPeriodoViewModel: CompraPeriodoViewModel,
) {

    val compraDiaFmt by compraDiaViewModel.compraDiaFormatted
    val compraSemanaFmt by compraSemanaViewModel.compraSemanaFormatted

    //Egreso Periodo
    val compraPeriodo by compraPeriodoViewModel.compraPeriodo.observeAsState(initial = emptyList())
    val isLoadingEgresoPeriodo by compraPeriodoViewModel.isLoading.collectAsState()
    val errorEgresoPeriodo by compraPeriodoViewModel.error.collectAsState()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    LaunchedEffect(key1 = true) {
        compraDiaViewModel.compraDiaView(empresaID)
        compraSemanaViewModel.compraSemanaView(empresaID)
        compraPeriodoViewModel.compraPeriodo(empresaID)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Compras",
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
            if (compraDiaFmt.tituloDia.isEmpty()) {
                CardResumen(
                    titulo = "Día: no se reportan compras",
                    total = "$ 0",
                    efectivo = "$ 0",
                    credito = "$ 0",
                    tipo = TipoVentaCard.DIA
                )
            } else {
                CardResumen(
                    titulo = "Día: ${compraDiaFmt.tituloDia}",
                    total = compraDiaFmt.total,
                    efectivo = compraDiaFmt.efectivo,
                    credito = compraDiaFmt.credito,
                    tipo = TipoVentaCard.DIA
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.weight(0.33f)
        ){
            if (compraSemanaFmt.tituloSemana.isEmpty()) {
                CardResumen(
                    titulo = "Semana: no se reportan compras",
                    total = "$ 0",
                    efectivo = "$ 0",
                    credito = "$ 0",
                    tipo = TipoVentaCard.SEMANA
                )
            } else {
                CardResumen(
                    titulo = "Semana: ${compraSemanaFmt.tituloSemana}",
                    total = compraSemanaFmt.total,
                    efectivo = compraSemanaFmt.efectivo,
                    credito = compraSemanaFmt.credito,
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
            if (compraPeriodo.isEmpty()) {
                CardResumen(
                    titulo = "Periodo: no se reportan compras",
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
                    items(compraPeriodo.reversed()) { compra ->
                        CardResumen(
                            modifier = Modifier.width(screenWidth / 1.15f),
                            titulo = "Periodo: ${compra.nom_periodo}",
                            total = formatCurrency(compra.sum_periodo),
                            efectivo = formatCurrency(compra.sum_contado),
                            credito = formatCurrency(compra.sum_credito),
                            tipo = TipoVentaCard.PERIODO
                        )
                    }
                }
            }
        }
    }
}


