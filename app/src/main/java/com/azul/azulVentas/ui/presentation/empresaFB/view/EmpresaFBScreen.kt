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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.azul.azulVentas.R
import com.azul.azulVentas.domain.model.empresaFB.EmpresaFB
import com.azul.azulVentas.ui.components.ActionButton
import com.azul.azulVentas.ui.components.DefaultBackArrow
import com.azul.azulVentas.ui.components.ErrorSuggestion
import com.azul.azulVentas.ui.components.ReadTextField
import com.azul.azulVentas.ui.components.SearchTextField
import com.azul.azulVentas.ui.presentation.empresaFB.viewmodel.EmpresaFBViewModel
import com.azul.azulVentas.ui.presentation.empresaPG.viewmodel.EmpresaPGViewModel
import com.azul.azulVentas.ui.presentation.login.viewmodel.AuthViewModel
import com.azul.azulVentas.ui.presentation.usuarioEmpresas.viewmodel.UsuarioEmpresasPGViewModel
import com.azul.azulVentas.ui.theme.PrimaryViolet
import com.azul.azulVentas.ui.theme.PrimaryVioletDark
import com.azul.azulVentas.ui.theme.PrimaryVioletLight
import com.azul.azulVentas.ui.theme.PrimaryYellowDark

@Composable
fun EmpresaFBScreen(
    navController: NavController,
    viewModel: EmpresaFBViewModel,
    empresaPGViewModel: EmpresaPGViewModel,
    usuarioEmpresasPGViewModel: UsuarioEmpresasPGViewModel,
    authViewModel: AuthViewModel,
    empresasScreenClicked: (String) -> Unit,
    onRegistrationClicked: ( idEmpresa: String, nomEmpresa: String, idPG: String ) -> Unit
) {

    // Collect the state from the ViewModel
    val empresasState by viewModel.empresasState.collectAsStateWithLifecycle()
    val empresaEncontradaState by viewModel.empresaEncontradaState.collectAsStateWithLifecycle()

    var email by remember { mutableStateOf(TextFieldValue(authViewModel.getUserEmail().toString(),)) }
    var nitEmpresa by remember { mutableStateOf(TextFieldValue("")) }
    val nitErrorState = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    //EmpresaPG ViewModel
    val empresaPG by empresaPGViewModel.empresaPG.observeAsState(initial = emptyList())
    val isLoadingPG by empresaPGViewModel.isLoading.collectAsState()
    val errorPG by empresaPGViewModel.error.collectAsState()

    //UsuarioEmpresasPG ViewModel
    val usuariosempresaPG by usuarioEmpresasPGViewModel.usuarioEmpresas.observeAsState(initial = emptyList())
    val isLoadingUE by usuarioEmpresasPGViewModel.isLoading.collectAsState()
    val errorUE by usuarioEmpresasPGViewModel.error.collectAsState()

    //Evaluar que encontro la empresa en PostGresql
    var empresaPGEncontrada = remember { mutableStateOf<Boolean>(false) }

    // State to control the visibility of the AlertDialog
    var showAlertDialog by remember { mutableStateOf(false) }

    //Inicializar la busqueda de la empresa cada que inicie la pantalla
    var initialLoad by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = true) {
        if (initialLoad){
            viewModel.buscarEmpresaPorNit("")
            initialLoad = false
        }
    }

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
                    .padding(top = 30.dp, start = 32.dp, end = 32.dp)
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

            Spacer(modifier = Modifier.height(12.dp))
            ReadTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                leadingIconRes = R.drawable.ic_factory,
                label = "Email Registrado",
                textValue = email.text
            )

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

                is EmpresaFBViewModel.EmpresaEncontradaState.Loading -> {
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
                            //Este error nos indica que la empresa no esta registrada en Firebase
                            ErrorSuggestion("Verifique los datos ingresados y/o la EMPRESA ${nitEmpresa.text} no esta registrada en PLATAFORMA, debe comunicarce con AZUL SOLUCIONES", isDark = false)
                        }
                    }

                }

                is EmpresaFBViewModel.EmpresaEncontradaState.Success  -> {
                    state.empresa?.let { empresa ->

                        //Buscar Empresa PostGresql
                        empresaPGViewModel.findEmpresaPG(empresa.nit)

                        //Verificar si no hubo error en la busqueda
                        //WS AZUL - PostGresql No esta operando
                        if (!errorPG.isNullOrBlank()) {
                            ErrorSuggestion(errorPG.toString(), isDark = false)
                        }
                        else {
                            if (empresaPG.isEmpty()) {
                                ErrorSuggestion("Debe registrar la EMPRESA en ambiente local (WS - AZUL WEB) y/o Iniciar WS-Contabo.", isDark = false)
                            }
                             //Empresa no existe en PostGresql
                              else {
                                  //empresaPGEncontrada.value = true

                                  //Buscar si el usuario ya tiene registrada la empresa
                                  usuarioEmpresasPGViewModel.listUsuarioEmpresas(empresaPG[0].EMPRESA_ID.toString(), email.text)
                                  if (usuariosempresaPG.isNotEmpty()) {
                                      if (usuariosempresaPG.first().usuarios == 0) {
                                          EmpresaItem(empresa) { onRegistrationClicked(empresa.nit, empresa.nombre, empresaPG[0].EMPRESA_ID.toString()) }
                                      }
                                      else {
                                          Spacer(modifier = Modifier.height(16.dp))
                                          ErrorSuggestion("La Empresa: ${empresa.nombre} con Nít-ID: ${empresa.nit} ya se encuentra registrada por el usuario con EMAIL: ${email.text}.", isDark = false)
                                      }
                                  }
                                    /*
                                    EmpresaItem(empresa) {
                                        val idEmp = empresaPG[0].EMPRESA_ID
                                        usuarioEmpresasPGViewModel.listUsuarioEmpresas(idEmp.toString(), email.text)
                                        if (usuariosempresaPG.isNotEmpty()) {
                                            val usuarios: Int = usuariosempresaPG.first().usuarios
                                            if (usuarios == 0) {
                                                onRegistrationClicked(empresa.nit, empresa.nombre, empresaPG[0].EMPRESA_ID.toString())
                                            } else {
                                                showAlertDialog = true
                                            }
                                        }
                                    }
                                    */
                              }
                        }
                    }
                }

                is EmpresaFBViewModel.EmpresaEncontradaState.Error -> {
                    // Show an error message
                    //Text("Error (WS-AZUL): ${state.message}")
                    Spacer(modifier = Modifier.height(8.dp))
                    ErrorSuggestion("Error (WS-AZUL): ${state.message}", isDark = false)
                }
            }

            if (showAlertDialog) {
                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when outside is clicked or back is pressed
                        showAlertDialog = false
                    },
                    title = { Text("Empresa Registrada") },
                    text = { Text("La EMPRESA ${nitEmpresa.text} ya se encuentra registrada por el USUARIO ${email.text}") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showAlertDialog = false
                            }) {
                            Text("OK")
                        }
                    }
                )
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
                    empresasScreenClicked("")
                },
                onLongClicked = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryVioletLight,
                    contentColor = Color.White
                ),
                shadowColor = PrimaryYellowDark,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
            )
        }
    }
}

@Composable
fun EmpresaItem(
    empresa:
    EmpresaFB,
    registrationScreen: () -> Unit
) {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .clickable{ registrationScreen() }
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, start = 24.dp, end = 24.dp),
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
