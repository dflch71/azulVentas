package com.azul.azulVentas.ui.presentation.diaEstadistica.view

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.azul.azulVentas.R
import com.azul.azulVentas.core.utils.Utility.Companion.formatearEtiqueta
import com.azul.azulVentas.core.utils.Utility.Companion.formatearFecha
import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.ui.components.CharTypes
import com.azul.azulVentas.ui.components.ErrorDialog
import com.azul.azulVentas.ui.components.OperationTypes
import com.azul.azulVentas.ui.presentation.compra.viewmodel.CompraDiaViewModel
import com.azul.azulVentas.ui.presentation.egreso.viewmodel.EgresoDiaViewModel
import com.azul.azulVentas.ui.presentation.graficas.grafColumn
import com.azul.azulVentas.ui.presentation.graficas.grafPie
import com.azul.azulVentas.ui.presentation.network.sync.NetworkSyncManager
import com.azul.azulVentas.ui.presentation.network.viewmodel.NetworkViewModel
import com.azul.azulVentas.ui.presentation.venta.viewmodel.VentaDiaFechaViewModel
import com.azul.azulVentas.ui.presentation.venta.viewmodel.VentaDiaViewModel
import com.azul.azulVentas.ui.presentation.ventaPOS.viewModel.VentaPosDiaFechaViewModel
import com.azul.azulVentas.ui.presentation.ventaPOS.viewModel.VentaPosDiaViewModel
import com.azul.azulVentas.ui.theme.DarkTextColor
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaEstadisticaScreen(
    navController: NavController,
    tipoOperacion: String, //Venta, VentaPOS, Egreso, Compra, VentaFecha
    ventaDiaViewModel: VentaDiaViewModel,
    ventaDiaFechaViewModel: VentaDiaFechaViewModel,
    ventaPosDiaViewModel: VentaPosDiaViewModel,
    ventaPosDiaFechaViewModel: VentaPosDiaFechaViewModel,
    compraDiaViewModel: CompraDiaViewModel,
    egresoDiaViewModel: EgresoDiaViewModel,
    networkViewModel: NetworkViewModel,
    empresaID: String,
    title: String,
    fecha: String,
    facturas: String,
    efectivo: String,
    credito: String,
    total: String
) {

    // Primero declaramos las variables que vamos a usar luego del when
    val datosDia: LiveData<List<ResumenDia>>
    val isLoading: Boolean
    val errorDia: String?

    when (tipoOperacion) {
        //"Venta"
        OperationTypes.Venta.toString() -> {
            datosDia = ventaDiaViewModel.ventaDia
            isLoading = ventaDiaViewModel.isLoading.collectAsState().value
            errorDia = ventaDiaViewModel.error.collectAsState().value
        }

        //"VentaFecha"
        OperationTypes.VentaFecha.toString() -> {
            datosDia = ventaDiaFechaViewModel.ventaDiaFecha
            isLoading = ventaDiaFechaViewModel.isLoading.collectAsState().value
            errorDia = ventaDiaFechaViewModel.error.collectAsState().value
        }

        //"VentaPos"
        OperationTypes.VentaPos.toString() -> {
            datosDia = ventaPosDiaViewModel.ventaPosDia
            isLoading = ventaPosDiaViewModel.isLoading.collectAsState().value
            errorDia = ventaPosDiaViewModel.error.collectAsState().value
        }

        //"VentaPosFecha"
        OperationTypes.VentaPosFecha.toString() -> {
            datosDia = ventaPosDiaFechaViewModel.ventaPosDiaFecha
            isLoading = ventaPosDiaFechaViewModel.isLoading.collectAsState().value
            errorDia = ventaPosDiaFechaViewModel.error.collectAsState().value
        }

        //"Egreso"
        OperationTypes.Egreso.toString() -> {
            datosDia = egresoDiaViewModel.egresoDia
            isLoading = egresoDiaViewModel.isLoading.collectAsState().value
            errorDia = egresoDiaViewModel.error.collectAsState().value
        }

        //"Compra"
        OperationTypes.Compra.toString() -> {
            datosDia = compraDiaViewModel.compraDia
            isLoading = compraDiaViewModel.isLoading.collectAsState().value
            errorDia = compraDiaViewModel.error.collectAsState().value
        }

        else -> {
            datosDia = MutableLiveData(emptyList())
            isLoading = false
            errorDia = null
        }
    }

    var showErrorDialog by remember { mutableStateOf(false) }
    var selectedButton by remember { mutableStateOf("Bar") }
    var selectedChart by remember { mutableStateOf<CharTypes?>(CharTypes.Bar) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val isSelectColor = remember {  Color(0xFFFFAB40) }
    val isUnSelectColor = remember {  Color(0xFF7C4DFF) }

    val isNetworkAvailable by networkViewModel.networkStatus.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }  // Para el Snackbar
    val scope = rememberCoroutineScope()

    fun cargarDatos() {
        when (tipoOperacion) {
            OperationTypes.Venta.toString() -> { ventaDiaViewModel.cargarVentaDia(empresaID) }
            OperationTypes.VentaFecha.toString() -> { ventaDiaFechaViewModel.cargarVentaDiaFecha(empresaID, fecha) }
            OperationTypes.VentaPos.toString() -> { ventaPosDiaViewModel.cargarVentaPosDia(empresaID) }
            OperationTypes.VentaPosFecha.toString() -> { ventaPosDiaFechaViewModel.cargarVentaPosDiaFecha(empresaID, fecha) }
            OperationTypes.Egreso.toString() -> { egresoDiaViewModel.cargarEgresoDia(empresaID) }
            OperationTypes.Compra.toString() -> { compraDiaViewModel.cargarCompraDia(empresaID) }

            //"Venta" -> { ventaDiaViewModel.cargarVentaDia(empresaID) }
            //"VentaFecha" -> { ventaDiaFechaViewModel.cargarVentaDiaFecha(empresaID, fecha)  }
            //"VentaPos" -> { ventaPosDiaViewModel.cargarVentaPosDia(empresaID) }
            //"Egreso" -> { egresoDiaViewModel.cargarEgresoDia(empresaID) }
            //"Compra" -> { compraDiaViewModel.cargarCompraDia(empresaID) }
        }
    }

    LaunchedEffect(isNetworkAvailable, empresaID) {
        if (isNetworkAvailable) { cargarDatos() }
    }

    // ✅ Mostrar el Snackbar si hay un error
    LaunchedEffect(errorDia) {
        val error = errorDia
        error?.let {
            showErrorDialog = true
            snackbarHostState.showSnackbar("Error: $it")
        }
    }

    val datosDiaState = datosDia.observeAsState(emptyList())
    if ((datosDiaState.value.isEmpty()) || (datosDiaState.value.first().sum_factura == 0)) return

    //Fecha decodificada para aceptar los formatos de la fecha /
    val fechaDecode: String = Uri.decode(fecha)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),

                title = {
                    Text(
                        title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
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

        bottomBar = {

            BottomAppBar(actions = {

                Spacer(modifier = Modifier.width(12.dp))

                IconButton(onClick = {
                    selectedButton = "Bar"
                    selectedChart = CharTypes.Bar
                }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_stacked_chart),
                        contentDescription = "Build description",
                        tint = if (selectedChart == CharTypes.Bar) isSelectColor else isUnSelectColor
                    )
                }

                IconButton(onClick = {
                    selectedButton = "Column"
                    selectedChart = CharTypes.Column
                }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bar_chart),
                        contentDescription = "Menu description",
                        tint = if (selectedChart == CharTypes.Column) isSelectColor else isUnSelectColor
                    )
                }

                IconButton(onClick = {
                    selectedButton = "Pie"
                    selectedChart = CharTypes.Pie
                }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_pie_chart),
                        contentDescription = "Menu description",
                        tint = if (selectedChart == CharTypes.Pie) isSelectColor else isUnSelectColor
                    )
                }
            })
        }

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
        if ((showErrorDialog) && (errorDia != null)
        ) {
            ErrorDialog(
                onDismissRequest = { showErrorDialog = false },
                onConfirmation = {
                    // Close the dialog when confirmed
                    showErrorDialog = false
                    // You can add other actions here if needed, e.g., retry loading data
                },
                dialogTitle = "ERROR VENTA",
                dialogText = "$errorDia",
                icon = Icons.Default.Info
            )
        }

        //Mostrar el progress para cargar las imagenes
        var showProgress by remember { mutableStateOf(false) }
        LaunchedEffect(isLoading) {
            if (isLoading) {
                showProgress = true
            } else {
                delay(1000) // mantener visible por 1 segundos extra
                showProgress = false
            }
        }

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column() {

                AnimatedVisibility(
                    visible = showProgress,
                    enter = fadeIn(animationSpec = tween(300)),
                    exit = fadeOut(animationSpec = tween(300))
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.padding(16.dp))
                            Text(
                                text = "Cargando Datos...",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Normal,
                                color = Color.DarkGray
                            )
                        }
                    }
                }

                //Fecha del dia Seleccionado
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = formatearFecha(fechaDecode).uppercase(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = DarkTextColor
                )

                Text(
                    text = "Reporte de ${title}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.DarkGray
                )


                Spacer(modifier = Modifier.padding(4.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column() {
                            Text(
                                text = "TOTAL (Facturas: $facturas)",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = total,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = DarkTextColor,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    if (!tipoOperacion.equals("Egreso")) {

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Center,

                            ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color(0xFFEDE7F6),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .weight(1f)
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.Center

                            ) {
                                Column() {
                                    Text(
                                        text = "EFECTIVO",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        color = Color.Gray,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = efectivo,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.padding(4.dp))

                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color(0xFFEDE7F6),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .weight(1f)
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column() {
                                    Text(
                                        text = "CRÉDITO",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        color = Color.Gray,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = credito,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                )

                BoxWithConstraints(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxSize()
                        .systemBarsPadding()
                ) {
                    val totalAvailableHeight = maxHeight
                    //val cardHeight = (totalAvailableHeight - 12.dp) / 2  // Espacio entre tarjetas
                    val cardHeight = (totalAvailableHeight - 12.dp) / 1  // Espacio entre tarjetas

                    when (selectedChart)  {
                        CharTypes.Bar -> {
                            LazyColumn(
                                contentPadding = PaddingValues(vertical = 4.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {

                                item {
                                    CardResumenDia(
                                        datosDia,
                                        'V',
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(cardHeight),
                                    )
                                }
                            }

                        }
                        CharTypes.Column -> {
                            LazyColumn(
                                contentPadding = PaddingValues(vertical = 4.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {

                                item {
                                    CardResumenDia(
                                        datosDia,
                                        'F',
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(cardHeight),
                                    )
                                }

                            }

                        }
                        CharTypes.Pie -> {
                            LazyColumn(
                                contentPadding = PaddingValues(vertical = 4.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {


                                item {
                                    CardResumenPie(
                                        datosDia,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(cardHeight)
                                    )
                                }
                            }

                        }
                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun CardResumenDia(
    listValues : LiveData<List<ResumenDia>>,
    tipoGraf: Char, // 'V' Venta, 'F' Factura
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            val tituloOperacion = formatearEtiqueta((listValues.value.first().tipo).lowercase())
            var titulo = ""
            when (tipoGraf) {
                'V' -> { titulo = "$tituloOperacion\nDistribución en pesos" }
                'F' -> { titulo = "$tituloOperacion\nVolumen de Facturas" }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$titulo",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    color = Color.DarkGray,
                    maxLines = 2,
                )
                grafColumn(listValues, tipoGraf)
            }
        }
    }
}

@Composable
fun CardResumenPie(
    listValues : LiveData<List<ResumenDia>>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            val tituloOperacion = formatearEtiqueta((listValues.value.first().tipo).lowercase())

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$tituloOperacion",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    color = Color.DarkGray,
                    maxLines = 2,
                )
                grafPie(listValues)
            }
        }
    }
}

