package com.azul.azulVentas.ui.presentation.venta.view

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
import com.azul.azulVentas.ui.presentation.venta.viewmodel.VentaDiaViewModel
import com.azul.azulVentas.ui.presentation.venta.viewmodel.VentaPeriodoViewModel
import com.azul.azulVentas.ui.presentation.venta.viewmodel.VentaSemanaViewModel
import com.azul.azulVentas.ui.theme.DarkTextColor
import kotlinx.coroutines.launch

@Composable
fun VentaScreen(
    navController: NavController,
    empresaID: String,
    nombreEmpresa: String,
    ventaDiaViewModel: VentaDiaViewModel,
    ventaSemanaViewModel: VentaSemanaViewModel,
    ventaPeriodoViewModel: VentaPeriodoViewModel,
    networkViewModel: NetworkViewModel,
) {

    val ventaDiaFmt by ventaDiaViewModel.ventaDiaFormatted
    val ventaSemanaFmt by ventaSemanaViewModel.ventaSemanaFormatted
    val ventaPeriodo by ventaPeriodoViewModel.ventaPeriodo.observeAsState(initial = emptyList())

    val isLoadingVentaPeriodo by ventaPeriodoViewModel.isLoading.collectAsState()

    val errorVentaDia by ventaDiaViewModel.error.collectAsState()
    val errorVentaSemana by ventaSemanaViewModel.error.collectAsState()
    val errorVentaPeriodo by ventaPeriodoViewModel.error.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val isNetworkAvailable by networkViewModel.networkStatus.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }  // Para el Snackbar
    val scope = rememberCoroutineScope()

    fun cargarDatos() {
        ventaDiaViewModel.cargarVentaDia(empresaID)
        ventaSemanaViewModel.cargarVentaSemana(empresaID)
        ventaPeriodoViewModel.cargarVentaPeriodo(empresaID)
    }

    // ✅ Al primer render, cargar si hay conexión
    LaunchedEffect(key1 = isNetworkAvailable) {
        if (isNetworkAvailable) { cargarDatos() }
    }

    // ✅ Mostrar el Snackbar si hay un error
    LaunchedEffect(errorVentaDia, errorVentaSemana, errorVentaPeriodo) {
        val error = errorVentaDia ?: errorVentaSemana ?: errorVentaPeriodo
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
            (errorVentaDia != null || errorVentaSemana != null || errorVentaPeriodo != null)
            ) {
            ErrorDialog(
                onDismissRequest = { showErrorDialog = false },
                onConfirmation = {
                    // Close the dialog when confirmed
                    showErrorDialog = false
                    // You can add other actions here if needed, e.g., retry loading data
                },
                dialogTitle = "ERROR VENTA",
                dialogText = "$errorVentaDia",
                icon = Icons.Default.Info
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            if (isLoadingVentaPeriodo) { LinearProgressIndicator(modifier = Modifier.fillMaxWidth()) }

            Text(
                text = "Ventas",
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
                color = Color.DarkGray
            )

            ShowRealTimeClock()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {

                item {
                    if (ventaDiaFmt.tituloDia.isEmpty()) {
                        CardResumen(
                            titulo = "Día: no se reportan ventas",
                            total = "$ 0",
                            efectivo = "$ 0",
                            credito = "$ 0",
                            tipoResumen = "",
                            tipo = TipoVentaCard.DIA
                        )
                    } else {
                        //Para poder enviar el argumento a la pantalla de detalle de venta
                        val fechaCodificada = Uri.encode(ventaDiaFmt.tituloDia)
                        CardResumen(
                            titulo = "Día: ${ventaDiaFmt.tituloDia}",
                            total = ventaDiaFmt.total,
                            facturas = ventaDiaFmt.facturas,
                            efectivo = ventaDiaFmt.efectivo,
                            credito = ventaDiaFmt.credito,
                            tipoResumen = "",
                            tipo = TipoVentaCard.DIA,
                            onClick = {
                                if (ventaSemanaFmt.facturas != "0") {
                                    navController.navigate(
                                        NavGraph.DiaEstadistica.createRoute(
                                            "Venta",
                                            empresaID,
                                            "Venta Día",
                                            fechaCodificada,
                                            ventaDiaFmt.efectivo,
                                            ventaDiaFmt.credito,
                                            ventaDiaFmt.total
                                        )
                                    )
                                } else {
                                    // Mostrar Snackbar dentro de una corrutina
                                    scope.launch {
                                        snackbarHostState.showSnackbar("No hay datos disponibles para mostrar")
                                    }
                                }
                            }
                        )
                    }
                }

                item {
                    if (ventaSemanaFmt.tituloSemana.isEmpty()) {
                        CardResumen(
                            titulo = "Últ 7 Días: no se reportan ventas",
                            total = "$ 0",
                            efectivo = "$ 0",
                            credito = "$ 0",
                            tipoResumen = "",
                            tipo = TipoVentaCard.SEMANA
                        )
                    } else {
                        CardResumen(
                            titulo = "Últ 7 Días: ${ventaSemanaFmt.tituloSemana}",
                            total = ventaSemanaFmt.total,
                            facturas = ventaSemanaFmt.facturas,
                            efectivo = ventaSemanaFmt.efectivo,
                            credito = ventaSemanaFmt.credito,
                            tipoResumen = "",
                            tipo = TipoVentaCard.SEMANA
                        )
                    }
                }

                item {
                    if (ventaPeriodo.isEmpty()) {
                        CardResumen(
                            titulo = "Periodo: no se reportan ventas",
                            total = "$ 0",
                            efectivo = "$ 0",
                            credito = "$ 0",
                            tipoResumen = "",
                            tipo = TipoVentaCard.PERIODO
                        )
                    } else {
                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(ventaPeriodo.reversed()) { venta ->
                                CardResumen(
                                    modifier = Modifier
                                        .width(screenWidth / 1.0f)
                                        .padding(end = 8.dp), // evita que se pegue al borde
                                    titulo = "Periodo: ${venta.nom_periodo}",
                                    total = formatCurrency(venta.sum_periodo),
                                    facturas = (venta.facturas).toString(),
                                    efectivo = formatCurrency(venta.sum_contado),
                                    credito = formatCurrency(venta.sum_credito),
                                    tipoResumen = "",
                                    tipo = TipoVentaCard.PERIODO
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}






