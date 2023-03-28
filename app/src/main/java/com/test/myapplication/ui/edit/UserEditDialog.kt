package com.test.myapplication.ui.edit

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.window.Dialog
import com.test.myapplication.ui.view.AppBtn
import com.test.myapplication.ui.view.AppTextField

@Composable
fun UserEditDialog(profileViewModel: ProfileViewModel, dismissListener: () -> Unit) {

    Dialog(onDismissRequest = {

        dismissListener()
    }) {

        val id = profileViewModel._userIdStateFlow.collectAsState()
        Log.v("_id", id.value.toString())
        profileViewModel.getUser(id.value)
        BodyContent(profileViewModel) { dismissListener() }
    }
}

@Composable
fun BodyContent(profileViewModel: ProfileViewModel, dismiss: () -> Unit) {

    val focusRequester = remember { FocusRequester() }

    val nameState = profileViewModel.nameState
    val emailState = profileViewModel.emailState

    val uiState by profileViewModel.uiActonState.collectAsState(UiState.No_State)
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
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
                keyboardType = KeyboardType.Email,
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
                    .padding(16.dp),
                label = "Email",
                txtValue = emailState.text,
                keyboardType = KeyboardType.Email,
                onTextChange = {

                },
                isEnabled = false
            )

            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = "Password",
                txtValue = "********",
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation(),
                onTextChange = {

                },
                isEnabled = false
            )
            AppBtn(onClick = {
                profileViewModel.saveRecord()
            })
        }
    }

    LaunchedEffect(key1 = uiState, block = {
        when (uiState) {
            is UiState.Success -> {

                profileViewModel.clearUiState()
                dismiss()
            }
            is UiState.Error -> {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    })
}