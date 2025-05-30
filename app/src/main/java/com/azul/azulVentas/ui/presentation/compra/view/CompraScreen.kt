package com.azul.azulVentas.ui.presentation.compra.view

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.azul.azulVentas.core.utils.Utility.Companion.ShowRealTimeClock
import com.azul.azulVentas.core.utils.Utility.Companion.formatCurrency
import com.azul.azulVentas.ui.components.ErrorDialog
import com.azul.azulVentas.ui.presentation.compra.viewmodel.CompraDiaViewModel
import com.azul.azulVentas.ui.presentation.compra.viewmodel.CompraPeriodoViewModel
import com.azul.azulVentas.ui.presentation.compra.viewmodel.CompraSemanaViewModel
import com.azul.azulVentas.ui.presentation.container.NavGraph
import com.azul.azulVentas.ui.presentation.network.sync.NetworkSyncManager
import com.azul.azulVentas.ui.presentation.network.viewmodel.NetworkViewModel
import com.azul.azulVentas.ui.presentation.venta.component.CardResumen
import com.azul.azulVentas.ui.presentation.venta.component.TipoVentaCard
import com.azul.azulVentas.ui.theme.DarkTextColor
import kotlinx.coroutines.launch

@Composable
fun CompraScreen(
    navController: NavController,
    empresaID: String,
    nombreEmpresa: String,
    compraDiaViewModel: CompraDiaViewModel,
    compraSemanaViewModel: CompraSemanaViewModel,
    compraPeriodoViewModel: CompraPeriodoViewModel,
    networkViewModel: NetworkViewModel
) {

    val compraDiaFmt by compraDiaViewModel.compraDiaFormatted
    val compraSemanaFmt by compraSemanaViewModel.compraSemanaFormatted
    val compraPeriodo by compraPeriodoViewModel.compraPeriodo.observeAsState(initial = emptyList())

    val isLoadingCompraPeriodo by compraPeriodoViewModel.isLoading.collectAsState()

    val errorCompraDia by compraDiaViewModel.error.collectAsState()
    val errorCompraSemana by compraSemanaViewModel.error.collectAsState()
    val errorCompraPeriodo by compraPeriodoViewModel.error.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val snackbarHostState = remember { SnackbarHostState() }  // Para el Snackbar
    val isNetworkAvailable by networkViewModel.networkStatus.collectAsState()
    val scope = rememberCoroutineScope()

    fun cargarDatos() {
        compraDiaViewModel.cargarCompraDia(empresaID)
        compraSemanaViewModel.cargarCompraSemana(empresaID)
        compraPeriodoViewModel.cargarCompraPeriodo(empresaID)
    }

    // ✅ Al primer render, cargar si hay conexión
    LaunchedEffect(key1 = isNetworkAvailable) {
        if (isNetworkAvailable) { cargarDatos() }
    }

    // ✅ Mostrar el Snackbar si hay un error
    LaunchedEffect(errorCompraDia, errorCompraSemana, errorCompraPeriodo) {
        val error = errorCompraDia ?: errorCompraSemana ?: errorCompraPeriodo
        error?.let {
            showErrorDialog = true
            snackbarHostState.showSnackbar("Error: $it")
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        LaunchedEffect(Unit) {
            NetworkSyncManager(
                networkViewModel = networkViewModel,
                snackbarHostState = snackbarHostState,
                coroutineScope = scope,
                onRefresh = { cargarDatos() },
                //refreshIntervalMs = 10_000 // 10 segundos opcional por defecto es 10 Min
            ).startObserving()
        }

        // Conditionally show the dialog
        if ((showErrorDialog) &&
            (errorCompraDia != null || errorCompraSemana != null || errorCompraPeriodo != null)
            ) {
            ErrorDialog(
                onDismissRequest = { showErrorDialog = false },
                onConfirmation = {
                    // Close the dialog when confirmed
                    showErrorDialog = false
                    // You can add other actions here if needed, e.g., retry loading data
                },
                dialogTitle = "ERROR COMPRA",
                dialogText = "$errorCompraDia",
                icon = Icons.Default.Info
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            if (isLoadingCompraPeriodo) { LinearProgressIndicator(modifier = Modifier.fillMaxWidth()) }

            Text(
                text = "Compras",
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                item{
                    //Para poder enviar el argumento a la pantalla de detalle de venta
                    val fechaCodificada = Uri.encode(compraDiaFmt.tituloDia)
                    CardResumen(
                        titulo = "Día: ${compraDiaFmt.tituloDia}",
                        total = compraDiaFmt.total,
                        facturas = compraDiaFmt.facturas,
                        efectivo = compraDiaFmt.efectivo,
                        credito = compraDiaFmt.credito,
                        tipoResumen = "",
                        tipo = TipoVentaCard.DIA,
                        onClick = {
                            if (compraDiaFmt.facturas != "0") {
                                navController.navigate(
                                    NavGraph.DiaEstadistica.createRoute(
                                        "Compra",
                                        empresaID,
                                        "Compras Día",
                                        fechaCodificada,
                                        compraDiaFmt.efectivo,
                                        compraDiaFmt.credito,
                                        compraDiaFmt.total
                                    )
                                )
                            }  else {
                                // Mostrar Snackbar dentro de una corrutina
                                scope.launch {
                                    snackbarHostState.showSnackbar("No hay datos disponibles para mostrar")
                                }
                            }
                        }
                    )
                }

                item{
                    CardResumen(
                        titulo = "Últ 7 Días: ${compraSemanaFmt.tituloSemana}",
                        total = compraSemanaFmt.total,
                        facturas = compraSemanaFmt.facturas,
                        efectivo = compraSemanaFmt.efectivo,
                        credito = compraSemanaFmt.credito,
                        tipoResumen = "",
                        tipo = TipoVentaCard.SEMANA
                    )
                }

                item{
                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(compraPeriodo.reversed()) { compra ->
                            CardResumen(
                                modifier = Modifier
                                    .width(screenWidth / 1.0f)
                                    .padding(end = 8.dp),
                                titulo = "Periodo: ${compra.nom_periodo}",
                                total = formatCurrency(compra.sum_periodo),
                                facturas = (compra.facturas).toString(),
                                efectivo = formatCurrency(compra.sum_contado),
                                credito = formatCurrency(compra.sum_credito),
                                tipoResumen = "",
                                tipo = TipoVentaCard.PERIODO
                            )
                        }
                    }
                }
            }


            /*

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp, bottom = 4.dp)
            ) {
                if (compraDiaFmt.tituloDia.isEmpty()) {
                    CardResumen(
                        titulo = "Día: no se reportan compras",
                        total = "$ 0",
                        efectivo = "$ 0",
                        credito = "$ 0",
                        tipoResumen = "",
                        tipo = TipoVentaCard.DIA
                    )
                } else {
                    CardResumen(
                        titulo = "Día: ${compraDiaFmt.tituloDia}",
                        total = compraDiaFmt.total,
                        efectivo = compraDiaFmt.efectivo,
                        credito = compraDiaFmt.credito,
                        tipoResumen = "",
                        tipo = TipoVentaCard.DIA
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 4.dp, bottom = 4.dp)
            ) {
                if (compraSemanaFmt.tituloSemana.isEmpty()) {
                    CardResumen(
                        titulo = "Últ 7 Días: no se reportan compras",
                        total = "$ 0",
                        efectivo = "$ 0",
                        credito = "$ 0",
                        tipoResumen = "",
                        tipo = TipoVentaCard.SEMANA
                    )
                } else {
                    CardResumen(
                        titulo = "Últ 7 Días: ${compraSemanaFmt.tituloSemana}",
                        total = compraSemanaFmt.total,
                        efectivo = compraSemanaFmt.efectivo,
                        credito = compraSemanaFmt.credito,
                        tipoResumen = "",
                        tipo = TipoVentaCard.SEMANA
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 4.dp, bottom = 4.dp)
            ) {
                if (compraPeriodo.isEmpty()) {
                    CardResumen(
                        titulo = "Periodo: no se reportan compras",
                        total = "$ 0",
                        efectivo = "$ 0",
                        credito = "$ 0",
                        tipoResumen = "",
                        tipo = TipoVentaCard.PERIODO
                    )
                } else {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(compraPeriodo.reversed()) { compra ->
                            CardResumen(
                                modifier = Modifier.width(screenWidth / 1.10f),
                                titulo = "Periodo: ${compra.nom_periodo}",
                                total = formatCurrency(compra.sum_periodo),
                                efectivo = formatCurrency(compra.sum_contado),
                                credito = formatCurrency(compra.sum_credito),
                                tipoResumen = "",
                                tipo = TipoVentaCard.PERIODO
                            )
                        }
                    }
                }
            }

            */

        }
    }
}


