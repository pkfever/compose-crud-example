package com.test.myapplication.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.myapplication.repository.UserRepository
import com.test.myapplication.ui.login.model.LoginModel
import com.test.myapplication.ui.login.state.EmailState
import com.test.myapplication.ui.login.state.PasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private var loginUiStateFlow = MutableSharedFlow<LoginUiState>()
    val uiState: SharedFlow<LoginUiState> = loginUiStateFlow

    var emailState by mutableStateOf(EmailState())
    var passwordState by mutableStateOf(PasswordState())

    fun validateFields() {
        if (emailState.isValid && passwordState.isValid) {
            checkIfUserExist()
        }
    }

    private fun checkIfUserExist() {
        viewModelScope.launch {
            loginUiStateFlow.emit(LoginUiState.Loading)

            val loginModel = LoginModel().apply {
                email = emailState.text
                password = passwordState.text
            }

            val response = userRepository.checkUserStatus(loginModel)
            if (response) {
                loginUiStateFlow.emit(LoginUiState.Success)
            } else {
                loginUiStateFlow.emit(LoginUiState.Error)
            }
        }
    }
}

sealed class LoginUiState {
    object Loading : LoginUiState()
    object Empty : LoginUiState()
    object Success : LoginUiState()
    object Error : LoginUiState()
}