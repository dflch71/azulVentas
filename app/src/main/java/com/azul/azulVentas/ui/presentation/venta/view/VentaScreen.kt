package com.azul.azulVentas.ui.presentation.venta.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azul.azulVentas.R
import com.azul.azulVentas.core.utils.Utility.Companion.ShowRealTimeClock
import com.azul.azulVentas.core.utils.Utility.Companion.calculateDaysToTargetDate
import com.azul.azulVentas.core.utils.Utility.Companion.formatCurrency
import com.azul.azulVentas.ui.presentation.venta.viewmodel.VentaDiaViewModel
import com.azul.azulVentas.ui.theme.DarkTextColor
import com.azul.azulVentas.core.utils.Utility.Companion.formatDate
import com.azul.azulVentas.core.utils.Utility.Companion.stringToLocalDateTime
import com.azul.azulVentas.ui.presentation.venta.viewmodel.VentaSemanaViewModel
import java.time.LocalDateTime

@Composable
fun VentaScreen(
    empresaID: String,
    ventaDiaViewModel: VentaDiaViewModel,
    ventaSemanaViewModel: VentaSemanaViewModel,
) {

    //Venta Día
    val ventaDia by ventaDiaViewModel.ventaDia.observeAsState(initial = emptyList())
    val isLoadingVentaDia by ventaDiaViewModel.isLoading.collectAsState()
    val errorVentaDia by ventaDiaViewModel.error.collectAsState()

    //Venta Semana
    val ventaSemana by ventaSemanaViewModel.ventaSemana.observeAsState(initial = emptyList())
    val isLoadingVentaSemana by ventaSemanaViewModel.isLoading.collectAsState()
    val errorVentaSemana by ventaSemanaViewModel.error.collectAsState()

    LaunchedEffect(key1 = true) {
        ventaDiaViewModel.ventaDia(empresaID)
        ventaSemanaViewModel.ventaSemana(empresaID)
    }

    var diaVenta by remember { mutableStateOf("") }
    var sumVentaDia by remember { mutableStateOf(0.0) }
    var sumVentaDiaEfectivo by remember { mutableStateOf(0.0) }
    var sumVentaDiaCredito by remember { mutableStateOf(0.0) }

    val dayofSale: LocalDateTime
    if (ventaDia.isNotEmpty()) {
        diaVenta = ventaDia.first().fecha_dia+"T00:00:00"
        dayofSale = stringToLocalDateTime(diaVenta)!!
        diaVenta = "${formatDate(ventaDia.first().fecha_dia)} - ${(calculateDaysToTargetDate(dayofSale))} Días"
        sumVentaDia = ventaDia.sumOf { it.sum_hora }
        sumVentaDiaEfectivo = ventaDia.sumOf { it.sum_contado }
        sumVentaDiaCredito = ventaDia.sumOf { it.sum_credito }
    }

    var semana by remember { mutableStateOf("") }
    var sumVentaSemana by remember { mutableStateOf(0.0) }
    var sumVentaSemanaEfectivo by remember { mutableStateOf(0.0) }
    var sumVentaSemanaCredito by remember { mutableStateOf(0.0) }
    if (ventaSemana.isNotEmpty()) {
        semana = "${formatDate(ventaSemana.first().fecha_dia)} - ${formatDate(ventaSemana.last().fecha_dia)}"
        sumVentaSemana = ventaSemana.sumOf { it.sum_dia }
        sumVentaSemanaEfectivo = ventaSemana.sumOf { it.sum_contado }
        sumVentaSemanaCredito = ventaSemana.sumOf { it.sum_credito }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Ventas",
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
            val totalVentaDia = formatCurrency(sumVentaDia)
            val totalEfectivoDia = formatCurrency(sumVentaDiaEfectivo)
            val totalCreditoDia = formatCurrency(sumVentaDiaCredito)
            CardVenta("Día: $diaVenta", totalVentaDia, totalEfectivoDia, totalCreditoDia)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.weight(0.33f)
        ){
            val totalVentaSem = formatCurrency(sumVentaSemana)
            val totalEfectivoSem = formatCurrency(sumVentaSemanaEfectivo)
            val totalCreditoSem = formatCurrency(sumVentaSemanaCredito)
            CardVenta("Semana: $semana", totalVentaSem, totalEfectivoSem, totalCreditoSem)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.weight(0.33f)
        ){
            CardVenta("Periodo: Ene-Feb-Mar", "$5,487,000", "$3,000,000", "$2,487,000")
        }
    }
}


@Composable
fun CardVenta(
    Title: String,
    Total: String,
    Efectivo: String,
    Credito: String,
    onClick: () -> Unit = {}
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .clickable { onClick() }
            .background(
                //colorResource(id = R.color.lightGrey),
                color = Color.LightGray,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Column (
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = Title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                fontWeight = FontWeight.Light,
                color = Color.Black
            )

            Text(
                text = Total,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Light
            )

            Row(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        //color = colorResource(id = R.color.lightGrey)
                        color = Color.LightGray
                    )
            ){

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                        .background(
                            //color = colorResource(id = R.color.light_Grey),
                            color = Color(0xFFDEDEDE),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            Efectivo,
                            fontWeight = FontWeight.Light,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            "Efectivo",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 4.dp),
                            color = Color.DarkGray
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                        .background(
                            //color = colorResource(id = R.color.light_Grey),
                            color = Color(0xFFDEDEDE),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            Credito,
                            fontWeight = FontWeight.Light,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            "Crédito",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 4.dp),
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CardVentaSemana(
    Title: String,
    Total: String,
    Efectivo: String,
    Credito: String,
    onClick: () -> Unit = {}
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .clickable { onClick() }
            .background(
                colorResource(id = R.color.lightGrey),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Column (
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = Title,
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                fontWeight = FontWeight.Light,
                color = Color.Black
            )

            Text(
                text = Total,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Light
            )

            Row(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = colorResource(id = R.color.LightBlue)
                    )
            ){

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                        .background(
                            color = colorResource(id = R.color.lightYellow),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            Efectivo,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            "Efectivo",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                        .background(
                            color = colorResource(id = R.color.LightRed),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            Credito,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            "Crédito",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

