package com.alex.yang.authenticationcompose.feature.auth.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alex.yang.authenticationcompose.R
import com.alex.yang.authenticationcompose.feature.auth.domain.model.AuthUser
import com.alex.yang.authenticationcompose.feature.auth.presentation.component.SectionHeader
import com.alex.yang.authenticationcompose.feature.auth.presentation.component.SocialIconButton
import com.alex.yang.authenticationcompose.feature.auth.presentation.component.UserInfoCard
import com.alex.yang.authenticationcompose.ui.theme.AlexAuthenticationComposeTheme

/**
 * Created by AlexYang on 2025/12/19.
 *
 *
 */
@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    state: AuthViewModel.AuthUiState,
    onSignOutClick: () -> Unit = {},
    onGoogleLoginInClick: () -> Unit = {},
    onGitHubLoginClick: () -> Unit = {},
    onFacebookLoginClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 80.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SectionHeader()

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Google Login Button
            SocialIconButton(
                painter = painterResource(R.drawable.ic_google),
                enabled = !state.loading,
                onClick = onGoogleLoginInClick
            )

            // Facebook Login Button
            SocialIconButton(
                painter = painterResource(R.drawable.ic_facebook),
                enabled = !state.loading,
                onClick = onFacebookLoginClick
            )

            // GitHub Login Button
            SocialIconButton(
                painter = painterResource(R.drawable.ic_github),
                enabled = !state.loading,
                onClick = onGitHubLoginClick
            )
        }

        Spacer(Modifier.height(20.dp))

        // Error
        state.error?.let {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                text = it
            )
            Spacer(Modifier.height(8.dp))
        }

        // Loading
        if (state.loading) {
            CircularProgressIndicator(modifier = Modifier.size(36.dp))
        }

        // User Info
        state.user?.let { user ->
            Spacer(Modifier.height(16.dp))

            UserInfoCard(
                user = user,
                onSignOutClick = onSignOutClick
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode"
)
@Preview(
    showBackground = true,
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun AuthScreenPreview() {
    AlexAuthenticationComposeTheme {
        AuthScreen(
            state = AuthViewModel.AuthUiState(
                user = AuthUser(
                    uid = "1234567890",
                    email = "abc@gmail.com",
                    displayName = "Alex Yang",
                    photoUrl = null,
                    providerIds = listOf("google.com", "facebook.com")
                ),
                loading = false,
                error = null
            ),
        )
    }
}