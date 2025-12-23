package com.alex.yang.authenticationcompose.feature.auth.domain.repository

import android.app.Activity
import com.alex.yang.authenticationcompose.feature.auth.domain.model.AuthUser

/**
 * Created by AlexYang on 2025/12/19.
 *
 *
 */
interface OAuthRepository {
    suspend fun signInWithGitHub(activity: Activity): Result<AuthUser>
    suspend fun signInWithApple(activity: Activity): Result<AuthUser>
}