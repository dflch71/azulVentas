package com.azul.azulVentas.ui.presentation.venta.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.azul.azulVentas.ui.theme.DarkTextColor

enum class TipoVentaCard {
    DIA, SEMANA, PERIODO
}

@Composable
fun CardResumen(
    modifier: Modifier = Modifier,
    titulo: String,
    total: String,
    facturas: String = "0",
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

    val colorCredito = colorEfectivo


    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val tarjetaAltura = maxHeight / 3 - 24.dp  // Calculamos altura para 3 tarjetas con espacio

        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .fillMaxWidth()
                .height(tarjetaAltura)
                .clickable { onClick() }
                .background(
                    color = fondoPrincipal,
                    shape = RoundedCornerShape(10.dp),
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(). padding(top= 4.dp, start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = titulo,
                        fontWeight = FontWeight.Light,
                        color = Color.Black,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )

                    if (tipo == TipoVentaCard.PERIODO) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = Color.DarkGray
                        )
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = total,
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Light,
                            color = Color.DarkGray
                        )
                        Text(
                            text = "Facturas: $facturas",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    }
                }

                Box(
                    modifier = Modifier.background(fondoPrincipal).padding(8.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(colorEfectivo, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally)
                            {
                                Text(efectivo, fontSize = 14.sp, fontWeight = FontWeight.Light)
                                Text(
                                    "Efectivo",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.DarkGray
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(colorCredito, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(credito, fontSize = 14.sp, fontWeight = FontWeight.Light)
                                Text(
                                    if (tipoResumen == "POS") "Tarjeta" else "Crédito",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
