package com.azul.azulVentas.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.azul.azulVentas.modClientes.data.ClienteDao
import com.azul.azulVentas.modClientes.data.ClienteEntity

@Database(entities = [ClienteEntity::class], version = 1)
abstract class AzulVentasDatabase: RoomDatabase() {

    abstract fun clienteDao(): ClienteDao

}