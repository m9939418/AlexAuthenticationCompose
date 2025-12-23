package com.alex.yang.authenticationcompose.feature.auth.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alex.yang.authenticationcompose.R
import com.alex.yang.authenticationcompose.ui.theme.AlexAuthenticationComposeTheme

/**
 * Created by AlexYang on 2025/12/19.
 *
 *
 */
@Composable
fun SocialIconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
//    painterResId: Int = 0,
    painter: Painter,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .size(84.dp)
            .aspectRatio(1f),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painter,
                contentDescription = null
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
fun SocialIconButtonPreview() {
    AlexAuthenticationComposeTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            SocialIconButton(
                enabled = true,
                painter = painterResource(R.drawable.ic_google),
                onClick = {}
            )
        }
    }
}