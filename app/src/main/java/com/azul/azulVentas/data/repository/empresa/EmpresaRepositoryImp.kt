package com.azul.azulVentas.data.repository.empresa

import com.azul.azulVentas.domain.model.empresaFB.EmpresaFB
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EmpresaRepositoryImp @Inject constructor(
    private val FirebaseRealtimeDB: FirebaseDatabase
) : EmpresaRepository {

    private val empresaRef = FirebaseRealtimeDB.getReference("Empresas")

    override suspend fun insertarEmpresa(empresa: EmpresaFB): Result<Boolean> {
        return  try {
            empresaRef.child(empresa.nit).setValue(empresa).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun verificarEmpresa(nit: String): Result<Boolean> {
        return try {
            val snapshot = empresaRef.orderByChild("nit").equalTo(nit).get().await()
            Result.success(snapshot.exists())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}