package com.azul.azulVentas.core.di

import android.content.Context
import androidx.room.Room
import com.azul.azulVentas.core.AzulVentasDatabase
import com.azul.azulVentas.modClientes.data.ClienteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideClienteDao(azulVentasDatabase: AzulVentasDatabase): ClienteDao {
        return azulVentasDatabase.clienteDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AzulVentasDatabase {
        return Room.databaseBuilder(
            appContext,
            AzulVentasDatabase::class.java,
            "azulVentas_Database"
        ).build()
    }

}