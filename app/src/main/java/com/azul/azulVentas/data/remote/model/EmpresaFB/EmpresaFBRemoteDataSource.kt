package com.azul.azulVentas.data.remote.model.EmpresaFB

//data/remote/EmpresaRemoteDataSource.kt
import android.util.Log
import com.azul.azulVentas.domain.model.empresaFB.EmpresaFB
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class EmpresaFBRemoteDataSource @Inject constructor(
    private val database: DatabaseReference
) {
    fun obtenerEmpresas(): Flow<List<EmpresaFB>> = callbackFlow {
        val empresasRef = database.child("Empresas")
        val listener = empresasRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val empresas = mutableListOf<EmpresaFB>()
                for (empresaSnapshot in snapshot.children) {
                    val empresa = empresaSnapshot.getValue(EmpresaFB::class.java)
                    if (empresa != null) {
                        empresas.add(empresa.copy(nit = empresaSnapshot.key ?: ""))
                    } else {
                        Log.e("EmpresaRemoteDataSource", "Error: Could not deserialize Empresa from snapshot: ${empresaSnapshot.key}")
                    }
                }
                trySend(empresas).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("EmpresaRemoteDataSource", "Error getting empresas", error.toException())
                close(error.toException())
            }
        })
        awaitClose { empresasRef.removeEventListener(listener) }
    }

    fun buscarEmpresaPorNit(nit: String): Flow<EmpresaFB?> = callbackFlow {
        val query = database.child("Empresas").orderByChild("nit").equalTo(nit)

        val listener = query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val empresa = snapshot.children.firstOrNull()?.getValue(EmpresaFB::class.java)
                trySend(empresa).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("EmpresaRemoteDataSource", "Error getting empresa by NIT", error.toException())
                close(error.toException())
            }
        })
        awaitClose { query.removeEventListener(listener) }
    }
}