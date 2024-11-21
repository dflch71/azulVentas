package com.azul.azulVentas.ui.presentation.empresa.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.azul.azulVentas.R
import com.azul.azulVentas.ui.presentation.empresa.component.EmpresaScreenTemplate
import com.azul.azulVentas.ui.presentation.empresa.viewmodel.EmpresaViewModel
import com.azul.azulVentas.ui.theme.PrimaryPink
import com.azul.azulVentas.ui.theme.PrimaryPinkBlended
import com.azul.azulVentas.ui.theme.PrimaryViolet
import com.azul.azulVentas.ui.theme.PrimaryVioletDark
import com.azul.azulVentas.ui.theme.PrimaryYellow
import com.azul.azulVentas.ui.theme.PrimaryYellowLight

@Composable
fun EmpresaScreen(
    empresaViewModel: EmpresaViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    EmpresaScreenTemplate(
        empresaViewModel,
        navController,
        modifier = modifier,
        backgroundGradient = arrayOf(
            0f to PrimaryPinkBlended,
            0.6f to PrimaryYellowLight,
            1f to PrimaryYellow
        )
    )
}