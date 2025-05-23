package com.azul.azulVentas.ui.presentation.diaEstadistica.view

import android.net.Uri
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.azul.azulVentas.R
import com.azul.azulVentas.core.utils.Utility.Companion.formatearEtiqueta
import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.ui.components.CharTypes
import com.azul.azulVentas.ui.components.ErrorDialog
import com.azul.azulVentas.ui.presentation.compra.viewmodel.CompraDiaViewModel
import com.azul.azulVentas.ui.presentation.egreso.viewmodel.EgresoDiaViewModel
import com.azul.azulVentas.ui.presentation.graficas.grafColumn
import com.azul.azulVentas.ui.presentation.network.sync.NetworkSyncManager
import com.azul.azulVentas.ui.presentation.network.viewmodel.NetworkViewModel
import com.azul.azulVentas.ui.presentation.venta.component.TipoVentaCard
import com.azul.azulVentas.ui.presentation.venta.viewmodel.VentaDiaViewModel
import com.azul.azulVentas.ui.presentation.ventaPOS.viewModel.VentaPosDiaViewModel
import com.azul.azulVentas.ui.theme.DarkTextColor
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.Pie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaEstadisticaScreen(
    navController: NavController,
    tipoOperacion: String, //Venta, VentaPOS, Egreso, Compra
    ventaDiaViewModel: VentaDiaViewModel,
    ventaPosDiaViewModel: VentaPosDiaViewModel,
    compraDiaViewModel: CompraDiaViewModel,
    egresoDiaViewModel: EgresoDiaViewModel,
    networkViewModel: NetworkViewModel,
    empresaID: String,
    title: String,
    fecha: String,
    efectivo: String,
    credito: String,
    total: String
) {
    // Primero declaramos las variables que vamos a usar luego del when
    val datosDia: LiveData<List<ResumenDia>>
    val isLoading: Boolean
    val errorDia: String?

    when (tipoOperacion) {
        "Venta" -> {
            datosDia = ventaDiaViewModel.ventaDia
            isLoading = ventaDiaViewModel.isLoading.collectAsState().value
            errorDia = ventaDiaViewModel.error.collectAsState().value
        }

        "VentaPos" -> {
            datosDia = ventaPosDiaViewModel.ventaPosDia
            isLoading = ventaPosDiaViewModel.isLoading.collectAsState().value
            errorDia = ventaPosDiaViewModel.error.collectAsState().value
        }

        "Egreso" -> {
            datosDia = egresoDiaViewModel.egresoDia
            isLoading = egresoDiaViewModel.isLoading.collectAsState().value
            errorDia = egresoDiaViewModel.error.collectAsState().value
        }

        "Compra" -> {
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
    var selectedButton by remember { mutableStateOf("Línea") }
    var selectedChart by remember { mutableStateOf<CharTypes?>(CharTypes.Line) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val isNetworkAvailable by networkViewModel.networkStatus.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }  // Para el Snackbar
    val scope = rememberCoroutineScope()

    fun cargarDatos() {
        when (tipoOperacion) {
            "Venta" -> { ventaDiaViewModel.cargarVentaDia(empresaID) }
            "VentaPos" -> { ventaPosDiaViewModel.cargarVentaPosDia(empresaID) }
            "Egreso" -> { egresoDiaViewModel.cargarEgresoDia(empresaID) }
            "Compra" -> { compraDiaViewModel.cargarCompraDia(empresaID) }
        }
    }

    LaunchedEffect(isNetworkAvailable, empresaID) {
        if (isNetworkAvailable) { cargarDatos()
            /*
            when (tipoOperacion) {
                "Venta" -> { ventaDiaViewModel.listarVentaDia(empresaID) }
                "VentaPos" -> { ventaPosDiaViewModel.listarVentaPosDia(empresaID) }
                "Egreso" -> { egresoDiaViewModel.listarEgresoDia(empresaID) }
                "Compra" -> { compraDiaViewModel.listarCompraDia(empresaID) }
            }
            */
        }
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
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
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


        bottomBar = {
            BottomAppBar(actions = {

                Spacer(modifier = Modifier.width(12.dp))

                IconButton(onClick = {
                    selectedButton = "Línea"
                    selectedChart = CharTypes.Line
                }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_line_chart),
                        contentDescription = "Build description"
                    )
                }

                IconButton(onClick = {
                    selectedButton = "Barra"
                    selectedChart = CharTypes.Bar
                }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bar_chart),
                        contentDescription = "Menu description",
                    )
                }

                IconButton(onClick = {
                    selectedButton = "Column"
                    selectedChart = CharTypes.Column
                }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_linear_scale),
                        contentDescription = "Menu description",
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


        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column() {
                Spacer(modifier = Modifier.padding(8.dp))
                if (isLoading) { LinearProgressIndicator(modifier = Modifier.fillMaxWidth()) }

                //Fecha del dia Seleccionado
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = fechaDecode.uppercase(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = DarkTextColor
                )

                Text(
                    text = "Última ${title} reportada",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.padding(4.dp))
                Box(modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                    ) {
                        Text(
                            text = "TOTAL",
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


                Row(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center,

                ){

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

                Spacer(modifier = Modifier.padding(4.dp))

                BoxWithConstraints(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxSize()
                        .systemBarsPadding()
                ) {
                    val totalAvailableHeight = maxHeight
                    //val cardHeight = (totalAvailableHeight - 12.dp) / 2  // Espacio entre tarjetas
                    val cardHeight = (totalAvailableHeight - 12.dp) / 1  // Espacio entre tarjetas

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

                        item {

                            CardResumenDia(
                                datosDia,
                                'F',
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(cardHeight),
                            )
                        }


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
            val tituloOperacion = formatearEtiqueta((listValues.value.first().tipo).lowercase())+
                    "\nEfectivo Vs Crédito"

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

@Composable
fun grafPie(listaValores: LiveData<List<ResumenDia>>,){
    val datos by listaValores.observeAsState(initial = emptyList())

    var totalEfectivo = 0.0
    var totalCredito = 0.0

    for (i in datos) {
        totalEfectivo += i.sum_contado
        totalCredito += i.sum_credito
    }

    var data by remember {
        mutableStateOf(
            listOf(
                Pie(
                    label = "Efectivo",
                    data = totalEfectivo,
                    color = Color.Gray,
                    selectedColor = Color.LightGray
                ),
                Pie(
                    label = "Crédito",
                    data = totalCredito,
                    color = Color.Cyan,
                    selectedColor = Color.Blue
                ),
            )
        )
    }

    val colors = listOf(
        Color(0xFF00ACC1),
        Color(0xFF039BE5),
        Color(0xFF7CB342),
        Color(0xFF43A047),
        Color(0xFFFB8C00),
        Color(0xFFFDD835),
        Color(0xFF6A1B9A)
    )

    // Transformamos los datos a la estructura que necesita el ColumnChart
    val chartData = remember(datos) {
        datos.mapIndexed { index, resumen ->
            Pie(
                label = resumen.hora_am_pm,
                data = resumen.sum_hora,
                color = colors[index % colors.size],
                selectedColor = colors[index % colors.size].copy(alpha = 0.5f)
            )
        }
    }


    PieChart(
        modifier = Modifier
            .wrapContentWidth()
            .fillMaxSize()
            .padding(24.dp),
        data = chartData,
        onPieClick = {
            println("${it.label} Clicked")
            val pieIndex = data.indexOf(it)
            data = data.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
        },
        selectedScale = 1.1f,
        scaleAnimEnterSpec = spring<Float>(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        colorAnimEnterSpec = tween(300),
        colorAnimExitSpec = tween(300),
        scaleAnimExitSpec = tween(300),
        spaceDegreeAnimExitSpec = tween(300),
        spaceDegree = 7f,
        selectedPaddingDegree = 4f,
        style = Pie.Style.Stroke(width = 50.dp)
        //style = Pie.Style.Fill
    )
}

@Composable
fun grafLine() {
    LineChart(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp),
        data = remember {
            listOf(
                Line(
                    label = "Windows",
                    values = listOf(28.0, 41.0, 5.0, 10.0, 35.0),
                    color = SolidColor(Color(0xFF23af92)),
                    firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                    secondGradientFillColor = Color.Transparent,
                    strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                    gradientAnimationDelay = 1000,
                    drawStyle = DrawStyle.Stroke(width = 2.dp),
                )
            )
        },
        animationMode = AnimationMode.Together(delayBuilder = {
            it * 500L
        }),
    )
}





