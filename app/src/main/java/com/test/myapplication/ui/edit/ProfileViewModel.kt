package com.test.myapplication.ui.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.myapplication.data.entities.User
import com.test.myapplication.repository.UserRepository
import com.test.myapplication.ui.login.state.EmailState
import com.test.myapplication.ui.signup.state.NameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private lateinit var user: User

    private var uiState = MutableStateFlow<UiState>(UiState.No_State)
    val uiActonState = uiState.asSharedFlow()

    var nameState by mutableStateOf(NameState())
    var emailState by mutableStateOf(EmailState())

    private var userIdMutableStateFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    val _userIdStateFlow: StateFlow<Int> = userIdMutableStateFlow

    fun getUser(id: Int) {
        viewModelScope.launch {
            val userObject = userRepository.getUserById(id)
            user = userObject
            nameState.text = userObject.firstName
            emailState.text = userObject.email
        }
    }

    fun setUserId(userId: Int) {
        userIdMutableStateFlow.value = userId
    }

    fun saveRecord() {
        viewModelScope.launch {
            user.firstName = nameState.text
            userRepository.updateUser(user)
            uiState.value = UiState.Success
        }
    }

    fun clearUiState() {
        uiState.value = UiState.No_State
    }
}

sealed class UiState {

    object No_State : UiState()
    object Success : UiState()
    object Error : UiState()
}