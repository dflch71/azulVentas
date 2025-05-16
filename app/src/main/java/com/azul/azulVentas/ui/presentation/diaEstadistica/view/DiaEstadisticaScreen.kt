package com.azul.azulVentas.ui.presentation.diaEstadistica.view

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.azul.azulVentas.R
import com.azul.azulVentas.ui.components.CharTypes
import com.azul.azulVentas.ui.theme.DarkTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaEstadisticaScreen(
    title: String,
    fecha: String,
    navController: NavController
) {

    var selectedButton by remember { mutableStateOf("Línea") }
    var selectedChart by remember { mutableStateOf<CharTypes?>(CharTypes.Line) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    //Fecha decodificada para aceptar los formatos de la fecha /
    val fechaDecode : String = Uri.decode(fecha)
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
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            Column() {
                //Fecha del dia Seleccionado
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = fechaDecode.uppercase(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = DarkTextColor
                )

                Text(
                    text = "Última ${title} reportada",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.DarkGray
                )
            }
        }
    }
}