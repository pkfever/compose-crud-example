package com.test.myapplication.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.myapplication.data.entities.User
import com.test.myapplication.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val userHomeState = MutableStateFlow<HomeUiState>(HomeUiState.loading)

    val uiState = userHomeState.asStateFlow()

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            val data = userRepository.getUserList()
            userHomeState.value = HomeUiState.UserList(data)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            userRepository.deleteUser(user)
            getUser()
        }
    }
}

