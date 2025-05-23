package com.azul.azulVentas.ui.presentation.graficas

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.azul.azulVentas.domain.model.resumenDia.ResumenDia
import com.azul.azulVentas.ui.theme.PrimaryVioletLight
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.PopupProperties


@Composable
fun grafColumn(
    listaValores: LiveData<List<ResumenDia>>,
    tipo: Char = 'V' // 'V' para ventas, 'F' para facturas
)
{
    val datos by listaValores.observeAsState(initial = emptyList())

    // Ordenar por hora_dia para mostrar la gráfica correctamente
    val datosOrdenados = remember(datos) {
        datos.sortedBy { it.hora_dia }
    }

    val colors = listOf(
        Color(0xFFE1BEE7),
        Color(0xFFCE93D8),
        Color(0xFFBA68C8),
        Color(0xFFBA68C8),
        Color(0xFFAB47BC),
        Color(0xFF8E24AA),
        Color(0xFF6A1B9A)
    )

    // Transformamos los datos a la estructura que necesita el ColumnChart
    val chartData = remember(datosOrdenados) {
        datosOrdenados.mapIndexed { index, resumen ->
            var resdatos: Double = 0.0
            when (tipo) {
                'V' -> { resdatos = resumen.sum_hora }
                'F' -> { resdatos = resumen.sum_factura.toDouble() }
            }
            Bars(
                label = resumen.hora_am_pm,
                values = listOf(
                    Bars.Data(
                        label = resumen.hora_am_pm,
                        value =  resdatos,
                        color = SolidColor(colors[index % colors.size]),
                    )
                )
            )
        }
    }

    // Ajustamos el grosor de las barras dinámicamente según la cantidad de datos
    val barThickness = when (datosOrdenados.size) {
        in 0..3 -> 40.dp    // Más anchas si hay pocos datos
        in 4..6 -> 30.dp
        else -> 20.dp        // Default para muchos datos
    }

    //val spacing = if (datosOrdenados.size < 5) 8.dp else 3.dp
    val spacing = if (datosOrdenados.size < 5) 150.dp else 3.dp

    ColumnChart(
        modifier = Modifier
            .wrapContentWidth()
            .fillMaxSize()
            .padding(8.dp),

        labelProperties = LabelProperties(
            enabled = true,
            forceRotation = true,
            labels = datosOrdenados.map { it.hora_am_pm },
            rotationDegreeOnSizeConflict = 0.0f,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 8.sp,
                textAlign = TextAlign.End
            ),
            padding = 8.dp
        ),

        indicatorProperties = HorizontalIndicatorProperties(
            textStyle = TextStyle(color = Color.Black, fontSize = 10.sp),
        ),

        data = chartData,

        barProperties = BarProperties(
            cornerRadius =  Bars.Data.Radius.Rectangle(topRight = 6.dp, topLeft = 6.dp),
            spacing = spacing,
            thickness = barThickness
        ),

        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),

        gridProperties = GridProperties(
            xAxisProperties = GridProperties.AxisProperties(
                enabled = true,
                thickness = 0.3.dp,
                lineCount = 5,
                color = SolidColor(Color.LightGray)
            )
        ),

        popupProperties = PopupProperties(
            textStyle =  TextStyle(
                color = Color.Black,
                fontSize = 10.sp,
                textAlign = TextAlign.Justify
            ) ,
            containerColor =  PrimaryVioletLight,
        )

    )
}
