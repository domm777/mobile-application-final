package com.example.mobile_application_final.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_application_final.R
import com.example.mobile_application_final.components.ErrorBox
import com.example.mobile_application_final.data.viewModels.LoginViewModel
import com.google.firebase.auth.FirebaseUser

@Composable
fun LoginScreen(onLoginSuccess: (FirebaseUser) -> Unit, onCreateAccountClicked: () -> Unit) {
    val loginModel: LoginViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.login),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        ErrorBox(error = loginModel.errorMessage)

        OutlinedTextField(
            value = loginModel.email,
            onValueChange = loginModel::onEmailChanged,
            label = { Text(stringResource(R.string.userEmail)) },
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = loginModel.password,
            onValueChange = loginModel::onPasswordChanged,
            label = { Text(stringResource(R.string.userPassword)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(8.dp)
        )
        Button(
            // disable the button when logging in is loading
            enabled = !loginModel.isLoading,
            onClick = {
                loginModel.onAttemptLogin(onLoginSuccess)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = stringResource(R.string.login))
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onCreateAccountClicked) {
            Text(
                text = stringResource(R.string.create_account_prompt),
                fontSize = 12.sp
            )
        }
    }
}
