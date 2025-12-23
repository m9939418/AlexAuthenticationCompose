package com.alex.yang.authenticationcompose.feature.auth.presentation.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.alex.yang.authenticationcompose.BuildConfig
import com.alex.yang.authenticationcompose.feature.auth.data.firebase.FirebaseAuthDataSource
import com.alex.yang.authenticationcompose.feature.auth.data.provider.FacebookTokenClient
import com.alex.yang.authenticationcompose.feature.auth.data.provider.GoogleIdTokenClient
import com.alex.yang.authenticationcompose.feature.auth.data.repository.AuthRepositoryImpl
import com.alex.yang.authenticationcompose.feature.auth.data.repository.OAuthRepositoryImpl
import com.alex.yang.authenticationcompose.feature.auth.domain.repository.AuthRepository
import com.alex.yang.authenticationcompose.feature.auth.domain.repository.OAuthRepository
import com.alex.yang.authenticationcompose.feature.auth.domain.usecase.ObserveAuthStateUseCase
import com.alex.yang.authenticationcompose.feature.auth.domain.usecase.SignInUseCase
import com.alex.yang.authenticationcompose.feature.auth.domain.usecase.SignOutUseCase
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by AlexYang on 2025/12/19.
 *
 *
 */
@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideCallbackManager(): CallbackManager = CallbackManager.Factory.create()

    @Provides
    @Singleton
    fun provideFacebookTokenClient(
        callbackManager: CallbackManager
    ): FacebookTokenClient = FacebookTokenClient(callbackManager)

    @Provides
    @Singleton
    fun provideFirebaseAuthDataSource(firebaseAuth: FirebaseAuth): FirebaseAuthDataSource =
        FirebaseAuthDataSource(firebaseAuth)

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuthDataSource: FirebaseAuthDataSource): AuthRepository =
        AuthRepositoryImpl(firebaseAuthDataSource)

    @Provides
    @Singleton
    fun provideOAuthRepository(firebaseAuth: FirebaseAuth): OAuthRepository =
        OAuthRepositoryImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideCredentialManager(@ApplicationContext context: Context): CredentialManager =
        CredentialManager.create(context)

    @Provides
    @Singleton
    @Named("googleServerClientId")
    fun provideGoogleServerClientId(): String = BuildConfig.GOOGLE_WEB_CLINENT_ID

    @Provides
    @Singleton
    fun provideGoogleIdTokenClient(
        @ApplicationContext context: Context,
        credentialManager: CredentialManager,
        @Named("googleServerClientId") serverClientId: String
    ): GoogleIdTokenClient = GoogleIdTokenClient(
        context = context,
        credentialManager = credentialManager,
        serverClientId = serverClientId
    )

    @Provides
    @Singleton
    fun provideSignInUseCase(
        authRepository: AuthRepository,
        oAuthRepository: OAuthRepository,
        googleIdTokenClient: GoogleIdTokenClient,
        facebookTokenClient: FacebookTokenClient
    ): SignInUseCase =
        SignInUseCase(authRepository, oAuthRepository, googleIdTokenClient, facebookTokenClient)

    @Provides
    @Singleton
    fun provideObserveAuthStateUseCase(repository: AuthRepository): ObserveAuthStateUseCase =
        ObserveAuthStateUseCase(repository)

    @Provides
    @Singleton
    fun provideSignOutUseCase(
        authRepository: AuthRepository,
        googleIdTokenClient: GoogleIdTokenClient,
        facebookTokenClient: FacebookTokenClient
    ): SignOutUseCase =
        SignOutUseCase(authRepository, googleIdTokenClient, facebookTokenClient)
}