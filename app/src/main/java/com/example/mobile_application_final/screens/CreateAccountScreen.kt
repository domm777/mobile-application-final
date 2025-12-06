package com.example.mobile_application_final.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_application_final.R
import com.example.mobile_application_final.data.viewModels.CreateAccountViewModel
import com.example.mobile_application_final.data.viewModels.ShopScreenViewModel

@Composable
fun CreateAccountScreen(onLoginSuccess: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val viewModel: CreateAccountViewModel = viewModel()
    val userName by viewModel.users.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(stringResource(R.string.userName)) },
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.userEmail)) },
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.userPassword)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(8.dp)
        )
        Button(
            onClick = {
                onLoginSuccess()
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = (stringResource(R.string.login)))
        }
        Button(
            onClick = {
                viewModel.seedDbBtn()
                onLoginSuccess()
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = (stringResource(R.string.seed_data)))
        }
        Text("Seed data just for actually seeding data into the database")
    }
}
