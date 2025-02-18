package com.azul.azulVentas.ui.presentation.empresas.view

import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun EmpresasScreen(){
    FilledTonalButton(onClick = { print("Hello") }) {
        Text(text = "Click me")
    }
}