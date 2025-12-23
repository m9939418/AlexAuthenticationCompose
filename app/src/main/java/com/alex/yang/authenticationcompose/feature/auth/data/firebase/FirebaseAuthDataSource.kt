package com.alex.yang.authenticationcompose.feature.auth.data.firebase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

/**
 * Created by AlexYang on 2025/12/19.
 *
 *
 */
class FirebaseAuthDataSource(private val auth: FirebaseAuth) {
    fun observeUser(): Flow<FirebaseUser?> =
        callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
                trySend(firebaseAuth.currentUser)
            }

            auth.addAuthStateListener(listener)

            awaitClose { auth.removeAuthStateListener(listener) }
        }

    // Google | Facebook Account Login
    suspend fun signInWithCredential(credential: AuthCredential): FirebaseUser {
        val result = suspendCancellableCoroutine { cont ->
            auth.signInWithCredential(credential)
                .addOnSuccessListener { cont.resume(it) {} }
                .addOnFailureListener { cont.resumeWithException(it) }
        }

        return requireNotNull(result.user)
    }

    fun signOut() = auth.signOut()
}