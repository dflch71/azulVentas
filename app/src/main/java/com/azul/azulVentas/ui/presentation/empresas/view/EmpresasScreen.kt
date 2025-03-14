package com.azul.azulVentas.ui.presentation.empresas.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.azul.azulVentas.ui.presentation.login.viewmodel.AuthViewModel

@Composable
fun EmpresasScreen(
    authViewModel: AuthViewModel,
    HomeScreenClicked: () -> Unit,
    RegisterScreenClicked: () -> Unit
){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Emails: ${authViewModel.getUserEmail()}")
        Spacer(modifier = Modifier.height(20.dp))
        FilledTonalButton(onClick = { RegisterScreenClicked() }) { Text(text = "Registrar Empresa") }
        Spacer(modifier = Modifier.height(20.dp))
        FilledTonalButton(onClick = { HomeScreenClicked() }) { Text(text = "Home") }
    }
}