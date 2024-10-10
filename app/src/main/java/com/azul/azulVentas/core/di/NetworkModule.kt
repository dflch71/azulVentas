package com.azul.azulVentas.core.di

import android.content.Context
import com.azul.azulVentas.data.repository.network.NetworkRepository
import com.azul.azulVentas.data.repository.network.NetworkRepositoryImpl
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
    fun provideNetworkRepository(@ApplicationContext context: Context): NetworkRepository {
        return NetworkRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideCheckNetworkUseCase(networkRepository: NetworkRepository): CheckNetworkUseCase {
        return CheckNetworkUseCase(networkRepository)
    }
}
