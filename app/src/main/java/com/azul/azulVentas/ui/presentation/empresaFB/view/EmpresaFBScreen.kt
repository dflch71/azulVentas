package com.azul.azulVentas.ui.presentation.empresaFB.view


// presentation/ui/EmpresaListScreen.kt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.azul.azulVentas.R
import com.azul.azulVentas.domain.model.empresa.Empresa
import com.azul.azulVentas.ui.components.ActionButton
import com.azul.azulVentas.ui.components.DefaultBackArrow
import com.azul.azulVentas.ui.components.ErrorSuggestion
import com.azul.azulVentas.ui.components.ReadTextField
import com.azul.azulVentas.ui.components.SearchTextField
import com.azul.azulVentas.ui.presentation.empresaFB.viewmodel.EmpresaFBViewModel
import com.azul.azulVentas.ui.theme.DarkTextColor
import com.azul.azulVentas.ui.theme.PrimaryViolet
import com.azul.azulVentas.ui.theme.PrimaryVioletDark
import com.azul.azulVentas.ui.theme.PrimaryVioletLight
import com.azul.azulVentas.ui.theme.PrimaryYellowDark
import com.azul.azulVentas.ui.theme.PrimaryYellowLight

@Composable
fun EmpresaFBScreen(
    navController: NavController,
    viewModel: EmpresaFBViewModel,
    loginScreenClicked: () -> Unit,
    onRegistrationClicked: ( idEmpresa: String, nomEmpresa: String ) -> Unit
) {

    //val empresas by viewModel.empresas.collectAsState()
    //val empresaEncontrada by viewModel.empresaEncontrada.collectAsState()
    //var nitBusqueda by remember { mutableStateOf("") }

    // Collect the state from the ViewModel
    val empresasState by viewModel.empresasState.collectAsStateWithLifecycle()
    val empresaEncontradaState by viewModel.empresaEncontradaState.collectAsStateWithLifecycle()


    var nitEmpresa by remember { mutableStateOf(TextFieldValue("")) }
    val nitErrorState = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Box(
        modifier = Modifier
            .fillMaxWidth() // Ocupa todo el ancho
            .height(screenHeight) // Ocupa la altura de la pantalla
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        *arrayOf(
                            0f to PrimaryViolet,
                            1f to PrimaryVioletDark
                        )
                    )
                )
                .systemBarsPadding()
                .imePadding()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .padding(top = 32.dp, start = 32.dp, end = 32.dp, bottom = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Box(modifier = Modifier.weight(0.5f)) {
                    DefaultBackArrow {
                        viewModel.buscarEmpresaPorNit("")
                        navController.popBackStack()
                    }
                }
                Box(modifier = Modifier.weight(1.5f)) {
                    Text(
                        text = "Buscar Empresa",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700)
                    )
                }
            }

            //controlar el Scroll, el teclado y el foco
            val keyboardController = LocalSoftwareKeyboardController.current
            val focusManager = LocalFocusManager.current // Gestor de enfoque
            val focusRequester = remember { FocusRequester() }

            Spacer(modifier = Modifier.height(8.dp))
            SearchTextField(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .focusRequester(focusRequester),
                placeholder = "Nít - ID",
                leadingIconRes = R.drawable.ic_identification,
                trailingIconRes = R.drawable.ic_search,
                label = "Nít - ID",
                errorState = nitErrorState,
                keyboardType = KeyboardType.Number,
                visualTransformation = VisualTransformation.None,
                imeAction = ImeAction.Search,
                onChanged = { newNit -> nitEmpresa = newNit },
                lengthChar = 20,
                onClicked = {
                    viewModel.buscarEmpresaPorNit(nitEmpresa.text)
                    keyboardController?.hide()
                    focusManager.clearFocus() // Quita el enfoque del TextField
                }
            )


            // Display the found company
            when (val state = empresaEncontradaState) {
                EmpresaFBViewModel.EmpresaEncontradaState.Loading -> {
                    // Show a loading indicator
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Text(
                                text = "Digite Nít-ID...",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight(400)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                color = PrimaryYellowDark,
                                trackColor = PrimaryVioletLight
                            )
                        }
                    }
                }

                is EmpresaFBViewModel.EmpresaEncontradaState.Empty -> {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "EMPRESA",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(horizontal = 8.dp),
                                color = Color.White
                            )
                            Text(
                                text = nitEmpresa.text,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(horizontal = 8.dp),
                                color = Color.White
                            )

                            ErrorSuggestion("No encontrada.")
                            ErrorSuggestion("No esta registrada en el sistema.")
                        }
                    }

                }

                is EmpresaFBViewModel.EmpresaEncontradaState.Success  -> {
                    state.empresa?.let { empresa ->
                            EmpresaItem(empresa = empresa) {
                                onRegistrationClicked(empresa.nit, empresa.nombre)
                            }
                    }

                }

                is EmpresaFBViewModel.EmpresaEncontradaState.Error -> {
                    // Show an error message
                    Text("Error: ${state.message}")
                }
            }

            // Display the list of companies (if needed)
            when (val state = empresasState) {
                EmpresaFBViewModel.EmpresaState.Loading -> {
                    // Show a loading indicator
                    //Text("Cargando empresas...")
                }

                is EmpresaFBViewModel.EmpresaState.Success -> {
                    // You can use LazyColumn here to display the list
                    // For this example, we'll just show a count
                    //Text("Total de empresas: ${state.empresas.size}")
                }

                is EmpresaFBViewModel.EmpresaState.Error -> {
                    // Show an error message
                    //Text("Error: ${state.message}")
                }
            }



            Spacer(modifier = Modifier.weight(weight = 1f))
            ActionButton(
                text = "Cancelar",
                isNavigationArrowVisible = false,
                onClicked = {
                    viewModel.buscarEmpresaPorNit("")
                    loginScreenClicked()
                },
                onLongClicked = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryVioletLight,
                    contentColor = Color.White
                ),
                shadowColor = PrimaryYellowDark,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
            )


        /* Este código funciona es el ejemplo para cargar
        la lista completa de empresas, tener en cuenta que se
        debe corregir el viewmodel de EmpresaFB
        // Campo de búsqueda
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = nitBusqueda,
            onValueChange = { nitBusqueda = it },
            label = { Text("Buscar por NIT") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { viewModel.buscarEmpresaPorNit(nitBusqueda) },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Buscar")
        }

        // Mostrar empresa encontrada
            empresaEncontrada?.let { empresa ->
                EmpresaItem(empresa = empresa)
            }
        // Lista de empresas
        //LazyColumn {
        //    items(empresas) { empresa ->
        //        EmpresaItem(empresa = empresa)
        //    }
        }
        */
        }
    }
}

