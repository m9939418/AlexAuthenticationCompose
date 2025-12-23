package com.alex.yang.authenticationcompose.feature.auth.data.repository

import com.alex.yang.authenticationcompose.feature.auth.data.firebase.FirebaseAuthDataSource
import com.alex.yang.authenticationcompose.feature.auth.data.mapper.toDomain
import com.alex.yang.authenticationcompose.feature.auth.domain.model.AuthUser
import com.alex.yang.authenticationcompose.feature.auth.domain.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by AlexYang on 2025/12/19.
 *
 *
 */
class AuthRepositoryImpl(
    private val firebase: FirebaseAuthDataSource,
) : AuthRepository {
    override fun observeAuthState(): Flow<AuthUser?> =
        firebase.observeUser().map { it?.toDomain() }

    override suspend fun signInWithGoogleIdToken(idToken: String): Result<AuthUser> =
        signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))

    override suspend fun signInWithFacebookAccessToken(token: String): Result<AuthUser> =
        signInWithCredential(FacebookAuthProvider.getCredential(token))

    override suspend fun signOut() {
        firebase.signOut()
    }

    private suspend fun signInWithCredential(credential: AuthCredential): Result<AuthUser> =
        runCatching {
            firebase.signInWithCredential(credential).toDomain()
        }
}