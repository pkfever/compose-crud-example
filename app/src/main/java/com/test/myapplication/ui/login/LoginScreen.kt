package com.test.myapplication.ui.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.test.myapplication.ui.login.LoginUiEvents.Error
import com.test.myapplication.ui.login.LoginUiEvents.Success
import com.test.myapplication.ui.view.AppBtn
import com.test.myapplication.ui.view.AppTextField

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
) {

    val uiLoginState by loginViewModel.uiState.collectAsState()
    BodyContent(
        loginViewModel,
        uiLoginState,
        { navController.navigate("signup") },
        { navController.navigate("home") })
}

@Composable
fun BodyContent(
    loginViewModel: LoginViewModel,
    loginUiState: LoginUiState,
    onSignupClick: () -> Unit,
    onUserLogin: () -> Unit
) {

    HandleLoginUiState(loginViewModel = loginViewModel) {
        onUserLogin()
    }
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column {

            val focusRequester = remember { FocusRequester() }
            val emailState = loginViewModel.emailState
            val passwordState = loginViewModel.passwordState

            val onSubmit = {
                loginViewModel.validateFields()
            }

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
                onImeAction = { focusRequester.requestFocus() }
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
                imeAction = ImeAction.Done,
                onImeAction = { onSubmit() }
            )
            AppBtn(onClick = {
                onSubmit()
            })

            Spacer(modifier = Modifier.padding(top = 30.dp))



            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                ClickableText(
                    text = AnnotatedString("Click To Signup!"),
                    modifier = Modifier.align(Alignment.Center),
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    onClick = {
                        onSignupClick()
                    }
                )
            }
        }
    }
}

@Composable
private fun HandleLoginUiState(loginViewModel: LoginViewModel, onUserLogin: () -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        loginViewModel.uiEvents.collect {
            when (it) {
                Success -> {
                    onUserLogin()
                }
                Error -> {
                    Toast.makeText(context, "Invalid user", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(
        navController = rememberNavController(),
        loginViewModel = hiltViewModel()
    )
}