package com.azul.azulVentas.core.di

import com.azul.azulVentas.data.remote.FirebaseAuthService
import com.azul.azulVentas.data.repository.UserRepositoryImpl
import com.azul.azulVentas.domain.repositories.user.UserRepository
import com.azul.azulVentas.domain.usecases.user.SignOutUseCase
import com.azul.azulVentas.domain.usecases.user.LoginUseCase
import com.azul.azulVentas.domain.usecases.user.RegisterUseCase
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
    fun provideSignOutUseCase(userRepository: UserRepository): SignOutUseCase {
        return SignOutUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(userRepository: UserRepository): RegisterUseCase {
        return RegisterUseCase(userRepository)
    }
}