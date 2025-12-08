package com.example.mobile_application_final.screens.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_application_final.R
import com.example.mobile_application_final.data.viewModels.ThemeViewModel

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    themeModel: ThemeViewModel? = null,
    onLogOut: () -> Unit,
) {
    var debugLog by remember { mutableStateOf(true) }
    var requestDeleteData by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("account_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Account",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Theme", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(24.dp))

            themeModel?.let { model ->
                val isDarkMode by model.isDarkMode.collectAsState()

                Text(stringResource(R.string.light_theme))
                Switch(
                    checked = isDarkMode ?: isSystemInDarkTheme(),
                    onCheckedChange = model::onDarkModeChanged,
                    modifier = Modifier.testTag("dark_theme_switch")
                )
                Text(stringResource(R.string.dark_theme))
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                AccountRowWithCheckbox(
                    "Debug Log", debugLog,
                ) { debugLog = it }
                AccountRowWithCheckbox(
                    "Request Delete Data", requestDeleteData,
                ) { requestDeleteData = it }
                AccountRowWithNavigation("Customer Support") {}
                AccountRowWithNavigation("Your Orders") {}
                AccountRowWithNavigation("Buy Again") {}
                AccountRowWithNavigation("Keep Shopping For") {}
                AccountRowWithNavigation("List And Registries") {}
                AccountRowWithNavigation("Gift Cards") {}
            }
        }
        Text(stringResource(R.string.message_features))

        Spacer(Modifier.weight(1f))

        Button(
            onClick = onLogOut,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        ) {
            Box {
                Text(
                    stringResource(R.string.logout),
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AccountRowWithCheckbox(
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "A", fontWeight = FontWeight.Bold)
        }
        Text(
            text = title,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            fontSize = 16.sp
        )
        Checkbox(checked = isChecked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun AccountRowWithNavigation(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "A",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = title,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            fontSize = 16.sp
        )
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Navigate")
    }
}
