package com.azul.azulVentas.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.azul.azulVentas.data.local.entities.ClienteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {

    @Query("SELECT * FROM ClienteEntity ORDER BY apellido ASC, nombre ASC")
    fun getClientes(): Flow<List<ClienteEntity>>

    @Query("SELECT * FROM ClienteEntity WHERE id = :id")
    fun getCliente(id: Int): ClienteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCliente(cliente: ClienteEntity)

    @Update
    suspend fun updateCliente(cliente: ClienteEntity)

    @Delete
    suspend fun deleteCliente(cliente: ClienteEntity)

}