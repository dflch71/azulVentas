package com.azul.azulVentas.ui.presentation.egreso.view

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
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
import com.azul.azulEgresos.ui.presentation.egreso.viewmodel.EgresoPeriodoViewModel
import com.azul.azulVentas.core.utils.Utility.Companion.ShowRealTimeClock
import com.azul.azulVentas.core.utils.Utility.Companion.formatCurrency
import com.azul.azulVentas.ui.components.ErrorDialog
import com.azul.azulVentas.ui.presentation.container.NavGraph
import com.azul.azulVentas.ui.presentation.egreso.component.CardEgreso
import com.azul.azulVentas.ui.presentation.egreso.component.TipoCard
import com.azul.azulVentas.ui.presentation.egreso.viewmodel.EgresoDiaViewModel
import com.azul.azulVentas.ui.presentation.egreso.viewmodel.EgresoSemanaViewModel
import com.azul.azulVentas.ui.presentation.network.sync.NetworkSyncManager
import com.azul.azulVentas.ui.presentation.network.viewmodel.NetworkViewModel
import com.azul.azulVentas.ui.theme.DarkTextColor
import kotlinx.coroutines.launch

@Composable
fun EgresoScreen(
    navController: NavController,
    empresaID: String,
    nombreEmpresa: String,
    egresoDiaViewModel: EgresoDiaViewModel,
    egresoSemanaViewModel: EgresoSemanaViewModel,
    egresoPeriodoViewModel: EgresoPeriodoViewModel,
    networkViewModel: NetworkViewModel
) {

    val egresoDiaFmt by egresoDiaViewModel.egresoDiaFormatted
    val egresoSemanaFmt by egresoSemanaViewModel.egresoSemanaFormatted
    val egresoPeriodo by egresoPeriodoViewModel.egresoPeriodo.observeAsState(initial = emptyList())

    val isLoadingEgresoPeriodo by egresoPeriodoViewModel.isLoading.collectAsState()

    val errorEgresoDia by egresoDiaViewModel.error.collectAsState()
    val errorEgresoSemana by egresoSemanaViewModel.error.collectAsState()
    val errorEgresoPeriodo by egresoPeriodoViewModel.error.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val snackbarHostState = remember { SnackbarHostState() }  // Para el Snackbar
    val isNetworkAvailable by networkViewModel.networkStatus.collectAsState()
    val scope = rememberCoroutineScope()

    fun cargarDatos() {
        egresoDiaViewModel.cargarEgresoDia(empresaID)
        egresoSemanaViewModel.cargarEgresoSemana(empresaID)
        egresoPeriodoViewModel.egresoPeriodo(empresaID)
    }

    // ✅ Al primer render, cargar si hay conexión
    LaunchedEffect(key1 = isNetworkAvailable) {
        if (isNetworkAvailable) { cargarDatos() }
    }

    // ✅ Mostrar el Snackbar si hay un error
    LaunchedEffect(errorEgresoDia, errorEgresoSemana, errorEgresoPeriodo) {
        val error = errorEgresoDia ?: errorEgresoSemana ?: errorEgresoPeriodo
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
            (errorEgresoDia != null || errorEgresoSemana != null || errorEgresoPeriodo != null))
        {
            ErrorDialog(
                onDismissRequest = { showErrorDialog = false },
                onConfirmation = {
                    // Close the dialog when confirmed
                    showErrorDialog = false
                    // You can add other actions here if needed, e.g., retry loading data
                },
                dialogTitle = "ERROR EGRESOS",
                dialogText = "$errorEgresoDia",
                icon = Icons.Default.Info
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {

            if (isLoadingEgresoPeriodo) { LinearProgressIndicator(modifier = Modifier.fillMaxWidth()) }

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

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp, bottom = 4.dp)
            ) {
                if (egresoDiaFmt.tituloDia.isEmpty()) {
                    CardEgreso(
                        titulo = "Día: no se reportan egresos",
                        total = "$ 0",
                        facturas = "0",
                        tipo = TipoCard.DIA
                    )
                } else {
                    //Para poder enviar el argumento a la pantalla de detalle de venta
                    val fechaCodificada = Uri.encode(egresoDiaFmt.tituloDia)
                    CardEgreso(
                        titulo = "Día: ${egresoDiaFmt.tituloDia}",
                        total = egresoDiaFmt.total,
                        facturas = egresoDiaFmt.facturas,
                        tipo = TipoCard.DIA,
                        onClick = {
                            if (egresoDiaFmt.facturas != "0") {
                                navController.navigate(
                                    NavGraph.DiaEstadistica.createRoute(
                                        "Egreso",
                                        empresaID,
                                        "Egresos Día",
                                        fechaCodificada,
                                        egresoDiaFmt.facturas,
                                        egresoDiaFmt.total,
                                        egresoDiaFmt.credito,
                                        egresoDiaFmt.total
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
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 4.dp, bottom = 4.dp)
            ) {
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 4.dp, bottom = 4.dp)
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
                        //contentPadding = PaddingValues(horizontal = 0.dp),
                        //horizontalArrangement = Arrangement.spacedBy(0.dp),
                    ) {
                        items(egresoPeriodo.reversed()) { egreso ->
                            CardEgreso(
                                modifier = Modifier.width(screenWidth / 1.10f),
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
}




