package com.alex.yang.authenticationcompose.feature.auth.data.provider

import android.app.Activity
import android.content.Intent
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Created by AlexYang on 2025/12/19.
 *
 *
 */
class FacebookTokenClient(
    private val callbackManager: CallbackManager
) {
    suspend fun startLoginSuspend(
        activity: Activity,
        permissions: List<String> = listOf("email", "public_profile")
    ): String = suspendCancellableCoroutine { cont ->
        val loginManager = LoginManager.getInstance()

        val callback = object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                if (cont.isActive) cont.resume(result.accessToken.token)
            }

            override fun onCancel() {
                cont.cancel()
            }

            override fun onError(error: FacebookException) {
                if (cont.isActive) cont.resumeWithException(error)
            }
        }

        loginManager.registerCallback(callbackManager, callback)
        loginManager.logInWithReadPermissions(activity, permissions)

        cont.invokeOnCancellation {
            runCatching { loginManager.unregisterCallback(callbackManager) }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        return callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    fun logout() = LoginManager.getInstance().logOut()
}