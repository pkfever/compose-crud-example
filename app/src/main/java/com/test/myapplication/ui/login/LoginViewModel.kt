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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private var loginUiEventFlow = MutableSharedFlow<LoginUiEvents>()
    val uiEvents: SharedFlow<LoginUiEvents> = loginUiEventFlow

    private var loginUiStateFlow = MutableStateFlow<LoginUiState>(LoginUiState.NO_STATE)
    val uiState: StateFlow<LoginUiState> = loginUiStateFlow

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
                loginUiEventFlow.emit(LoginUiEvents.Success)
            } else {
                loginUiEventFlow.emit(LoginUiEvents.Error)
            }
        }
    }
}

sealed class LoginUiEvents {
    object Success : LoginUiEvents()
    object Error : LoginUiEvents()
}

sealed class LoginUiState {
    object Loading : LoginUiState()
    object NO_STATE : LoginUiState()
}