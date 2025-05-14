package com.azul.azulVentas.core.utils

object Constants {


    //var IP      = "192.168.72.222" //LocalHost
    //var IP      = "5.189.133.32" //Remote Host .. Contabo
    var IP       = "144.91.111.80" //Contabo-Nuevo
    var PUERTO   = "9060"
    var AZUL_URL = "http://$IP:$PUERTO/datasnap/rest/TServerMethods/"

    //EMPRESA
    //http://localhost:9060/datasnap/rest/TServerMethods/Empresa/901253102
    const val GET_PATH_EMPRESA = "Empresa/{Nit_ID}"

    //REGISTAR USUARIO
    //http://localhost:9060/datasnap/rest/TServerMethods/usuario
    const val GET_PATH_USUARIO = "Usuario/{usu_email}"
    const val PUT_PATH_USUARIO = "Usuario/"

    //USUARIO-EMPRESA
    //http://localhost:9060/datasnap/rest/TServerMethods/validarUsuarioEmail/ID_EMPRESA/EMAIL
    const val GET_PATH_VALIDAR_USUARIO_EMAIL = "validarUsuarioEmail/{idEmpresa}/{Email}"

    //EMPRESA-EMAIL
    //http://localhost:9060/datasnap/rest/TServerMethods/validarEmpresasEmail/EMAIL
    const val GET_PATH_VALIDAR_EMPRESAS_EMAIL = "validarEmpresaSEmail/{Email}"


    //VENTA
    //http://localhost:9060/datasnap/rest/TServerMethods/VentaxHora/901253102
    const val GET_PATH_VENTA_DIA = "VentaxHora/{EmpresaID}"
    const val GET_PATH_VENTA_SEMANA = "VentaUltimaSemana/{EmpresaID}"
    const val GET_PATH_VENTA_PERIODO = "VentaPeriodos/{EmpresaID}"

    //VENTA POS
    //http://localhost:9060/datasnap/rest/TServerMethods/VentaPosxHora/901253102
    const val GET_PATH_VENTA_POS_DIA = "VentaPosxHora/{EmpresaID}"
    const val GET_PATH_VENTA_POS_SEMANA = "VentaPosUltimaSemana/{EmpresaID}"
    const val GET_PATH_VENTA_POS_PERIODO = "VentaPosPeriodos/{EmpresaID}"

    //COMPRA
    //http://localhost:9060/datasnap/rest/TServerMethods/CompraxHora/901253102
    const val GET_PATH_COMPRA_DIA = "CompraxHora/{EmpresaID}"
    const val GET_PATH_COMPRA_SEMANA = "CompraUltimaSemana/{EmpresaID}"
    const val GET_PATH_COMPRA_PERIODO = "CompraPeriodos/{EmpresaID}"

    //EGRESO
    //http://localhost:9060/datasnap/rest/TServerMethods/EgresoxHora/901253102
    const val GET_PATH_EGRESO_DIA = "EgresoxHora/{EmpresaID}"
    const val GET_PATH_EGRESO_SEMANA = "EgresoUltimaSemana/{EmpresaID}"
    const val GET_PATH_EGRESO_PERIODO = "EgresoPeriodos/{EmpresaID}"
}