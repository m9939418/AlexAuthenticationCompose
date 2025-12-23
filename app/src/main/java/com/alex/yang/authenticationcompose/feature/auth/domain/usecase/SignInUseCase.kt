package com.alex.yang.authenticationcompose.feature.auth.domain.usecase

import android.app.Activity
import com.alex.yang.authenticationcompose.feature.auth.data.provider.FacebookTokenClient
import com.alex.yang.authenticationcompose.feature.auth.data.provider.GoogleIdTokenClient
import com.alex.yang.authenticationcompose.feature.auth.domain.model.AuthUser
import com.alex.yang.authenticationcompose.feature.auth.domain.repository.AuthRepository
import com.alex.yang.authenticationcompose.feature.auth.domain.repository.OAuthRepository

/**
 * Created by AlexYang on 2025/12/19.
 *
 *
 */
class SignInUseCase(
    private val authRepo: AuthRepository,
    private val oauthRepo: OAuthRepository,
    private val googleClient: GoogleIdTokenClient,
    private val facebookTokenClient: FacebookTokenClient
) {
    suspend fun google(): Result<AuthUser> {
        val idToken = runCatching {
            googleClient.getIdToken()
        }.getOrElse { return Result.failure(it) }

        return authRepo.signInWithGoogleIdToken(idToken)
    }

    suspend fun facebook(activity: Activity): Result<AuthUser> {
        val token = runCatching {
            facebookTokenClient.startLoginSuspend(activity)
        }.getOrElse { return Result.failure(it) }

        return authRepo.signInWithFacebookAccessToken(token)
    }

    suspend fun github(activity: Activity): Result<AuthUser> =
        oauthRepo.signInWithGitHub(activity)

    suspend fun apple(activity: Activity): Result<AuthUser> =
        oauthRepo.signInWithApple(activity)
}