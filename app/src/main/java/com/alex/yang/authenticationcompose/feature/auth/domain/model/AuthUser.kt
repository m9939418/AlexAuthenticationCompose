package com.alex.yang.authenticationcompose.feature.auth.domain.model

/**
 * Created by AlexYang on 2025/12/18.
 *
 *
 */
data class AuthUser(
    val uid: String,
    val email: String?,
    val displayName: String?,
    val photoUrl: String?,
    val providerIds: List<String>
)
