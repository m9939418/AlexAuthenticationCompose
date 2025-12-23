package com.alex.yang.authenticationcompose.feature.auth.data.provider

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

/**
 * Created by AlexYang on 2025/12/19.
 *
 *
 */
class GoogleIdTokenClient(
    private val context: Context,
    private val credentialManager: CredentialManager,
    private val serverClientId: String
) {
    suspend fun getIdToken(): String {
        val option = GetGoogleIdOption.Builder()
            .setServerClientId(serverClientId)
            .setFilterByAuthorizedAccounts(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(option)
            .build()

        val result = credentialManager.getCredential(context, request)
        val googleIdCredential = GoogleIdTokenCredential.createFrom(result.credential.data)

        return googleIdCredential.idToken
    }

    suspend fun clearCredential() {
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
    }
}