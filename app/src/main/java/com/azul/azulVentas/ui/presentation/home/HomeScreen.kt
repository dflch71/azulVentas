package com.azul.azulVentas.ui.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.azul.azulVentas.ui.components.BottomNavigationBar
import com.azul.azulVentas.ui.components.FavoritesScreen
import com.azul.azulVentas.ui.components.SettingsScreeen
import com.azul.azulVentas.ui.presentation.clientes.view.ClientsScreen
import com.azul.azulVentas.ui.presentation.clientes.viewmodel.ClientesViewModel
import com.azul.azulVentas.ui.presentation.login.viewmodel.AuthViewModel


@Composable
fun HomeScreen(
    clientesViewModel: ClientesViewModel,
    authViewModel: AuthViewModel,
    onLogoutSuccess : () -> Unit
) {
    var selectedItemIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            AzulVentasTopAppBar(authViewModel, onLogoutSuccess)
        },

        bottomBar = {
            BottomNavigationBar(
                onItemselected = { actualIndex ->
                    selectedItemIndex = actualIndex }
            )
        }

    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column (
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(10.dp)
                    //.verticalScroll(rememberScrollState())
            ) {
                when (selectedItemIndex) {
                    0 -> { ClientsScreen(clientesViewModel) }
                    1 -> { FavoritesScreen() }
                    2 -> { Text (text = "Notifications")}
                    3 -> { SettingsScreeen() }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AzulVentasTopAppBar(
    authViewModel: AuthViewModel,
    onLogoutSuccess: () -> Unit
    ) {
    TopAppBar(
        navigationIcon = {
            //IconButton(onClick = {}) {
            //    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null )
            //}
        },

        actions = {
            //IconButton(onClick = {}) {
            //    Icon(imageVector = Icons.Filled.Add, contentDescription = null )
            //}

            IconButton(onClick = {
                authViewModel.signout()
                authViewModel.isLoggedIn()
                onLogoutSuccess()
            }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null,
                    //tint = MaterialTheme.colorScheme.inversePrimary,
                    //modifier = Modifier.size(24.dp)
                )
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = { Text(text = "Azul Ventas") }
    )
}