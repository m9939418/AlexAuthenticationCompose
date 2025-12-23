package com.alex.yang.authenticationcompose.feature.auth.presentation.di

import android.app.Activity
import com.alex.yang.authenticationcompose.feature.auth.data.provider.FirebaseOAuthSignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * Created by AlexYang on 2025/12/19.
 *
 *
 */
@Module
@InstallIn(ActivityComponent::class)
object AuthActivityModule {
    @Provides
    fun provideFirebaseOAuthSignInClient(
        activity: Activity,
        firebaseAuth: FirebaseAuth
    ): FirebaseOAuthSignInClient = FirebaseOAuthSignInClient(activity, firebaseAuth)
}