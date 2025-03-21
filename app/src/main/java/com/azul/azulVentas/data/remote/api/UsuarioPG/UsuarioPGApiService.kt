package com.azul.azulVentas.data.remote.api.UsuarioPG


import com.azul.azulVentas.core.utils.Constants.GET_PATH_USUARIO
import com.azul.azulVentas.core.utils.Constants.PUT_PATH_USUARIO
import com.azul.azulVentas.data.remote.model.UserPG.UserPGResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioPGApiService {
    @GET(GET_PATH_USUARIO)
    suspend fun getUsuarioPGEmail(@Path("usu_email") usu_email: String): Response<List<UserPGResponse>>

    //@PUT(PUT_PATH_USUARIO)
    //suspend fun insertUserPG(@Body userPG: UserPGResponse): Response<UserPGResponse>
    //Cuando la respuesta del insert en el web servicie tiene body por ejemplo
    //{
    //    "success": true,
    //    "message": "Usuario insertado correctamente"
    //}


    @PUT(PUT_PATH_USUARIO)
    suspend fun insertUserPG(@Body userPG: UserPGResponse): Response<Unit>
    //Cuando la respuesta del insert en el web servicie no tiene body
}