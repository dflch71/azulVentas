package com.azul.azulVentas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.azul.azulVentas.ui.presentation.clientes.viewmodel.ClientesViewModel
import com.azul.azulVentas.ui.presentation.container.ScreenContainer
import com.azul.azulVentas.ui.presentation.login.viewmodel.AuthViewModel
import com.azul.azulVentas.ui.theme.AzulVentasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
   // private lateinit var auth: FirebaseAuth

    private val authViewModel: AuthViewModel by viewModels()
    private val clientesViewModel: ClientesViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //auth = Firebase.auth

        setContent {

            navHostController = rememberNavController()


            AzulVentasTheme {

                //NavigationWrapper(navHostController, auth, clientesViewModel)
                //ScreenContainer(navHostController,  auth,  authViewModel, clientesViewModel)
                ScreenContainer(navHostController, authViewModel, clientesViewModel)

/*
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
                */

            }
        }
    }

    override fun onStart() {
        super.onStart()

        /*val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            //navigate to home
            Log.i("Login", "Login success Main")

            auth.signOut()
        }*/
    }
}

/*

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
}*/

