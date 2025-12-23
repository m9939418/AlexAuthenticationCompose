package com.alex.yang.authenticationcompose.feature.auth.domain.repository

import com.alex.yang.authenticationcompose.feature.auth.domain.model.AuthUser
import kotlinx.coroutines.flow.Flow

/**
 * Created by AlexYang on 2025/12/18.
 *
 *
 */
interface AuthRepository {
    fun observeAuthState(): Flow<AuthUser?>
    suspend fun signInWithGoogleIdToken(idToken: String): Result<AuthUser>
    suspend fun signInWithFacebookAccessToken(token: String): Result<AuthUser>
    suspend fun signOut()
}