@Composable
fun EmpresaItem(
    empresa: Empresa,
    registrationScreen: () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .clickable{ registrationScreen() }
            .fillMaxWidth()
            .padding(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)

    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Image(
                painter = rememberAsyncImagePainter(model = empresa.picEmpresa),
                contentDescription = "Imagen de ${empresa.nombre}",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(128.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(4.dp))
            ReadTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                leadingIconRes = R.drawable.ic_identification,
                label = "Nít - ID",
                textValue = empresa.nit
            )

            Spacer(modifier = Modifier.height(4.dp))
            ReadTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                leadingIconRes = R.drawable.ic_factory,
                label = "Nombre Empresa",
                textValue = empresa.nombre
            )

            Spacer(modifier = Modifier.height(4.dp))
            ReadTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                leadingIconRes = R.drawable.ic_map,
                label = "Dirección",
                textValue = empresa.direccion
            )

            Spacer(modifier = Modifier.height(4.dp))
            ReadTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                leadingIconRes = R.drawable.ic_person,
                label = "Representante Legal",
                textValue = empresa.repLegal,
            )

            /*
            Spacer(modifier = Modifier.height(4.dp))
            ReadTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                leadingIconRes = R.drawable.ic_phone,
                label = "Teléfono",
                textValue = empresa.telefono
            )
            */

            Spacer(modifier = Modifier.height(4.dp))
            ReadTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                leadingIconRes = R.drawable.ic_email,
                label = "Email",
                textValue = empresa.email
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}