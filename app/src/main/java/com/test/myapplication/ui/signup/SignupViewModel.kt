package com.test.myapplication.ui.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.myapplication.repository.UserRepository
import com.test.myapplication.ui.login.state.EmailState
import com.test.myapplication.ui.login.state.PasswordState
import com.test.myapplication.ui.signup.model.SignupModel
import com.test.myapplication.ui.signup.state.NameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    var nameState by mutableStateOf(NameState())
    var emailState by mutableStateOf(EmailState())
    var passwordState by mutableStateOf(PasswordState())

    private var uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiLoginState = uiState.asStateFlow()


    fun validateFields() {
        uiState.value = UiState.Loading
        if (nameState.isValid && emailState.isValid &&
            passwordState.isValid
        ) {
            updateOrInsertData()
        } else {
            uiState.value = UiState.Error
        }
    }

    private fun updateOrInsertData() {
        viewModelScope.launch {

            val signupModel = SignupModel().apply {
                fullName = nameState.text
                email = emailState.text
                password = passwordState.text
            }

            val data = userRepository.addUser(signupModel)
            if (data) {
                uiState.value = UiState.Success
            } else {
                uiState.value = UiState.Error
            }
        }
    }

    sealed class UiState {

        object Empty : UiState()
        object Loading : UiState()
        object Success : UiState()
        object Error : UiState()
    }
}