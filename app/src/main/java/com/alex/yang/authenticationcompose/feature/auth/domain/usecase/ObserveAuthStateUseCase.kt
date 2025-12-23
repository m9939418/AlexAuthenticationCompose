package com.alex.yang.authenticationcompose.feature.auth.domain.usecase

import com.alex.yang.authenticationcompose.feature.auth.domain.repository.AuthRepository

/**
 * Created by AlexYang on 2025/12/19.
 *
 *
 */
class ObserveAuthStateUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.observeAuthState()
}