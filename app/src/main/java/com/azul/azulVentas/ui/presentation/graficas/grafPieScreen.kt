package com.azul.azulVentas.ui.presentation.graficas

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.azul.azulVentas.core.utils.Utility.Companion.formatCurrency
import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.ui.theme.DarkTextColor
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie

@Composable
fun grafPie(listaValores: LiveData<List<ResumenDia>>,){
    val datos by listaValores.observeAsState(initial = emptyList())


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

    var data by remember { mutableStateOf(chartData) }
    var selectedLabel by remember { mutableStateOf<String?>(null) }
    var selectedColor by remember { mutableStateOf<Color>(colors.first()) }

    Column() {

        Row(
            modifier = Modifier.
            fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            Text(
                " O ",
                modifier = Modifier
                    .padding(start = 16.dp)
                    .background(selectedColor),
                color = selectedColor,
                fontSize = 12.sp
            )

            selectedLabel?.let {
                Text(
                    text = "$it",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                    color = DarkTextColor,
                    maxLines = 2,
                )
            }
        }

        PieChart(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxSize()
                .padding(24.dp),
            data = data,
            onPieClick = {
                println("${it.label} Clicked")
                val pieIndex = data.indexOf(it)
                data = data.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
                selectedLabel = it.label+": "+ formatCurrency(it.data)
                selectedColor = it.color
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
}
