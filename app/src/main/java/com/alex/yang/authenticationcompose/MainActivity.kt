package com.alex.yang.authenticationcompose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alex.yang.authenticationcompose.feature.auth.data.provider.FacebookTokenClient
import com.alex.yang.authenticationcompose.feature.auth.presentation.AuthScreen
import com.alex.yang.authenticationcompose.feature.auth.presentation.AuthViewModel
import com.alex.yang.authenticationcompose.ui.theme.AlexAuthenticationComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var facebookTokenClient: FacebookTokenClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlexAuthenticationComposeTheme {
                val viewModel = hiltViewModel<AuthViewModel>()
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                AuthScreen(
                    state = state,
                    onSignOutClick = { viewModel.onSignOut() },
                    onGoogleLoginInClick = viewModel::onGoogleClick,
                    onGitHubLoginClick = { viewModel.onGitHubClick(activity = this) },
                    onFacebookLoginClick = { viewModel.onFacebookClick(activity = this) }
                )
            }
        }
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("DEBUG", "onActivityResult rc=$requestCode res=$resultCode data=${data?.data}")
        facebookTokenClient.onActivityResult(requestCode, resultCode, data)
    }
}