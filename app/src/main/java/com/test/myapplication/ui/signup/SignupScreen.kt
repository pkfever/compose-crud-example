package com.test.myapplication.ui.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.myapplication.ui.view.AppBtn
import com.test.myapplication.ui.view.AppTextField

@Composable
fun SignupScreen(viewModel: SignupViewModel = hiltViewModel(), onSignup: () -> Unit) {
    BodyContent(viewModel = viewModel, onSignup)
}

@Composable
fun BodyContent(viewModel: SignupViewModel, onSignup: () -> Unit) {

    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current

    val nameState = viewModel.nameState
    val emailState = viewModel.emailState
    val passwordState = viewModel.passwordState

    val loginUiState by viewModel.uiLoginState.collectAsState()

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column {
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        nameState.onFocusChange(focusState.isFocused)
                        if (!focusState.isFocused) {
                            nameState.enableShowErrors()
                        }
                    }
                    .padding(16.dp),
                label = "FullName",
                txtValue = nameState.text,
                keyboardType = KeyboardType.Text,
                onTextChange = {
                    nameState.text = it
                },
                isError = nameState.showErrors(),
                errorMsg = nameState.getError(),
                onImeAction = { focusRequester.requestFocus() },
                imeAction = ImeAction.Next
            )

            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        emailState.onFocusChange(focusState.isFocused)
                        if (!focusState.isFocused) {
                            emailState.enableShowErrors()
                        }
                    }
                    .padding(16.dp),
                label = "Email",
                txtValue = emailState.text,
                keyboardType = KeyboardType.Email,
                onTextChange = {
                    emailState.text = it
                },
                isError = emailState.showErrors(),
                errorMsg = emailState.getError(),
                onImeAction = { focusRequester.requestFocus() },
                imeAction = ImeAction.Next
            )

            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        passwordState.onFocusChange(focusState.isFocused)
                        if (!focusState.isFocused) {
                            passwordState.enableShowErrors()
                        }
                    }
                    .padding(16.dp),
                label = "Password",
                txtValue = passwordState.text,
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation(),
                onTextChange = {
                    passwordState.text = it
                },
                isError = passwordState.showErrors(),
                errorMsg = passwordState.getError(),
                onImeAction = { viewModel.validateFields() },
                imeAction = ImeAction.Done
            )
            AppBtn(onClick = {
                viewModel.validateFields()
            })
        }
    }

    LaunchedEffect(key1 = loginUiState) {
        if (loginUiState is SignupViewModel.UiState.Error) {
            Toast.makeText(context, "Invalid form info!", Toast.LENGTH_SHORT).show()
        } else if (loginUiState is SignupViewModel.UiState.Success) {
            onSignup()
        }
    }

}