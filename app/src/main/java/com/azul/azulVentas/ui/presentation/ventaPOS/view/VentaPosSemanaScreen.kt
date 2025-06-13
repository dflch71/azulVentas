package com.azul.azulVentas.ui.presentation.ventaPOS.view

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.azul.azulVentas.core.utils.Utility.Companion.calculateDaysToTargetDate
import com.azul.azulVentas.core.utils.Utility.Companion.formatCurrency
import com.azul.azulVentas.core.utils.Utility.Companion.formatDate
import com.azul.azulVentas.core.utils.Utility.Companion.stringToLocalDateTime
import com.azul.azulVentas.ui.components.ErrorDialog
import com.azul.azulVentas.ui.presentation.container.NavGraph
import com.azul.azulVentas.ui.presentation.network.sync.NetworkSyncManager
import com.azul.azulVentas.ui.presentation.network.viewmodel.NetworkViewModel
import com.azul.azulVentas.ui.presentation.venta.component.CardResumen
import com.azul.azulVentas.ui.presentation.venta.component.TipoVentaCard
import com.azul.azulVentas.ui.presentation.venta.viewmodel.VentaSemanaViewModel
import com.azul.azulVentas.ui.presentation.ventaPOS.viewModel.VentaPosSemanaViewModel
import com.azul.azulVentas.ui.theme.DarkTextColor
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentaPosSemanaScreen(
    navController: NavController,
    empresaID: String,
    nombreEmpresa: String,
    ventaPosSemanaViewModel: VentaPosSemanaViewModel,
    networkViewModel: NetworkViewModel
) {
    //val ventaDiaFmt by ventaDiaViewModel.ventaDiaFormatted
    val ventaPosSemanaFmt by ventaPosSemanaViewModel.ventaPosSemanaFormatted

    val ventaPosSemana by ventaPosSemanaViewModel.ventaPosSemana.observeAsState(initial = emptyList())

    val isLoadingVentaSemana by ventaPosSemanaViewModel.isLoading.collectAsState()

    //val errorVentaDia by ventaDiaViewModel.error.collectAsState()
    val errorVentaSemana by ventaPosSemanaViewModel.error.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }

    //val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val isNetworkAvailable by networkViewModel.networkStatus.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }  // Para el Snackbar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scope = rememberCoroutineScope()

    fun cargarDatos() {
        ventaPosSemanaViewModel.cargarVentaPosSemana(empresaID)
        ventaPosSemanaViewModel.listarVentaPosSemanaAgrupada(empresaID)
    }

    // ✅ Al primer render, cargar si hay conexión
    LaunchedEffect(key1 = isNetworkAvailable) {
        if (isNetworkAvailable) { cargarDatos() }
    }

    // ✅ Mostrar el Snackbar si hay un error
    //LaunchedEffect(errorVentaDia, errorVentaSemana) {
    LaunchedEffect(errorVentaSemana) {
        //val error = errorVentaDia ?: errorVentaSemana
        val error = errorVentaSemana
        error?.let {
            showErrorDialog = true
            snackbarHostState.showSnackbar("Error: $it")
        }
    }

    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = { Text(text = "VentaPos Semana") },


                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {},
                scrollBehavior = scrollBehavior
            )
        },

        snackbarHost = { SnackbarHost(snackbarHostState) }


    ) { innerPadding ->

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
            //(errorVentaDia != null || errorVentaSemana != null )
            ( errorVentaSemana != null )
        ) {
            ErrorDialog(
                onDismissRequest = { showErrorDialog = false },
                onConfirmation = {
                    // Close the dialog when confirmed
                    showErrorDialog = false
                    // You can add other actions here if needed, e.g., retry loading data
                },
                dialogTitle = "ERROR VENTA",
                //dialogText = "$errorVentaDia",
                dialogText = "$errorVentaSemana",
                icon = Icons.Default.Info
            )
        }

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
            ) {
                if (isLoadingVentaSemana) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = nombreEmpresa,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = DarkTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = empresaID,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.DarkGray
                )

                Text(
                    text = ventaPosSemanaFmt.tituloSemana,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.DarkGray
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(ventaPosSemana.size) { index ->
                        val venta = ventaPosSemana[index]

                        val fechaCodificada = Uri.encode(venta.fecha_dia)
                        val fecha = venta.fecha_dia + "T00:00:00"
                        val date = stringToLocalDateTime(fecha) ?: LocalDateTime.now()
                        val tDia = "${formatDate(fechaCodificada)} - ${calculateDaysToTargetDate(date)} Días"

                        CardResumen(
                            titulo = "Día: $tDia",
                            total = formatCurrency(venta.sum_dia),
                            facturas = venta.facturas.toString(),
                            efectivo = formatCurrency(venta.sum_contado),
                            credito = formatCurrency(venta.sum_credito),
                            tipoResumen = "",
                            tipo = TipoVentaCard.DIA,
                            onClick = {
                                if (venta.facturas != 0) {
                                    navController.navigate(
                                        NavGraph.DiaEstadistica.createRoute(
                                            "VentaPosFecha",
                                            empresaID,
                                            "VentaPos Día",
                                            fechaCodificada, //Debe ser este formato
                                            venta.facturas.toString(),
                                            formatCurrency(venta.sum_contado),
                                            formatCurrency(venta.sum_credito),
                                            formatCurrency(venta.sum_dia),
                                        )
                                    )
                                } else {
                                    // Mostrar Snackbar dentro de una corrutina
                                    scope.launch {
                                        snackbarHostState.showSnackbar("No hay datos disponibles para mostrar")
                                    }
                                }
                                // Acción de navegación
                            }
                        )
                    }
                }
            }
        }
    }

}
