package com.azul.azulVentas.ui.presentation.venta.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class TipoVentaCard {
    DIA, SEMANA, PERIODO
}

@Composable
fun CardResumen(
    modifier: Modifier = Modifier,
    titulo: String,
    total: String,
    efectivo: String,
    credito: String,
    tipoResumen: String,
    tipo: TipoVentaCard,
    onClick: () -> Unit = {}
) {
    val fondoPrincipal = when (tipo) {
        TipoVentaCard.DIA -> Color(0xFFe9e9e9)
        TipoVentaCard.SEMANA -> Color(0xFFEFECF5)
        TipoVentaCard.PERIODO -> Color(0xFFF9FBE7)
    }

    val colorEfectivo = when (tipo) {
        TipoVentaCard.DIA -> Color(0xFFDEDEDE)
        TipoVentaCard.SEMANA -> Color(0xFFECE4FA)
        TipoVentaCard.PERIODO -> Color(0xFFDCEDC8)
    }

    val colorCredito = when (tipo) {
        TipoVentaCard.DIA -> Color(0xFFDEDEDE)
        TipoVentaCard.SEMANA -> Color(0xFFECE4FA)
        TipoVentaCard.PERIODO -> Color(0xFFDCEDC8)
    }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .clickable { onClick() }
            .background(
                color = fondoPrincipal,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top =  8.dp, start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = titulo,
                    fontWeight = FontWeight.Light,
                    color = Color.Black,
                    maxLines = 1,
                    modifier = Modifier.weight(0.9f),
                )

                if (tipo == TipoVentaCard.PERIODO) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Estrella",
                        tint = Color.DarkGray,
                        modifier = Modifier.weight(0.1f)
                    )
                }
            }

            Text(
                text = total,
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
                    .fillMaxWidth()
                    .background(fondoPrincipal)
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(0.5f)
                        .background(colorEfectivo, shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(efectivo, fontSize = 16.sp, fontWeight = FontWeight.Light)
                        Text("Efectivo", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    }
                }

                // Crédito
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(0.5f)
                        .background(colorCredito, shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(credito, fontSize = 16.sp, fontWeight = FontWeight.Light)
                        Text(if (tipoResumen == "POS") "Tarjeta" else "Crédito", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    }
                }
            }
        }
    }
}
