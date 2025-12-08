package com.example.mobile_application_final.screens.account

import android.R.attr.textColor
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_application_final.R

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    onLogOut: () -> Unit
) {
    var debugLog by remember { mutableStateOf(true) }
    var requestDeleteData by remember { mutableStateOf(true) }

    val cardBackgroundColor = if (isDarkMode) Color(0xFF333333) else Color(0xFFE8E0F2)

    val textColor = if (isDarkMode) Color.White else Color.Black

    val iconCircleColor = if (isDarkMode) Color.Gray else Color(0xFFD0C3E6)

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
            color = textColor
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Theme", style = MaterialTheme.typography.titleLarge, color = textColor)
            Spacer(modifier = Modifier.height(24.dp))















            Spacer(modifier = Modifier.height(24.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                AccountRowWithCheckbox(
                    "Debug Log", debugLog, isDarkMode, iconCircleColor) { debugLog = it }
                AccountRowWithCheckbox(
                    "Request Delete Data",requestDeleteData,isDarkMode, iconCircleColor) { requestDeleteData = it }
                AccountRowWithNavigation("Customer Support", isDarkMode, iconCircleColor) {}
                AccountRowWithNavigation("Your Orders", isDarkMode, iconCircleColor) {}
                AccountRowWithNavigation("Buy Again", isDarkMode, iconCircleColor) {}
                AccountRowWithNavigation("Keep Shopping For", isDarkMode, iconCircleColor) {}
                AccountRowWithNavigation("List And Registries", isDarkMode, iconCircleColor) {}
                AccountRowWithNavigation("Gift Cards", isDarkMode, iconCircleColor) {}
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
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8E0F2))
        ) {
            Box {
                Text(stringResource(R.string.logout), color = Color.Red, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun AccountRowWithCheckbox(
    title: String,
    isChecked: Boolean,
    isDarkMode: Boolean,
    iconCircleColor: Color,
    onCheckedChange: (Boolean) -> Unit
) {
    val textColor = if (isDarkMode) Color.White else Color.Black
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(iconCircleColor),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "A", color = Color.White, fontWeight = FontWeight.Bold)
        }
        Text(
            text = title,
            color = textColor,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            fontSize = 16.sp
        )
        Checkbox(checked = isChecked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun AccountRowWithNavigation(title: String, isDarkMode: Boolean, iconCircleColor: Color, onClick: () -> Unit) {
    val textColor = if (isDarkMode) Color.White else Color.Black
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
                .background(Color(0xFFD0C3E6)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "A", color = textColor, fontWeight = FontWeight.Bold)
        }
        Text(
            text = title,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            fontSize = 16.sp,
            color = textColor
        )
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Navigate")
    }
}

@Composable
@Preview(showBackground = true)
fun AccountScreenPreview() {
    MaterialTheme {
        Surface {
            AccountScreen(isDarkMode = false, onDarkModeChange = {}, onLogOut = {})
        }
    }
}