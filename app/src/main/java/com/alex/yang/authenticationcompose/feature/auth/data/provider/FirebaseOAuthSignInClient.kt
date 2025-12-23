package com.alex.yang.authenticationcompose.feature.auth.data.provider

import android.app.Activity
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * Created by AlexYang on 2025/12/19.
 *
 *
 */
class FirebaseOAuthSignInClient(
    private val activity: Activity,
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun signIn(providerId: String, scopes: List<String> = emptyList()): AuthResult {
        val builder = OAuthProvider.newBuilder(providerId)
        if (scopes.isNotEmpty()) builder.setScopes(scopes)
        val provider = builder.build()

        return suspendCancellableCoroutine { cont ->
            firebaseAuth.startActivityForSignInWithProvider(activity, provider)
                .addOnSuccessListener { cont.resume(it) {} }
                .addOnFailureListener { cont.resumeWith(Result.failure(it)) }
        }
    }
}