package com.alex.yang.authenticationcompose.feature.auth.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alex.yang.authenticationcompose.core.utils.toAvatarInitials
import com.alex.yang.authenticationcompose.ui.theme.AlexAuthenticationComposeTheme

/**
 * Created by AlexYang on 2025/12/22.
 *
 *
 */
@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    photoUrl: String?,
    displayName: String?,
) {
    val initials = displayName.toAvatarInitials()

    Box(
        modifier = modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        if (!photoUrl.isNullOrBlank()) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentDescription = null,
                model = photoUrl,
            )
            return
        }

        Text(
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = initials
        )
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
fun AvatarPreview() {
    AlexAuthenticationComposeTheme {
        Avatar(
            photoUrl = null,
            displayName = "Alex Yang",
        )
    }
}