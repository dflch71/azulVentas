package com.azul.azulVentas.ui.presentation.ventaPOS.view

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
import com.azul.azulVentas.ui.presentation.container.NavGraph
import com.azul.azulVentas.ui.presentation.network.sync.NetworkSyncManager
import com.azul.azulVentas.ui.presentation.network.viewmodel.NetworkViewModel
import com.azul.azulVentas.ui.presentation.venta.component.CardResumen
import com.azul.azulVentas.ui.presentation.venta.component.TipoVentaCard
import com.azul.azulVentas.ui.presentation.ventaPOS.viewModel.VentaPosDiaViewModel
import com.azul.azulVentas.ui.presentation.ventaPOS.viewModel.VentaPosPeriodoViewModel
import com.azul.azulVentas.ui.presentation.ventaPOS.viewModel.VentaPosSemanaViewModel
import com.azul.azulVentas.ui.theme.DarkTextColor
import kotlinx.coroutines.launch

@Composable
fun VentaPosScreen(
    navController: NavController,
    empresaID: String,
    nombreEmpresa: String,
    ventaPosDiaViewModel: VentaPosDiaViewModel,
    ventaPosSemanaViewModel: VentaPosSemanaViewModel,
    ventaPosPeriodoViewModel: VentaPosPeriodoViewModel,
    networkViewModel: NetworkViewModel
) {

    val ventaPosDiaFmt by ventaPosDiaViewModel.ventaPosDiaFormatted
    val ventaSemanaFmt by ventaPosSemanaViewModel.ventaSemanaFormatted
    val ventaPosPeriodo by ventaPosPeriodoViewModel.ventaPosPeriodo.observeAsState(initial = emptyList())

    val isLoadingVentaPosPeriodo by ventaPosPeriodoViewModel.isLoading.collectAsState()

    val errorVentaPosDia by ventaPosDiaViewModel.error.collectAsState()
    val errorVentaPosSemana by ventaPosSemanaViewModel.error.collectAsState()
    val errorVentaPosPeriodo by ventaPosPeriodoViewModel.error.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val isNetworkAvailable by networkViewModel.networkStatus.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }  // Para el Snackbar
    val scope = rememberCoroutineScope()

    fun cargarDatos() {
        ventaPosDiaViewModel.cargarVentaPosDia(empresaID)
        ventaPosSemanaViewModel.cargarVentaPosSemana(empresaID)
        ventaPosPeriodoViewModel.cargarVentaPosPeriodo(empresaID)
    }

    // ✅ Al primer render, cargar si hay conexión
    LaunchedEffect(key1 = isNetworkAvailable) {
        if (isNetworkAvailable) { cargarDatos() }
    }

    // ✅ Mostrar el Snackbar si hay un error
    LaunchedEffect(errorVentaPosDia, errorVentaPosSemana, errorVentaPosPeriodo) {
        val error = errorVentaPosDia ?: errorVentaPosSemana ?: errorVentaPosPeriodo
        error?.let {
            showErrorDialog = true
            snackbarHostState.showSnackbar("Error: $it")
        }
    }

    Scaffold (
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
            (errorVentaPosDia != null || errorVentaPosSemana != null || errorVentaPosPeriodo != null)
            ) {
            ErrorDialog(
                onDismissRequest = { showErrorDialog = false },
                onConfirmation = {
                    // Close the dialog when confirmed
                    showErrorDialog = false
                    // You can add other actions here if needed, e.g., retry loading data
                },
                dialogTitle = "ERROR VENTA POS",
                dialogText = "$errorVentaPosDia",
                icon = Icons.Default.Info
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            if (isLoadingVentaPosPeriodo) { LinearProgressIndicator(modifier = Modifier.fillMaxWidth()) }

            Text(
                text = "Ventas POS",
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

                item {
                    //Para poder enviar el argumento a la pantalla de detalle de venta
                    val fechaCodificada = Uri.encode(ventaPosDiaFmt.tituloDia)
                    CardResumen(
                        titulo = "Día: ${ventaPosDiaFmt.tituloDia}",
                        total = ventaPosDiaFmt.total,
                        facturas = ventaPosDiaFmt.facturas,
                        efectivo = ventaPosDiaFmt.efectivo,
                        credito = ventaPosDiaFmt.credito,
                        tipoResumen = "POS",
                        tipo = TipoVentaCard.DIA,
                        onClick = {
                            if (ventaPosDiaFmt.facturas != "0") {
                                navController.navigate(
                                    NavGraph.DiaEstadistica.createRoute(
                                        "VentaPos",
                                        empresaID,
                                        "Ventapos Día",
                                        fechaCodificada,
                                        ventaPosDiaFmt.efectivo,
                                        ventaPosDiaFmt.credito,
                                        ventaPosDiaFmt.total
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

                item {
                    CardResumen(
                        titulo = "Últ 7 Días: ${ventaSemanaFmt.tituloSemana}",
                        total = ventaSemanaFmt.total,
                        facturas = ventaSemanaFmt.facturas,
                        efectivo = ventaSemanaFmt.efectivo,
                        credito = ventaSemanaFmt.credito,
                        tipoResumen = "POS",
                        tipo = TipoVentaCard.SEMANA,
                    )
                }

                item {

                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(ventaPosPeriodo.reversed()) { venta ->

                            CardResumen(
                                modifier = Modifier
                                    .width(screenWidth / 1.0f)
                                    .padding(end = 8.dp), // evita que se pegue al borde,
                                titulo = "Periodo: ${venta.nom_periodo}",
                                total = formatCurrency(venta.sum_periodo),
                                facturas = (venta.facturas).toString(),
                                efectivo = formatCurrency(venta.sum_contado),
                                credito = formatCurrency(venta.sum_credito),
                                tipoResumen = "POS",
                                tipo = TipoVentaCard.PERIODO
                            )
                        }
                    }
                }
            }
        }
    }
}


