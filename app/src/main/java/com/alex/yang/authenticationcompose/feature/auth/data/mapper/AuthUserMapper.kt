package com.alex.yang.authenticationcompose.feature.auth.data.mapper

import com.alex.yang.authenticationcompose.feature.auth.domain.model.AuthUser
import com.google.firebase.auth.FirebaseUser

/**
 * Created by AlexYang on 2025/12/22.
 *
 *
 */
fun FirebaseUser.toDomain() = AuthUser(
    uid = uid,
    email = email,
    displayName = displayName,
    photoUrl = photoUrl?.toString(),
    providerIds = providerData.map { it.providerId }
)