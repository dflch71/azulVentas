package com.azul.azulVentas.domain.repository.userPG

import com.azul.azulVentas.data.remote.api.UsuarioPG.UsuarioPGApiService
import com.azul.azulVentas.data.remote.mappers.userPG.toDomain
import com.azul.azulVentas.data.remote.mappers.userPG.toResponse
import com.azul.azulVentas.data.remote.model.UserPG.UserPGResponse
import com.azul.azulVentas.domain.model.userPG.UserPG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import android.util.Log
import com.google.gson.Gson
import com.azul.azulVentas.core.utils.Result


class UserPGRepositoryImpl @Inject constructor(
    private val apiService: UsuarioPGApiService
): UserPGRepository {
    override suspend fun getUserPGEmail(usu_email: String): List<UserPG> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getUsuarioPGEmail(usu_email)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }

    override suspend fun insertUserPG(userPG: UserPG): Result<UserPG> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<UserPGResponse> = apiService.insertUserPG(userPG.toResponse())
                if (response.isSuccessful) {
                    val insertedUser = response.body()?.toDomain()
                    if (insertedUser != null) {
                        Result.Success(insertedUser)
                    } else {
                        Result.ErrorPG(Throwable("Failed to parse the response"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = if (!errorBody.isNullOrEmpty()) {
                        val errorResponse: ErrorResponse? = try {
                            Gson().fromJson(errorBody, ErrorResponse::class.java)
                        } catch (e: Exception) { null }
                        errorResponse?.error ?: "Unknown error"
                    } else { "Unknown error" }

                    Log.e("insertUserPG", "Error inserting user: $errorMessage")
                    Result.ErrorPG(Throwable(errorMessage))
                }
            } catch (e: Exception) {
                Log.e("insertUserPG", "Error inserting user", e.cause)
                Result.ErrorPG(e)
            }
        }
    }
}

data class ErrorResponse(
    val error: String
)
