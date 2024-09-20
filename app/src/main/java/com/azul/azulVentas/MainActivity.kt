package com.azul.azulVentas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.azul.azulVentas.modClientes.ui.components.BottomNavigationBar
import com.azul.azulVentas.modClientes.ui.components.FavoritesScreen
import com.azul.azulVentas.modClientes.ui.components.SettingsScreeen
import com.azul.azulVentas.modClientes.ui.ClientesScreen
import com.azul.azulVentas.modClientes.ui.ClientesViewModel
import com.azul.azulVentas.modClientes.ui.model.ClientsScreen
import com.azul.azulVentas.ui.theme.AzulVentasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val clientesViewModel: ClientesViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AzulVentasTheme {

                var selectedItemIndex by remember {
                    mutableStateOf(0)
                }

                Scaffold(
                    topBar = {
                        AzulVentasTopAppBar()
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
                                0 -> {
                                    //ClientesScreen(clientesViewModel)
                                    //AzulVentasCard()
                                    ClientsScreen(clientesViewModel)
                                }
                                1 -> {
                                    FavoritesScreen()
                                }
                                2 -> {
                                    Text (text = "Notifications")
                                }
                                3 -> {
                                    SettingsScreeen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AzulVentasTopAppBar() {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null )
            }
        },

        actions = {

            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Filled.Create, contentDescription = null )
            }

            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null )
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

@Composable
fun AzulVentasBottonAppBar() {

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AzulVentasAlertDialog(
            onDismiss = { showDialog = false }
        )
    }

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        actions = {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = null )
            }

            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Filled.Create, contentDescription = null )
            }

            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Filled.Call, contentDescription = null )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AzulVentasCard() {

    var expanded by remember { mutableStateOf(false) }

    Card(
        onClick = {  expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 15.dp)
    ){
        Box (modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
        ) {
            Column {
                val url =
                    "https://images.unsplash.com/photo-1534628526458-a8de087b1123?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8ZG9nJTIwc2hpaCUyMHR6dXxlbnwwfHwwfHx8MA%3D%3D"

                AsyncImage(
                    model = url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                )

                Text(
                    text = "This is My Dog",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 10.dp)
                )

                val description =
                "Max es un perrito juguetón y amigable de raza mixta. Tiene un pelaje suave y esponjoso de color beige, con orejas caídas y ojos brillantes que reflejan su curiosidad. Siempre está listo para jugar y correr por el parque, pero también disfruta de largas siestas junto a su dueño. Su cola no para de moverse cuando conoce a alguien nuevo, demostrando su afecto y amor por las personas. Con su naturaleza cariñosa y su lealtad incondicional, Max ilumina cada día con su presencia."
                Text(
                    text = description,
                    fontSize = 15.sp,
                    maxLines = if (expanded) Int.MAX_VALUE else 4,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 5.dp)
                )

            }
        }
    }
}

@Composable
fun AzulVentasAlertDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(

        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),

        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        iconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,

        onDismissRequest = {
            onDismiss()
        },

        confirmButton ={
            TextButton(
               onClick = {
                   onDismiss()
               }) {
               Text(text = "Yes, Call my Dog")
            }
        },

        dismissButton ={
            TextButton(
                onClick = {
                    onDismiss()
                }) {
                Text(text = "Not, not yet")
            }

        },

        title = {
            Text(text = "Call My Dog")
        },

        text = {
            Text(text = "Are you sure you want to call Max?")
        },

        icon = {
            Icon(imageVector = Icons.Filled.Call, contentDescription = null )
        }
    )
}