package com.alex.yang.authenticationcompose.feature.auth.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alex.yang.authenticationcompose.feature.auth.domain.model.AuthUser
import com.alex.yang.authenticationcompose.ui.theme.AlexAuthenticationComposeTheme

/**
 * Created by AlexYang on 2025/12/22.
 *
 *
 */
@Composable
fun UserInfoCard(
    user: AuthUser,
    onSignOutClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Avatar(
                modifier = Modifier.padding(top = 10.dp),
                photoUrl = user.photoUrl,
                displayName = user.displayName,
            )

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "個人資訊"
                )

                HorizontalDivider()

                InfoRow(label = "UID", value = user.uid)
                InfoRow(label = "EMAIL", value = user.email ?: "-")
                InfoRow(label = "NAME", value = user.displayName ?: "-")
                InfoRow(
                    label = "Providers",
                    value = user.providerIds.joinToString().ifBlank { "-" }
                )

                Spacer(Modifier.height(4.dp))

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSignOutClick
                ) {
                    Text(text = "SIGN OUT")
                }
            }
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
fun UserInfoCardPreview() {
    AlexAuthenticationComposeTheme {
        UserInfoCard(
            user = AuthUser(
                uid = "1234567890",
                email = "abc@gmail.com",
                displayName = "Alex Yang",
                photoUrl = null,
                providerIds = listOf("google.com", "facebook.com")
            )
        )
    }
}