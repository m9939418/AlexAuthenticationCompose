package com.alex.yang.authenticationcompose.feature.auth.presentation

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.yang.authenticationcompose.feature.auth.domain.model.AuthUser
import com.alex.yang.authenticationcompose.feature.auth.domain.usecase.ObserveAuthStateUseCase
import com.alex.yang.authenticationcompose.feature.auth.domain.usecase.SignInUseCase
import com.alex.yang.authenticationcompose.feature.auth.domain.usecase.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

/**
 * Created by AlexYang on 2025/12/19.
 *
 *
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val observeAuth: ObserveAuthStateUseCase,
    private val signOut: SignOutUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeAuth().collect { user ->
                _uiState.update { it.copy(user = user) }
            }
        }
    }

    fun onGoogleClick() = launchSignIn { signInUseCase.google() }

    fun onFacebookClick(activity: Activity) = launchSignIn { signInUseCase.facebook(activity) }

    fun onGitHubClick(activity: Activity) = launchSignIn { signInUseCase.github(activity) }

    fun onAppleClick(activity: Activity) = launchSignIn { signInUseCase.apple(activity) }

    fun onSignOut() = viewModelScope.launch { signOut() }

    private fun launchSignIn(block: suspend () -> Result<AuthUser>) = viewModelScope.launch {
        _uiState.update { it.copy(loading = true, error = null) }

        val result = block()

        result.fold(
            onSuccess = { user ->
                _uiState.update { it.copy(loading = false, user = user, error = null) }
            },
            onFailure = { e ->
                _uiState.update { it.copy(loading = false, error = e.toUiMessage()) }
            }
        )
    }

    private fun Throwable.toUiMessage(): String {
        if (this is CancellationException) return "已取消登入"

        val msg = message.orEmpty()

        return when {
            msg.contains("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL", true) ->
                "此帳號已使用其他登入方式註冊，請改用原本的登入方式。"
            msg.contains("network", true) -> "網路異常，請確認連線後再試一次。"
            else -> "登入失敗：${message ?: "未知錯誤"}"
        }
    }

    data class AuthUiState(
        val loading: Boolean = false,
        val user: AuthUser? = null,
        val error: String? = null
    )
}