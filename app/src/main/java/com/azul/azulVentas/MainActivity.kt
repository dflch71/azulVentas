package com.azul.azulVentas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.azul.azulEgresos.ui.presentation.egreso.viewmodel.EgresoPeriodoViewModel
import com.azul.azulVentas.ui.presentation.clientes.viewmodel.ClientesViewModel
import com.azul.azulVentas.ui.presentation.compra.viewmodel.CompraDiaViewModel
import com.azul.azulVentas.ui.presentation.compra.viewmodel.CompraPeriodoViewModel
import com.azul.azulVentas.ui.presentation.compra.viewmodel.CompraSemanaViewModel
import com.azul.azulVentas.ui.presentation.container.ScreenContainer
import com.azul.azulVentas.ui.presentation.egreso.viewmodel.EgresoDiaViewModel
import com.azul.azulVentas.ui.presentation.egreso.viewmodel.EgresoSemanaViewModel
import com.azul.azulVentas.ui.presentation.empresa.viewmodel.EmpresaViewModel
import com.azul.azulVentas.ui.presentation.empresaFB.viewmodel.EmpresaFBViewModel
import com.azul.azulVentas.ui.presentation.empresaPG.viewmodel.EmpresaPGViewModel
import com.azul.azulVentas.ui.presentation.empresas.viewmodel.EmpresasPGViewModel
import com.azul.azulVentas.ui.presentation.login.viewmodel.AuthViewModel
import com.azul.azulVentas.ui.presentation.network.viewmodel.NetworkViewModel
import com.azul.azulVentas.ui.presentation.recoverPassword.viewmodel.RecoverPasswordViewModel
import com.azul.azulVentas.ui.presentation.registration.viewmodel.RegisterViewModel
import com.azul.azulVentas.ui.presentation.registrationEmail.viewmodel.RegisterEmailViewModel
import com.azul.azulVentas.ui.presentation.userPG.viewmodel.UserPGViewModel
import com.azul.azulVentas.ui.presentation.usuarioEmpresas.viewmodel.UsuarioEmpresasPGViewModel
import com.azul.azulVentas.ui.presentation.venta.viewmodel.VentaDiaFechaViewModel
import com.azul.azulVentas.ui.presentation.venta.viewmodel.VentaDiaViewModel
import com.azul.azulVentas.ui.presentation.venta.viewmodel.VentaPeriodoViewModel
import com.azul.azulVentas.ui.presentation.venta.viewmodel.VentaSemanaViewModel
import com.azul.azulVentas.ui.presentation.ventaPOS.viewModel.VentaPosDiaFechaViewModel
import com.azul.azulVentas.ui.presentation.ventaPOS.viewModel.VentaPosDiaViewModel
import com.azul.azulVentas.ui.presentation.ventaPOS.viewModel.VentaPosPeriodoViewModel
import com.azul.azulVentas.ui.presentation.ventaPOS.viewModel.VentaPosSemanaViewModel
import com.azul.azulVentas.ui.theme.AzulVentasTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    private lateinit var auth: FirebaseAuth

    //View Models
    private val networkViewModel: NetworkViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val clientesViewModel: ClientesViewModel by viewModels()
    private val empresaViewModel: EmpresaViewModel by viewModels()
    private val empresaFBViewModel: EmpresaFBViewModel by viewModels()
    private val empresaPGViewModel: EmpresaPGViewModel by viewModels()
    private val empresasPGViewModel: EmpresasPGViewModel by viewModels()
    private val usuarioEmpresasPGViewModel: UsuarioEmpresasPGViewModel by viewModels()
    private val userPGViewModel: UserPGViewModel by viewModels()
    private val recoverPasswordViewModel: RecoverPasswordViewModel by viewModels()
    private val registerEmailViewModel: RegisterEmailViewModel by viewModels()
    private val ventaDiaViewModel: VentaDiaViewModel by viewModels()
    private val ventaDiaFechaViewModel: VentaDiaFechaViewModel by viewModels()
    private val ventaSemanaViewModel: VentaSemanaViewModel by viewModels()
    private val ventaPeriodoViewModel: VentaPeriodoViewModel by viewModels()
    private val ventaPosDiaViewModel: VentaPosDiaViewModel by viewModels()
    private val ventaPosDiaFechaViewModel: VentaPosDiaFechaViewModel by viewModels()
    private val ventaPosSemanaViewModel: VentaPosSemanaViewModel by viewModels()
    private val ventaPosPeriodoViewModel: VentaPosPeriodoViewModel by viewModels()
    private val egresoDiaViewModel: EgresoDiaViewModel by viewModels()
    private val egresoSemanaViewModel: EgresoSemanaViewModel by viewModels()
    private val egresoPeriodoViewModel: EgresoPeriodoViewModel by viewModels()
    private val compraDiaViewModel: CompraDiaViewModel by viewModels()
    private val compraSemanaViewModel: CompraSemanaViewModel by viewModels()
    private val compraPeriodoViewModel: CompraPeriodoViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        setContent {

            navHostController = rememberNavController()

            AzulVentasTheme {
                ScreenContainer(
                    navHostController,
                    networkViewModel,
                    authViewModel,
                    registerViewModel,
                    clientesViewModel,
                    empresaViewModel,
                    empresaFBViewModel,
                    empresaPGViewModel,
                    empresasPGViewModel,
                    usuarioEmpresasPGViewModel,
                    userPGViewModel,
                    recoverPasswordViewModel,
                    registerEmailViewModel,
                    ventaDiaViewModel,
                    ventaDiaFechaViewModel,
                    ventaSemanaViewModel,
                    ventaPeriodoViewModel,
                    ventaPosDiaViewModel,
                    ventaPosDiaFechaViewModel,
                    ventaPosSemanaViewModel,
                    ventaPosPeriodoViewModel,
                    egresoDiaViewModel,
                    egresoSemanaViewModel,
                    egresoPeriodoViewModel,
                    compraDiaViewModel,
                    compraSemanaViewModel,
                    compraPeriodoViewModel,
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            //navigate to home
            Log.i("Login", "Login success Main")

            auth.signOut()
        }


    }
}

