package com.example.mobile_application_final.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_application_final.R
import com.example.mobile_application_final.components.ErrorBox
import com.example.mobile_application_final.data.viewModels.RegisterViewModel
import com.example.mobile_application_final.data.viewModels.RegistrationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CreateAccountScreen(onAccountCreated: () -> Unit) {
    val registerViewModel: RegisterViewModel = viewModel()
    var errorMessage: String? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ErrorBox(error = errorMessage)

        OutlinedTextField(
            value = registerViewModel.displayName,
            onValueChange = registerViewModel::onDisplayNameChanged,
            label = { Text(stringResource(R.string.display_name)) },
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = registerViewModel.email,
            onValueChange = registerViewModel::onEmailChanged,
            label = { Text(stringResource(R.string.userEmail)) },
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = registerViewModel.password,
            onValueChange = registerViewModel::onPasswordChanged,
            label = { Text(stringResource(R.string.userPassword)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(8.dp)
        )
        Button(
            enabled = !registerViewModel.isLoading,
            onClick = {
                errorMessage = null

                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        registerViewModel.onRegister()
                        onAccountCreated()
                    } catch (e: RegistrationException) {
                        errorMessage = e.msg
                    }
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = (stringResource(R.string.register)))
        }
    }
}
