package com.alex.yang.authenticationcompose.feature.auth.domain.usecase

import com.alex.yang.authenticationcompose.feature.auth.data.provider.FacebookTokenClient
import com.alex.yang.authenticationcompose.feature.auth.data.provider.GoogleIdTokenClient
import com.alex.yang.authenticationcompose.feature.auth.domain.repository.AuthRepository

/**
 * Created by AlexYang on 2025/12/19.
 *
 *
 */
class SignOutUseCase(
    private val authRepository: AuthRepository,
    private val googleIdTokenClient: GoogleIdTokenClient,
    private val facebookTokenClient: FacebookTokenClient
) {
    suspend operator fun invoke() {
        // 1. Firebase session
        authRepository.signOut()

        // 2. Provider session
        runCatching { googleIdTokenClient.clearCredential() }
        runCatching { facebookTokenClient.logout() }
    }
}