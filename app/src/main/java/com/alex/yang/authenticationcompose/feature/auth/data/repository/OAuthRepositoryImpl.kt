package com.alex.yang.authenticationcompose.feature.auth.data.repository

import android.app.Activity
import com.alex.yang.authenticationcompose.feature.auth.data.mapper.toDomain
import com.alex.yang.authenticationcompose.feature.auth.domain.model.AuthUser
import com.alex.yang.authenticationcompose.feature.auth.domain.repository.OAuthRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

/**
 * Created by AlexYang on 2025/12/19.
 *
 *
 */
class OAuthRepositoryImpl(
    private val auth: FirebaseAuth
) : OAuthRepository {

    override suspend fun signInWithGitHub(activity: Activity): Result<AuthUser> = runCatching {
        val provider = OAuthProvider.newBuilder("github.com").build()

        val result = auth.startActivityForSignInWithProviderSuspend(activity, provider)
        requireNotNull(result.user).toDomain()
    }

    override suspend fun signInWithApple(activity: Activity): Result<AuthUser> = runCatching {
        val provider = OAuthProvider.newBuilder("apple.com")
            .setScopes(listOf("email", "name"))
            .build()

        val result = auth.startActivityForSignInWithProviderSuspend(activity, provider)
        requireNotNull(result.user).toDomain()
    }
}

private suspend fun FirebaseAuth.startActivityForSignInWithProviderSuspend(
    activity: Activity,
    provider: OAuthProvider
): AuthResult = suspendCancellableCoroutine { cont ->
    startActivityForSignInWithProvider(activity, provider)
        .addOnSuccessListener { cont.resume(it) {} }
        .addOnFailureListener { cont.resumeWithException(it) }
}