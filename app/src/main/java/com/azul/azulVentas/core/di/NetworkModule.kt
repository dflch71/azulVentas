package com.azul.azulVentas.core.di

import android.content.Context
import android.net.ConnectivityManager
import com.azul.azulVentas.data.repository.network.ConnectivityObserver
import com.azul.azulVentas.data.repository.network.NetworkRepository
import com.azul.azulVentas.data.repository.network.NetworkRepositoryImpl
import com.azul.azulVentas.data.repository.network.NetworkStatusTracker
import com.azul.azulVentas.domain.usecases.network.CheckNetworkUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideConnectivityObserver(@ApplicationContext context: Context): ConnectivityObserver {
        return ConnectivityObserver(context)
    }

    @Provides
    @Singleton
    fun provideNetworkRepository(connectivityObserver: ConnectivityObserver): NetworkRepository {
        return NetworkRepositoryImpl(connectivityObserver)
    }


    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideNetworkStatusTracker(connectivityManager: ConnectivityManager): NetworkStatusTracker {
        return NetworkStatusTracker(connectivityManager)
    }
}
