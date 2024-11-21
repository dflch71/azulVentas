package com.azul.azulVentas.core.di

import android.content.Context
import android.content.SharedPreferences
import com.azul.azulVentas.data.local.sharePreferences.SessionManager
import com.azul.azulVentas.data.remote.FirebaseAuthService
import com.azul.azulVentas.data.repository.empresa.EmpresaRepository
import com.azul.azulVentas.data.repository.empresa.EmpresaRepositoryImp
import com.azul.azulVentas.data.repository.user.UserRepository
import com.azul.azulVentas.data.repository.user.UserRepositoryImpl
import com.azul.azulVentas.domain.usecases.empresa.AddEmpresaUseCase
import com.azul.azulVentas.domain.usecases.user.IsUserLoggedInUseCase
import com.azul.azulVentas.domain.usecases.user.LoginUseCase
import com.azul.azulVentas.domain.usecases.user.RegisterUseCase
import com.azul.azulVentas.domain.usecases.user.SignOutUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthService(
        firebaseAuth: FirebaseAuth,
        sessionManager: SessionManager
    ): FirebaseAuthService {
        return FirebaseAuthService(firebaseAuth, sessionManager)
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
    fun provideUserLoggedUseCase(userRepository: UserRepository): IsUserLoggedInUseCase {
        return IsUserLoggedInUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(userRepository: UserRepository): RegisterUseCase {
        return RegisterUseCase(userRepository)
    }


    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSessionManager(sharedPreferences: SharedPreferences): SessionManager {
        return SessionManager(sharedPreferences)
    }

    // Proveer instancia de Firebase Realtime Database
    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    // Proveer UserRepository
    @Provides
    @Singleton
    fun provideEmpresaRepository(databaseReference: FirebaseDatabase): EmpresaRepository {
        return EmpresaRepositoryImp(databaseReference)
    }

    // Proveer InsertarUsuarioUseCase
    @Provides
    fun provideAddEmpresaUseCase(empresaRepository: EmpresaRepository): AddEmpresaUseCase {
        return AddEmpresaUseCase(empresaRepository )
    }


}