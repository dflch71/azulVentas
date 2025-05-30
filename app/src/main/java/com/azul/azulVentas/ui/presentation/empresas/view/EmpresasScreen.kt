package com.azul.azulVentas.ui.presentation.empresas.view

//import android.view.WindowInsets
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.azul.azulVentas.R
import com.azul.azulVentas.domain.model.empresaPG.EmpresaPG
import com.azul.azulVentas.ui.components.DefaultBackArrow
import com.azul.azulVentas.ui.presentation.empresas.viewmodel.EmpresasPGViewModel
import com.azul.azulVentas.ui.presentation.login.viewmodel.AuthViewModel
import com.azul.azulVentas.ui.theme.PrimaryViolet
import com.azul.azulVentas.ui.theme.PrimaryVioletDark
import kotlinx.coroutines.delay

@Composable
fun EmpresasScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    empresasPGViewModel: EmpresasPGViewModel,
    HomeScreenClicked: (String, String) -> Unit,
    RegisterScreenClicked: () -> Unit
){
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val empresasPG by empresasPGViewModel.empresasPG.observeAsState(initial = emptyList())
    val isLoadingPG by empresasPGViewModel.isLoading.collectAsState()
    val errorPG by empresasPGViewModel.error.collectAsState()

    var showLoading by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = true) {
        delay(2000)
        empresasPGViewModel.listEmpresasPG(authViewModel.getUserEmail().toString())
        showLoading = false
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
                    DefaultBackArrow { navController.popBackStack() }
                }
                Box(modifier = Modifier.weight(1.5f)) {
                    Text(
                        text = "Empresas Registradas",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Usuario: ${authViewModel.getUserEmail()}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )


            Spacer(modifier = Modifier.height(8.dp))

            //TODO: Pendiente de verificar el scroll cuando se llega al final
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(color = Color.Transparent)
            ){

                if (showLoading) {
                    // Show progress indicator during the delay
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color.White)
                } else {
                    // Show the content after the delay
                    if (empresasPG.isNotEmpty()) {
                        listarEmpresas(empresasPG, HomeScreenClicked)
                    } else {
                        val msg: String = if (showLoading) "Cargando..." else "- No hay empresas registradas.\n\n- WS de empresas no responde. $errorPG"
                        Text(
                            modifier = Modifier.padding(24.dp),
                            text = msg,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
    AddEmpresas { RegisterScreenClicked() }
}

@Composable
private fun listarEmpresas(
    empresasPG: List<EmpresaPG>,
    HomeScreenClicked: (String, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(empresasPG) { empresa ->
            CardEmpresa(
                ID = empresa.EMP_TERCERO,
                RazonSocial = empresa.EMP_RAZON_SOCIAL,
                Direccion = empresa.EMP_DIRECCION,
                Ciudad = empresa.EMP_CIUDAD,
                onClickHome = { HomeScreenClicked( empresa.EMP_TERCERO, empresa.EMP_RAZON_SOCIAL ) }
            )
        }
    }
}

@Composable
fun AddEmpresas(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = { onClick() },
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .padding(WindowInsets.navigationBars.asPaddingValues())
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }
    }
}

@Composable
fun CardEmpresa(
    ID: String,
    RazonSocial: String,
    Direccion: String,
    Ciudad: String,
    onClickHome: () -> Unit
){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .clickable { onClickHome() }
            .fillMaxSize()
            .padding(top = 10.dp, start = 32.dp, end = 32.dp),

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextEmpresa(ID, R.drawable.ic_identification)
            Spacer(modifier = Modifier.size(4.dp))
            DivideLine()
            TextEmpresa(RazonSocial, R.drawable.ic_business)
            Spacer(modifier = Modifier.size(4.dp))
            DivideLine()
            TextEmpresa(Direccion, R.drawable.ic_location)
            Spacer(modifier = Modifier.size(4.dp))
            DivideLine()
            TextEmpresa(Ciudad, R.drawable.ic_city)
        }
    }
}

@Composable
private fun TextEmpresa(
    ID: String,
    @DrawableRes iconText: Int,
) {
    Row() {
        Icon(
            painter = painterResource(iconText),
            "ID",
            tint = Color.Gray,
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = ID,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.DarkGray,
        )
    }
}

@Composable
private fun DivideLine(){
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 1.dp),
        thickness = 1.dp,
        color = colorResource(id = R.color.light_Grey)
    )
}