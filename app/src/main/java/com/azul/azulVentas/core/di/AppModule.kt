package com.azul.azulVentas.core.di

import com.azul.azulVentas.data.remote.FirebaseAuthService
import com.azul.azulVentas.data.repository.UserRepositoryImpl
import com.azul.azulVentas.domain.repositories.user.UserRepository
import com.azul.azulVentas.domain.usecases.user.LogOutUseCase
import com.azul.azulVentas.domain.usecases.user.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthService(): FirebaseAuthService {
        return FirebaseAuthService()
    }

    @Provides
    @Singleton
    fun provideUserRepository(authService: FirebaseAuthService): UserRepository {
        return UserRepositoryImpl(authService)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(userRepository: UserRepository): LoginUseCase {
        return LoginUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideLogOutUseCase(userRepository: UserRepository): LogOutUseCase {
        return LogOutUseCase(userRepository)
    }
}